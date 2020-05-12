## 2.4 uni-app接入
### 2.4.1 兼容性
微信小程序等(uni-app支持的小程序系系列)
### 2.4.2 初始化组件
**将view/uni-app/src/pages/verify文件夹copy到自己项目中** <br/>
**下载相关依赖, npm install crypto-js   -S** <br/>
基础用例

```javascript
<template>
	<Verify
		@success="'success'" //验证成功的回调函数
		:mode="'fixed'"     //调用的模式
		:captchaType="'blockPuzzle'"    //调用的类型 点选或者滑动   
		:imgSize="{ width: '330px', height: '155px' }"//图片的大小对象
	></Verify>
</template>

****注: mode为"pop"时,使用组件需要给组件添加ref值,并且手动调用show方法 例: this.$refs.verify.show();****

<script>
//引入组件
import Verify from "./../../components/verifition/Verify";
export default {
	name: 'app',
	components: {
		Verify
	}
	methods:{
		success(params){
		// params 返回的二次验证参数
		}
	}
}
</script>
```

后端请求地址根据部署情况到:\view\uni-app\src\pages\verify\utils\request.js  第三行 修改路劲
```javascript
let baseUrl = "https://mirror.anji-plus.com/api"
```
后端参数请求格式到 : verify/utils/signUtil.js 修改

### 2.4.3 事件

|  参数 | 说明  |
| ------------ | ------------ |
| success  | 验证码匹配成功后的回调函数  |
| error  | 验证码匹配失败后的回调函数  |
| ready  |  验证码初始化成功的回调函数 |

### 2.4.4 验证码参数

| 参数  | 说明  |
| ------------ | ------------ |
| captchaType  | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord  |
| mode  | 验证码的显示方式，弹出式pop，固定fixed，默认是：mode : ‘pop’  |
| vSpace  | 验证码图片和移动条容器的间隔，默认单位是px。如：间隔为5px，设置vSpace:5  |
| explain  |  滑动条内的提示，不设置默认是：'向右滑动完成验证' |
|  imgSize |  其中包含了width、height两个参数，分别代表图片的宽度和高度，支持百分比方式设置 如:{width:'100%',height:'200px'} |
### 2.4.5 打包微信小程序
npm run  dev:mp-weixin