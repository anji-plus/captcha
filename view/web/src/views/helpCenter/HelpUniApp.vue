<template>
    <div class="helpWeb">
        <h2>1. 兼容性</h2>
        <p>
            微信小程序等(uni-app支持的小程序系系列)
        </p>
        <h2>2. 初始化组件</h2>
        <div class="code">
            <p>2.1 引入或者下载组件到项目中</p>
            <p>2.2 下载组件所需要的依赖插件: <span style="color:red
            "> npm install  &nbsp;&nbsp; crypto-js&nbsp;&nbsp;  -S</span> </p>
            <pre >
                <i>&lt;</i>template<i>></i>
                <i>&lt;</i>Verify
                    @success<i>=</i>"'success'"                                            //验证成功的回调函数
                    :mode<i>=</i>"'fixed'"                                                     //调用的模式
                    :captchaType="'blockPuzzle'"                                //调用的类型 点选或者滑动   
                    :imgSize<i>=</i>"{ width: '330px', height: '155px' }"       //图片的大小对象
                    ref="verify"
                <i>></i><i>&lt;</i>/Verify
                <i>&lt;</i>/template<i>></i>

                <i>&lt;</i>script<i>></i>
                    //引入组件
                    import Verify from "./../../components/verifition/Verify";

                    export default {
                        name: 'app',
                        components<i>:</i> {
                            Verify
                        }
                        methods:{
                            success(params){
                            // params 返回的二次验证参数
                            }
                        }
                    }
                <i>&lt;</i>/script<i>></i>
            </pre>
            <strong class="sub_title">注: mode为"pop"时,使用组件需要给组件添加ref值,并且手动调用show方法
                例: this.$refs.verify.show();
            </strong>
        </div>
        <h2>3. 回调函数</h2>
        <el-table :data="backFuc" border style="width: 100%;margin-top:10px;">
            <el-table-column prop="fucName" label="事件名" width="180"></el-table-column>
            <el-table-column prop="desc" label="说明"></el-table-column>
        </el-table>
        <h2>4. 验证码参数</h2>
        <el-table :data="transParams" border style="width: 100%;margin-top:10px;">
            <el-table-column prop="paramName" label="参数名" width="180"></el-table-column>
            <el-table-column prop="desc" label="说明"></el-table-column>
        </el-table>
        <h2>5. 获取验证码接口详情</h2>
        <p><span class="sub_title">接口地址</span> ：http://10.108.11.46:8080/api/captcha/get</p>
        <p>
            <span class="sub_title">请求参数:</span>
            <pre style='line-hieght:36px;'>  {    
                    "captchaType": "blockPuzzle"  //验证码类型 clickWord
                }
            </pre>
        </p>
        <p>
            <span class="sub_title">响应参数：</span>
            <pre style="color:black;background:white;">     {
                "repCode": "0000",
                "repData": {
                    "currentPage": 1,
                    "pageSize": 10,
                    "captchaId": "9ca07a9c-c260-50ae-2c13-89cde2f34cb9",
                    "projectCode": "mirror_prod",
                    "originalImageBase64": "底图base64",
                    "point": {    //默认不返回的，校验的就是该坐标信息，允许误差范围
                        "x": 205,
                        "y": 5
                    },
                    "jigsawImageBase64"<i>:</i> "滑块图base64",
                    "token"<i>:</i> "71dd26999e314f9abb0c635336976635",
                    "result"<i>:</i> false,
                    "opAdmin"<i>:</i> false
                }
            </pre>

        </p>
        <h2>6. 核对验证码接口详情</h2>
        <p><span class="sub_title">接口地址</span> ：http://10.108.11.46:8080/api/captcha/check</p>
        <p>
            <span class="sub_title">请求参数：</span>
            <pre style="color:black;background:white;">     {
                    "captchaType": "blockPuzzle",
                    "pointJson": "QxIVdlJoWUi04iM+65hTow==",  //aes加密坐标信息
                    "token": "71dd26999e314f9abb0c635336976635"  //get请求返回的token
                },
            </pre>
        </p>
        <p>
            <span class="sub_title">响应参数：</span>
            <pre style="color:black;background:white;">     {
                "repCode": "0000",
                "repData": {
                    "currentPage": 1,
                    "pageSize": 10,
                    "opToken": "ddd9f7596f364dfabc474cd695ae26c0",
                    "sign": "6523310d4331385e330bdd01304653f4",
                    "time": "1582789723222",
                    "sourceIP": "10.108.11.159",
                    "captchaId": "9ca07a9c-c260-50ae-2c13-89cde2f34cb9",
                    "captchaType": "blockPuzzle",
                    "pointJson": "QxIVdlJoWUi04iM+65hTow==", //
                    "token": "ddd9f7596f364dfabc474cd695ae26c0",
                    "result": true,
                    "opAdmin": false
                },
                "success": true,
                "error": false
                }
            </pre>

        </p>
        

    </div>
</template>

<script>
export default {
    data() {
        return {
            backFuc:[
                {fucName:'success',desc:'验证码匹配成功后的回调函数。'},
                {fucName:'error',desc:'验证码匹配失败后的回调函数。'},
                {fucName:'ready',desc:'验证码初始化成功的回调函数。'}
            ],
            transParams:[
                {paramName:'captchaType',desc:'必需 1）滑动拼图 blockPuzzle 2）文字点选 clickWord'},
                {paramName:'mode',desc:'验证码的显示方式，弹出式pop，固定fixed，默认是：mode : ‘pop’。'},
                {paramName:'vSpace',desc:'验证码图片和移动条容器的间隔，默认单位是px。如：间隔为5px，设置vSpace:5。'},
                {paramName:'explain',desc:'滑动条内的提示，不设置默认是："向右滑动完成验证"。'},
                {paramName:'imgSize',desc:'其中包含了width、height两个参数，分别代表图片的宽度和高度.'},
            ]

        }
    },
}
</script>

<style lang="less" scoped>
.helpWeb{
    h2{
        background: #eee;
        line-height: 36px;
        text-indent: 1rem;
        margin-top: 20px;
    }
    p{
        line-height: 36px;
        text-indent: 2em;
    }
    .code{
        padding-top: 20px;
        display: block;
        overflow: auto;
        color: #4d4d4c;
        padding: 0.5em;
        line-height: 1.5;
    }
    .sub_title{
        background-color: yellow;
        padding: 5px;
        border-radius: 8px;
    }
}
</style>
