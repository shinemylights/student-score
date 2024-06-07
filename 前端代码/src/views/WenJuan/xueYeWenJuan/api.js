let base = 'http://localhost:8083';
import axios from 'axios'

export const saveOneByType = (params) => {
  let url = '/wenjuan/save'
  return axios({
    method: 'get',
    url: `${base}${url}`,
    params: params,
    headers: {
      'token': sessionStorage.getItem('token')
    }
  });
}
export const getOneByType = (params) => {
    // return getRequest('/questionnaire/getOneByType', params)
}
export const editXueYeZiPing = (params) => {
    // return getRequest('/studentEvaluate/editXueYeZiPing', params)
}
