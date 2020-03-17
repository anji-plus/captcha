<template>
    <div class="helpAdmin">
        <h2>1. 引入maven依赖</h2>
        <p>
            <i>&lt;</i>dependency<i>></i>
        <pre>  <i>&lt;</i>groupId<i>></i>com.anji<i>&lt;</i>/groupId<i>></i>
        <i>&lt;</i>artifactId<i>></i>captcha<i>&lt;</i>/artifactId<i>></i>
        <i>&lt;</i>version<i>></i>0.0.1-SNAPSHOT<i>&lt;</i>/version<i>></i>
    <i>&lt;</i>/dependency<i>></i>
</pre>
        </p>
        <h2>2. 启动类上添加相应注解</h2>
        <p>
            @ComponentScan(basePackages = {  <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "com.anji.captcha.util",     <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "com.anji.captcha.controller", <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "com.anji.captcha.service.impl",<br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "产品自身对应的包路径…"  <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;})

        </p>
        <h2>3. 二次校验接口</h2>
        <p>
            登录为例，用户在提交表单到产品应用后台，会携带一个验证码相关的参数。产品应用会在登录接口login中将该参数传给集成jar包中相关接口做二次校验。
接口地址：https://****/captcha/verify

        </p>
        <h2>4.请求方式</h2>
        <p>HTTP POST, 接口仅支持POST请求, 且仅接受 application/json 编码的参数</p>
        <h2>5. 请求参数</h2>
        <el-table :data="reqData" border style="width: 100%;margin-top:10px;">
            <el-table-column prop="params" label="参数" width="180"></el-table-column>
            <el-table-column prop="type" label="类型"></el-table-column>
            <el-table-column prop="isrequire" label="必填"></el-table-column>
            <el-table-column prop="mark" label="备注"></el-table-column>
        </el-table>
        <h2>6. 响应参数</h2>
        <el-table :data="repData" border style="width: 100%;margin-top:10px;">
            <el-table-column prop="params" label="参数" width="180"></el-table-column>
            <el-table-column prop="type" label="类型"></el-table-column>
            <el-table-column prop="isrequire" label="必填"></el-table-column>
            <el-table-column prop="mark" label="备注"></el-table-column>
        </el-table>
        <h2>7. 异常代号</h2>
        <el-table :data="numData" border style="width: 100%;margin-top:10px;">
            <el-table-column prop="number" label="error" width="180"></el-table-column>
            <el-table-column prop="desc" label="说明"></el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    data() {
        return {
            reqData:[
                {params:'captchaVerification',type:'String',isrequire:'Y',mark:'验证数据'},
            ],
            repData:[
                {params:'repCode',type:'String',isrequire:'Y',mark:'异常代号'},
                {params:'success',type:'Boolean',isrequire:'Y',mark:'成功或者失败'},
                {params:'error',type:'Boolean',isrequire:'Y',mark:'接口报错'},
                {params:'repMsg',type:'String',isrequire:'Y',mark:'错误信息'},
            ],
            numData:[
                {number:'0000',desc:'请求成功'},
                {number:'9999',desc:'服务器内部异常'},
                {number:'0011',desc:'参数不能为空'},
                {number:'6000',desc:'签名验证错误'},
            ]
        }
    },
}
</script>

<style lang="less" scoped>
.helpAdmin{
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
}
</style>
