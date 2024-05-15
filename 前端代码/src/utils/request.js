import axios from 'axios'
import { Message,Notification} from 'element-ui'
import router from "@/router";

const baseURL = 'http://localhost:8083'
// create an axios instance
const service = axios.create({
  baseURL: baseURL,
  timeout: 50000, // request timeout
  responseType: 'json',
  withCredentials: false // 是否允许带cookie这些
})

// 请求拦截
service.interceptors.request.use(
  config => {
    // 自定义header，可添加项目token
    if (sessionStorage.getItem('token')) {
      config.headers.token = sessionStorage.getItem('token')
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

//响应拦截
service.interceptors.response.use(
  response => {
    const resCode = response.data.code
    if (resCode === 200) {
      return Promise.resolve(response)
    } else if (resCode === 402){
      Notification.warning(response.data.msg)
      router.push("/home");
    }else if (resCode === 401){
      Message.error("信息失效，请重新登陆");
      sessionStorage.clear();
      router.push("/login");
    }else {
      Message.error(response.data.msg)
      return Promise.resolve(response)
    }
  },
  error => {
    return Promise.reject(error)
  }
)
export default {service, baseURL}
