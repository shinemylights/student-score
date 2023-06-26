
import request from "@/utils/request";
import router, { resetRouter } from '@/router'
import {Message} from "element-ui";

const getDefaultState = () => {
  return {
    user:null,
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_USERINFO: (state, userInfo) => {
    state.user=userInfo
  }
}


const actions = {
  //获取用户信息
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      request.service.post('/getInfo').then((res) => {
        if(res.data.code===200){
          commit('SET_USERINFO',res.data.data)
          resolve(res.data.data)
        }
      }).catch((error)=>{
         reject(error)
      })
      }
    )
  },
  //用户登出
  logout({ commit}) {
      commit('SET_USERINFO', null)
      resetRouter()
      sessionStorage.clear()
      Message.success("登出成功")
      router.push('/login')
  },


  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const { roles } = await dispatch('getInfo')
    resetRouter()
    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }

}



export default {
  namespaced: true,
  state,
  mutations,
  actions
}

