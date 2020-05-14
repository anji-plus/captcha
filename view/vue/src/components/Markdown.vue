<template>
    <div class="markdown">
        <mavon-editor v-model="value"></mavon-editor>
    </div>
</template>

<script>

import {readFile} from './../utils/readFile'

export default {
    props:{
        filePath:{
            type:String,
            require:true
        }
    },
    data() {
        return {
            value:'',
        }
    },
    watch: {
        filePath:'loadFile',
        immediate:true
    },
    methods: {
        loadFile(){
            if (!this.filePath.includes("/captcha-web")) {
                this.filePath ='https://mirror.anji-plus.com/captcha-web' + this.filePath
                readFile(this.filePath).then(res=>{
                    console.log(res,"res");
                    
                    this.value = res.data ? res.data: res
                })
            }
        }
    },
    created() {},
}
</script>

<style lang="less" scoped>
/deep/.v-note-wrapper {
    height:calc(100vh - 186px)!important;
    .v-note-op{
        display: none!important;
    }
    .v-note-edit{
        display: none!important;
    }
    .v-note-show{
        width: 100%!important;
        flex: 0 0 100%!important;
    }
} 
</style>
