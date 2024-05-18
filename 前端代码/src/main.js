import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import App from './App'
import store from './store'
import router from './router'
import './assets/icons/font-icon/iconfont.css'
import './assets/icons/font-icon/iconfont.js'
import '@/icons'
import '@/permission' // permission control
import request from "@/utils/request"; // icon

import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css'; // 引入View UI的CSS样式
Vue.use(ViewUI); // 让Vue应用使用View UI

// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)
Vue.prototype.$axios = request.service
Vue.prototype.$BaseUrl =request.baseURL
//用户头像回显路径
Vue.prototype.$UserImg = '/uploads/images/user/'
//课程背景图片回显路径
Vue.prototype.$CouImg = '/uploads/images/cou/'
//试卷图片回显路径
Vue.prototype.$ExamImg= '/uploads/images/grade/'
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
