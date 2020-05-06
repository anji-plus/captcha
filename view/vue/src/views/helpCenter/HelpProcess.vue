<template>
    <div class="helpProcess">
        <h2>1. 后端接入</h2>
        <p>
            用户提交表单会携带验证码相关参数，产品应用在相关接口处将该参数传给 魔镜推送平台做二次校验，以确保该次验证是正确有效的。
        </p>
        <h2>2. 后端接入</h2>
        <p>
            引入相关组件，调用初始化函数，通过配置的一些参数信息。将行为验证码渲染出来
        </p>
    </div>
</template>

<script>
export default {
    
}
</script>

<style lang="less" scoped>
.helpProcess{
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
