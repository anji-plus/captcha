<template>
    <div class="webHtml">
        <h3>
            在线体验:  <a class="link" target="_blank" href="/static/web-html/index.html">web-html在线体验地址</a>
        </h3>
        <h2>1. 兼容性</h2>
        <p>
            IE8+、Chrome、Firefox.(其他未测试)
        </p>
        <h2>2. 引入对应的css以及js文件</h2>
        <p>
            <p>
                1.引入css文件verify.css <link rel="stylesheet" type="text/css" href="css/verify.css">
            </p> 
            <p>
                2.<span class="sub_title">按顺序</span>引入js文件下js文件 jquery.min.js ,&nbsp;&nbsp;&nbsp;md5.js,&nbsp;&nbsp;&nbsp;signUtil.js,&nbsp;&nbsp;&nbsp;crypto-js.js,&nbsp;&nbsp;&nbsp;ase.js,&nbsp;&nbsp;&nbsp;verify.js
                <br>其中 md5.js,&nbsp;&nbsp;&nbsp;signUtil.js 根据<span class="sub_title">参数传输格式 自行修改</span>
            </p>
        </p>
        <h2>3. 初始化组件</h2>
        <div class="code">
            <pre>
                <p>准备初始化的容器</p> 
                <i>&lt;</i>div<i> id="content"></i><i>&lt;</i>/div<i>></i>

                <p class="sub_title">滑动式 调用slideVerify方法初始化; 点选调用pointsVerify方法初始化</p>
                $('#content').slideVerify({
                    vOffset : 5,	//误差量，根据需求自行调整
                    mode:'fixed',
                    imgSize : {       //图片的大小对象
                        width: '400px',
                        height: '200px',
                    },
                    barSize:{
                        width: '400px',
                        height: '40px',
                    },
                    ready : function() {  //加载完毕的回调
                    },
                    success : function(params) { //成功的回调
                        // 返回的二次验证参数
                        console.log(params,"params");
                    },
                    error : function() {        //失败的回调
                        //alert('验证失败！');
                    }
                });
            </pre>
        </div>
        <h2>4. 回调函数</h2>
        <el-table :data="backFuc" border style="width: 100%;margin-top:10px;">
            <el-table-column prop="fucName" label="事件名" width="180"></el-table-column>
            <el-table-column prop="desc" label="说明"></el-table-column>
        </el-table>
        <h2>6. 验证码参数</h2>
        <el-table :data="transParams" border style="width: 100%;margin-top:10px;">
            <el-table-column prop="paramName" label="参数名" width="180"></el-table-column>
            <el-table-column prop="desc" label="说明"></el-table-column>
        </el-table>
        <h2>7. 获取验证码接口详情</h2>
        <p class="sub_title">
            后端请求地址根据部署情况到:verify/js/verify.js 第五行 var baseUrl = "https://mirror.anji-plus.com/api" 修改路劲
        </p>
        <p><span class="sub_title">接口地址</span> ：http://10.108.11.46:8080/api/captcha/get</p>
        <p>
            <span class="sub_title">请求参数:</span>
            <pre style='line-hieght:36px;'> {
                    "captchaType": "blockPuzzle"  //验证码类型滑动
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
                {fucName:'success',desc:'验证码匹配成功后的回调函数。回调参数params 需要合并到业务逻辑参数中进行二次验证'},
                {fucName:'error',desc:'验证码匹配失败后的回调函数。'},
                {fucName:'ready',desc:'验证码初始化成功的回调函数。'}
            ],
            transParams:[
                {paramName:'mode',desc:'验证码的显示方式，弹出式pop，固定fixed，默认是：mode : ‘pop’。'},
                {paramName:'vSpace',desc:'验证码图片和移动条容器的间隔，默认单位是px。如：间隔为5px，设置vSpace:5。'},
                {paramName:'explain',desc:'滑动条内的提示，不设置默认是："向右滑动完成验证"。'},
                {paramName:'imgSize',desc:'其中包含了width、height两个参数，分别代表图片的宽度和高度'},
                {paramName:'barSize',desc:'其中包含了width、height两个参数，分别代表下方滑块容器的宽度和高度'},
            ]
        }
    },
}
</script>


<style lang="less" scoped>
.webHtml{
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
    .link{
        text-decoration:none;
        color: red;
    }
}
</style>
