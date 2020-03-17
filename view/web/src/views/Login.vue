<template>
  <div class="login-bg">
    <div class="login-bg_pattern">
      <div class="login-bg_left-top-circle">
      </div>
      <div class="login-bg_left100-top-circle">
      </div>
      <div class="login-bg_right-top-circle">
      </div>
      <div class="login-bg_right-bottom-circle">
      </div>
      <div class="login-bg_left-bottom-circle">
      </div>
    </div>

    <Verify
      @success="login"
      :captchaType="'blockPuzzle'"
      :containerId ="'#btn'"
      :imgSize="{width:'400px',height:'200px'}"
    ></Verify>

    <div class="login-login_wrap">
      <div class="login-login_box">
        <el-row type="flex" justify="center">
          <el-col :xs="22" :sm="20" :md="20" :lg="20" :xl="14">
            <div class="grid-content">
              <!--登录-->
              <div class="key">
                <div class="bottom-img"></div>
                <div class="form-info mt14vh pb50" style="margin-left:5vw">
                  <el-row :gutter="20">
                    <el-col :xs="22" :sm="14" :md="10" :lg="8" :xl="7">
                      <div class="logo"></div>
                      <form onsubmit="return false">
                        <ul class="user-info mt60">
                          <li class="user-input">
                            <input class="effect" placeholder="请输入用户名" id="usernameIput" type="text" v-model.trim="loginName" required autocomplete='username' @keyup.enter="login()" />
                            <label>用户名</label>
                          </li>
                          <li class="user-input">
                            <input class="effect" placeholder="请输入密码" id="passwordIput" type="password" v-model.trim="loginPassword" required autocomplete='current-password' @keyup.enter="login()" />
                            <label>密码</label>
                          </li>
                          <li class="keep-password">
                            <label>
                              <!-- <el-checkbox v-model="checked">记住密码</el-checkbox> -->
                              测试账号: admin  &nbsp; &nbsp; 密码: 123456
                            </label>
                            <!-- <el-button class="forget fr" @click="goForgetPassword">忘记密码?</el-button> -->

                          </li>
                          <li class="mt50">
                            <!-- @click="login" -->
                            <el-button class="buttonSize button-solid goHome" type="primary"  id="btn">
                              登&nbsp;&nbsp;录</el-button>
                          </li>
                          <!-- <li class="register mt50">
                            没有账号？ <el-button class="forget" @click="goRegister">马上注册 ></el-button>
                            <el-popover placement="right" width="300" trigger="click">
                              <h2 class="qrcode">扫码下载“魔镜—窥见”APP</h2> -->
                          <!-- <img src="../../static/QRcode.png" style="margin: 20px auto;display: block"> -->
                          <!-- <el-button class="forget fr " style="margin-top: -4px" slot="reference"><img src="../../static/icon-appdownload.png"> 移动端下载</el-button> -->
                          <!-- </el-popover>
                        </li> -->
                        </ul>

                      </form>
                    </el-col>
                  </el-row>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
    import { reqLogin, getMenu, queryForCodeSelect } from "@/api/basic";
    import { aesEncrypt ,aesDecrypt} from '@/utils/aes'
    import { setItem, getItem } from '@/utils/storage';

    import Verify from './../components/verifition/Verify'

    export default {
        data () {
            return {
                loginName: '',
                loginPassword: '',
                menu: [],
                checked: true,
                dialogFormVisible: false
            }
        },
        components: {
            Verify
        },
        mounted () {
            this.getCookie();
        },
        beforeDestroy () {
            document.removeEventListener('keyup',this.handerKeyup)
        },
        created(){
            document.addEventListener("keyup",this.handerKeyup)

        },
        methods: {
            handerKeyup(e){
                var keycode = document.all ? event.keyCode : e.which;
                if (keycode == 13) {
                    // this.login();// 登录方法名
                    document.getElementById("btn").click();
                    // return false;
                }
            },
            goRegister () {
                this.$router.push("/register");
            },
            goForgetPassword () {
                this.$router.push("/forget-password");
            },
            login (params) {
                if (this.loginName == "admin" && this.loginPassword=='123456') {
                    this.$router.push("/useOnline/sliderFixed");
                }else{
                    this.$message({
                        message: "输入测试账号密码",
                        type: 'warning'
                    });
                }

                // let _this = this;
                // let p = {
                //   "userName": this.loginName,
                //   "password": aesEncrypt(this.loginPassword)
                // }
                // if (this.checked = true) {
                //   //传入账号名，密码，和保存天数3个参数
                //   this.setCookie(this.loginName, aesEncrypt(this.loginPassword), 7);
                // }
                // reqLogin(Object.assign(p,params)).then(res => {
                //   // 缓存设置
                //   if (res.repCode == "0000") {
                //     var repData = res.repData;

                //     setItem("token", repData.token)
                //     setItem("accessUser", repData.accessUser)

                //     queryForCodeSelect().then(res1 => {
                //       if (res1.repCode == '0000') {
                //         setItem("queryForCodeSelect",res1.repData);

                //       }
                //     }).catch(error => {
                //     })
                //   }
                // }).catch(error => {
                // })
            },
            loginEnter(){
                this.login();
            },
            //设置cookie
            setCookie (c_name, c_pwd, exdays) {
                var exdate = new Date();//获取时间
                exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays);//保存的天数
                //字符串拼接cookie
                window.document.cookie = "userName" + "=" + c_name + ";path=/;expires=" + exdate.toGMTString();
                window.document.cookie = "userPwd" + "=" + c_pwd + ";path=/;expires=" + exdate.toGMTString();
            },
            //读取cookie
            getCookie: function () {
                console.log(document.cookie)
                if (document.cookie.length > 0) {
                    var arr = document.cookie.split('; ');//这里显示的格式需要切割一下自己可输出看下
                    for (var i = 0; i < arr.length; i++) {
                        var arr2 = arr[i].split('=');//再次切割
                        //判断查找相对应的值
                        if (arr2[0] == 'userName') {
                            this.loginName = arr2[1];//保存到保存数据的地方
                        } else if (arr2[0] == 'userPwd') {
                            this.loginPassword = aesDecrypt(arr2[1]);
                        }
                    }
                }
            },
            //清除cookie
            clearCookie: function () {
                this.setCookie("", "", -1);//修改2值都为空，天数为负1天就好了
            }
        },
    }
