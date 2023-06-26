import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // 首部进度条
import 'nprogress/nprogress.css' //  进度条样式
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = sessionStorage.getItem("token")
  if (hasToken) {
    const hasUserInfo = store.state.user.user!=null
    if (hasUserInfo) {
      next()
    } else {
        try{
          const { roles } = await store.dispatch('user/getInfo')
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)
          router.options.routes = store.getters.permission_routes;
          router.addRoutes(store.getters.permission_routes)
          next({ ...to, replace: true })
      } catch (error) {
          sessionStorage.clear();
          NProgress.done()
        }
    }
  }
  else {
    /* 没有token且不是登录注册就重定向到登录页面*/
    if (to.path === '/login'||to.path === '/register') {
      next()
    } else {
      Message.error("请登录")
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
