let base = 'http://localhost:8083';
import axios from 'axios'
export const getCompetitionOne = (params) => {
  let url = '/contest/insert'
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
  console.info(params);
  let url = '/practice/getMyPage'
  // let accessToken = getStore('accessToken');
  // let accessToken = ''
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'token': sessionStorage.getItem('token')
    }
  });
}
export const getCompetitionCount = (params) => {
  let url = '/contest/insert'
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
    let url = '/practice/insert'
    // let accessToken = getStore('accessToken');
    // let accessToken = ''
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
    let url = '/contest/insert'
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
    let url = '/contest/insert'
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
    let url = '/contest/insert'
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

