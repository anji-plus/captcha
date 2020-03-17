import axios from 'axios';
import { Message, MessageBox } from 'element-ui';
import { setItem, getItem } from '@/utils/storage';
import signUtil from '@/utils/signUtil';
import router from '../router'

// console.log(process.env.NODE_ENV);

// axios.defaults.baseURL = process.env.BASE_API;
axios.defaults.baseURL = "http://10.108.11.46";

// if (process.env.NODE_ENV == 'development') {
//   axios.defaults.baseURL = 'http://10.108.12.100:28060/api/';
// } else if (process.env.NODE_ENV == 'production') {
//   axios.defaults.baseURL = 'http://10.108.12.3:28103/';
// }
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
    // 在发送请求之前做些什么
    var token = getItem('token');
    var accessUser = getItem('accessUser');
    
    // sign 加密
    if ((token == null || token == '') && config.data.hasOwnProperty('token')) {
      token = config.data.token;
    }

    config.data = signUtil.sign(token, config.data);
    return config
  },
  error => {
    // Do something with request error
    // console.log(error) // for debug
    Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data;
    if (res.repCode == '0000') {
      return res
    }
    else if (res.repCode == '0024') {
      //登录超时或被登出，弹确认框，用户确认后，跳转到登录页面
      MessageBox({
        message: "当前登录已失效或异地登录，请重新登录",
        type: 'error',
        duration: 3 * 1000,
      }).then(() => {
        sessionStorage.clear();
        localStorage.clear();
        // location.reload();
        window.location.href = "/";
      })
    }else if(res.repCode == "3100" || res.repCode == "3101"){
      return res;
    }
    else {
      Message({
        message: res.repMsg,
        type: 'error',
        duration: 3 * 1000
      })
      return res;
    }
  },
  error => {
    var errorStatus = error.response.status;
    var errorData = error.response.data;
    var messageTxt = "";
    if (errorStatus != 200) {
      messageTxt = "服务器内部错误，请联系管理员";
    } else {
      messageTxt = '失败原因：' + errorData.repCode + '--' + errorData.repMsg;
    }
    Message({
      message: messageTxt,
      type: 'error',
      duration: 5 * 1000
    })
  }
)

export default service
