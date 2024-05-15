let base = 'http://localhost:8083';
import axios from 'axios'
export const getCompetitionOne = (params) => {
  let url = '/competition/insert'
  // let accessToken = getStore('accessToken');
  let accessToken = ''
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'token': sessionStorage.getItem('token')
    }
  });
}
export const getCompetitionList = (params) => {
  let url = '/competition/insert'
  // let accessToken = getStore('accessToken');
  let accessToken = ''
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'accessToken': accessToken
    }
  });
}
export const getCompetitionCount = (params) => {
  let url = '/competition/insert'
  // let accessToken = getStore('accessToken');
  let accessToken = ''
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'accessToken': accessToken
    }
  });
}
  export const addCompetition = (params) => {
    console.log("进入了添加方法")
    let url = '/competition/insert'
    // let accessToken = getStore('accessToken');
    let accessToken = ''
    return axios({
      method: 'post',
      url: `${base}${url}`,
      data: params,
      headers: {
        'token': sessionStorage.getItem('token')
      }
    });
  }
  export const editCompetition = (params) => {
    let url = '/competition/insert'
    // let accessToken = getStore('accessToken');
    let accessToken = ''
    return axios({
      method: 'post',
      url: `${base}${url}`,
      data: params,
      headers: {
        'accessToken': accessToken
      }
    });
  }
  export const addOrEditCompetition = (params) => {
    let url = '/competition/insert'
    // let accessToken = getStore('accessToken');
    let accessToken = ''
    return axios({
      method: 'post',
      url: `${base}${url}`,
      data: params,
      headers: {
        'accessToken': accessToken
      }
    });
  }
  export const deleteCompetition = (params) => {
    let url = '/competition/insert'
    // let accessToken = getStore('accessToken');
    let accessToken = ''
    return axios({
      method: 'post',
      url: `${base}${url}`,
      data: params,
      headers: {
        'accessToken': accessToken
      }
    });
  }