</script>
<style scoped lang="less">
  @import "./../assets/style/theme";
  @wathet-blue: #f0f7ff;
  @wathet-blue: #f0f7ff;
  @color91: #919191;
  @coloreE0: #e0e0e0;
  @colore666: #666;
  :focus {
    outline: none;
  }
  .login-bg {
    background: @wathet-blue;
    width: 100vw;
    height: 100vh;
    position: relative;
    overflow: hidden;
  }
  .login-bg_pattern {
    position: absolute;
    z-index: 1;
    width: 100vw;
    height: 100vh;
    .login-bg_left-top-circle,
    .login-bg_left100-top-circle,
    .login-bg_right-top-circle,
    .login-bg_right-bottom-circle,
    .login-bg_left-bottom-circle {
      opacity: 0.45;
      background: #e1eeff;
      border-radius: 74px;
      display: block;
      float: left;
      position: relative;
    }
    .login-bg_left-top-circle {
      width: 300px;
      height: 300px;
      transform: rotate(30deg);
      margin-top: -150px;
      margin-left: 70px;
    }
    .login-bg_left100-top-circle {
      width: 200px;
      height: 200px;
      transform: rotate(30deg);
      margin-top: -150px;
      margin-left: 200px;
    }
    .login-bg_right-top-circle {
      width: 300px;
      height: 400px;
      margin-right: -100px;
      transform: rotate(42deg);
      float: right;
    }
    .login-bg_right-bottom-circle {
      width: 300px;
      height: 300px;
      transform: rotate(30deg);
      bottom: -200px;
      right: 350px;
      position: absolute;
    }
    .login-bg_left-bottom-circle {
      width: 300px;
      height: 300px;
      transform: rotate(-11deg);
      bottom: -50px;
      left: -100px;
      position: absolute;
    }
  }
  .login-login_box {
    position: absolute;
    left:50%;
    top:50%;
    transform:translate(-50%,-50%);
    z-index: 1000;
    width: 100%;
    /*overflow: auto;*/
    .key {
      box-sizing: border-box;
      background: @white url("/static/login/bg-1.png") right top no-repeat;
      box-shadow: 0 0 60px 5px rgba(36, 132, 255, 0.41);
      border-radius: 8px;
      overflow: hidden;
      opacity: 0;
      -webkit-animation: fromBack 0.3s linear 0.1s forwards;
      -moz-animation: fromBack 0.3s linear 0.1s forwards;
      -ms-animation: fromBack 0.3s linear 0.1s forwards;
      animation: fromBack 0.3s linear 0.1s forwards;
      /* -webkit- */
      @-webkit-keyframes fromBack {
        0% {
          -webkit-transform: scale(0);
          opacity: 0;
        }
        100% {
          -webkit-transform: scale(1);
          opacity: 1;
          min-height: 69vh;
          max-height: 90vh;
        }
      }
      /* -moz- */
      @-moz-keyframes fromBack {
        0% {
          -moz-transform: scale(0);
          opacity: 0;
        }
        100% {
          -moz-transform: scale(1);
          opacity: 1;
          height: 69vh;
          min-height: 69vh;
          max-height: 90vh;
        }
      }
      /**/
      @keyframes fromBack {
        0% {
          transform: scale(0);
          opacity: 0;
        }
        100% {
          transform: scale(1);
          opacity: 1;
          min-height: 69vh;
          max-height: 90vh;
        }
      }
      .bottom-img {
        width: 170px;
        height: 170px;
        display: block;
        position: absolute;
        z-index: 2;
        bottom: 0;
        background: url("/static/login/bg-2.png") left bottom no-repeat;
      }
      .form-info {
        .logo {
          width: 200px;
          height: 89px;
          display: block;
          margin: 0 auto;
          background: url("/static/logo.png") right top no-repeat;
        }
        .user-info {
          padding-left: @px30;
          .user-input {
            position: relative;
            border-bottom: 1px solid @coloreE0;
            padding: 0 20px 0 30px;
            margin-top: 37px;
            height: 30px;
            background: #fff;
            .effect ~ label {
              position: relative;
              top: -45px;
              width: 100%;
              color: @color91;
              transition: 0.3s;
              -webkit-transition: 0.1s;
              font-size: @f12;
              letter-spacing: 0.5px;
            }
            .effect:focus ~ label {
              position: relative;
              top: -45px;
              color: @lightblue;
              transition: 0.3s;
              border-left: 3px solid @lightblue;
              margin-left: -30px;
              padding-left: 30px;
            }
            input {
              position: relative;
              width: 100%;
              font-size: 13px;
            }
          }
          .keep-password {
            font-size: @f13;
            letter-spacing: 0;
            margin-top: @px24;
            label {
              .el-checkbox__input.is-checked + .el-checkbox__label {
                color: @color91;
              }
            }
          }
          .goHome {
            background: @lightblue;
            box-shadow: 0 12px 51px -12px @azure;
            border-radius: 100px;
            width: 140px;
            height: 40px;
            display: block;
            margin: @px24 auto;
            font-size: @f18;
            text-align: center;
            line-height: 17px;
          }
          .register {
            font-size: 13px;
            color: #919191;
            text-align: center;
            .a {
              color: @lightblue;
              cursor: pointer;
            }
            .a:hover {
              text-decoration: underline;
            }
          }
          .forget {
            font-size: @f13;
            color: @lightblue;
            letter-spacing: 0;
            text-align: right;
            border: none;
            padding: 0;
            text-decoration: underline;
            background: none;
            margin-top: 2px;
          }
        }
      }
    }
  }
  /*注册*/
  .transition-box {
    position: relative;
    margin-top: 0;
    z-index: 1000;
    background-color: @white;
  }
  .qrcode {
    line-height: 40px;
    text-align: center;
    border-bottom: 1px solid #eee;
  }
  #qrcode {
    width: 120px;
    padding: 20px;
    margin: 40px auto;
    display: block;
  }
  input:-webkit-autofill,
  textarea:-webkit-autofill,
  select:-webkit-autofill {
    -webkit-box-shadow: 0 0 0 1000px white inset;
  }
</style>
