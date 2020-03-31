<template>
	<view class="content">
		<image class="logo" src="/static/logo.png"></image>
		<view>
			<text class="title">{{name}}</text>
			<button type="primary" id="btn" @click="showVerify">登录测试</button>
			<Verify
				@success='success'
				:mode="'pop'"
            	:captchaType="'blockPuzzle'"
				:captchaId ="'btn'"
				ref="verify"
			></Verify>
		</view>
	</view>
</template>

<script>
	import Verify from "./../verify/verify"
	export default {
		data() {
			return {
				name:"行为验证码插件测试"
			}
		},
		components:{
			Verify
		},
		methods: {
			showVerify(){
				this.$refs.verify.show();
			},
			success(data){
				console.log(data,"成功返回");
			}
		},
	}
</script>

<style>
	.content {
		text-align: center;
		height: 400upx;
	}

	.logo {
		height: 200upx;
		width: 200upx;
		margin-top: 200upx;
	}

	.title {
		font-size: 36upx;
		color: #8f8f94;
	}
</style>
