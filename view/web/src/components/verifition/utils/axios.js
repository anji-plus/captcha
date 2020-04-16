import axios from 'axios';
import signUtil from './signUtil';

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
    // config.data = signUtil.sign(token, config.data);  //此处为魔镜后端数据传输结构 机密方式
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
