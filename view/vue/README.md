
## 2.3 前端接入
### 2.3.1 兼容性
IE8+、Chrome、Firefox.(其他未测试)
### 2.3.2 初始化组件
安装请求和加密组件
```java
npm install axios  crypto-js   -S
```

复制vue验证码实现文件夹到自己工程对应目录下，view/vue/src/components/verifition，在登录页面插入如下代码。
```javascript
<template>
	<Verify
		@success="'success'" //验证成功的回调函数
		:mode="'fixed'"     //调用的模式
		:captchaType="'blockPuzzle'"    //调用的类型 点选或者滑动
		:imgSize="{ width: '330px', height: '155px' }"//图片的大小对象
	></Verify
</template>
****注: mode为"pop"时,使用组件需要给组件添加ref值,并且手动调用show方法 例: this.$refs.verify.show()****
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
		  // params 返回的二次验证参数, 和登录参数一起回传给登录接口，方便后台进行二次验证
		}
	}
}
</script>
```
## 注意事项
#### 后端请求地址根据部署情况到:verifition/utils/axios.js 第五行 axios.defaults.baseURL = process.env.BASE_API 修改路劲
#### 后端参数请求格式到 : verifition/utils/axios.js 第23行  注释掉或者修改成自己的加密方式

### 2.3.3 事件

|  参数 | 说明  |
| ------------ | ------------ |
| success  | 验证码匹配成功后的回调函数  |
| error  | 验证码匹配失败后的回调函数  |
| ready  |  验证码初始化成功的回调函数 |

### 2.3.4 验证码参数

| 参数  | 说明  |
| ------------ | ------------ |
| captchaType  | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord  |
| mode  | 验证码的显示方式，弹出式pop，固定fixed，默认是：mode : ‘pop’  |
| vSpace  | 验证码图片和移动条容器的间隔，默认单位是px。如：间隔为5px，设置vSpace:5  |
| explain  |  滑动条内的提示，不设置默认是：'向右滑动完成验证' |
|  imgSize |  其中包含了width、height两个参数，分别代表图片的宽度和高度，支持百分比方式设置 如:{width:'100%',height:'200px'} |
| blockSize  | 其中包含了width、height两个参数，分别代表拼图块的宽度和高度，如:{width:'40px',height:'40px'}  |
| barSize  | 其中包含了width、height两个参数，分别代表滑动条的宽度和高度，支持百分比方式设置，如:{width:'100%',height:'40px'}  |

### 2.3.5 获取验证码接口详情
#### 接口地址：http://*:*/captcha/get
##### 请求参数：
```json
{
	"captchaType": "blockPuzzle"  //验证码类型 clickWord
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "originalImageBase64": "底图base64",
        "point": {    //默认不返回的，校验的就是该坐标信息，允许误差范围
            "x": 205,
            "y": 5
        },
        "jigsawImageBase64": "滑块图base64",
        "token": "71dd26999e314f9abb0c635336976635", //一次校验唯一标识
        "result": false,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```
### 2.3.6 核对验证码接口详情
#### 请求接口：http://*:*/captcha/check
##### 请求参数：
```json
{
	 "captchaType": "blockPuzzle",
	 "pointJson": "QxIVdlJoWUi04iM+65hTow==",  //aes加密坐标信息
	 "token": "71dd26999e314f9abb0c635336976635"  //get请求返回的token
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "captchaType": "blockPuzzle",
        "token": "71dd26999e314f9abb0c635336976635",
        "result": true,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```
