import axios from 'axios';
import signUtil from './signUtil';
import { Message, MessageBox } from 'element-ui';

axios.defaults.baseURL = process.env.BASE_API;

const service = axios.create({
  withCredentials: true,
  timeout: 40000,
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
    'Content-Type': 'application/json; charset=UTF-8'
  },
})

service.interceptors.request.use(
  config => {
    var token = sessionStorage.getItem('token');
    var accessUser =JSON.parse(sessionStorage.getItem('accessUser'));
    if ((token == null || token == '') && config.data.hasOwnProperty('token')) {
      token = config.data.token;
    }
    //config.data = signUtil.sign(token, config.data);  //如果应用经过网关需要签名或者时间戳，请自己实现。
    return config
  },
  error => {
    Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data;
    if (res.repCode == '0000') {
      return res
    }else {
      Message({
        message: res.repMsg,
        type: 'error',
        duration: 3 * 1000
      })
      return res;
    }
  },
  error => {
  }
)

export default service
