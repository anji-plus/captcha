import axios from 'axios';
import signUtil from './signUtil';

axios.defaults.baseURL = "http://127.0.0.1:8086";

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
    // config.data = signUtil.sign(token, config.data);
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
      return res;
    }
  },
  error => {
  }
)

export default service
