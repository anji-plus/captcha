<template>
  <div>
    <el-container style="width: 100%;height: 100%;">
      <el-header style="height: auto; padding: 0;">
        <myHeader></myHeader>
      </el-header>
    </el-container>
    <el-main>
      <iframe id="mainContent"  style="position:absolute;width:99%;height:80vh;top:64px"
              src="https://captcha.anji-plus.com/captcha-doc/"
      ></iframe>
    </el-main>
  </div>

</template>

<script>
import myHeader from "./../layout/Header"
import MenuItem from "./../layout/UseMenuItem"

export default {
    name:'doc',
    data() {
        return {

        }
    },
  mounted() {

  },
  components:{
       myHeader
    },
    methods: {

    },
}
</script>

<style scoped lang="less">

</style>

