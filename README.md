# 1 总体功能概述
## 1.1 组件介绍
 行为验证码采用嵌入式集成方式，接入方便，安全，高效。抛弃了传统字符型验证码展示-填写字符-比对答案的流程，采用验证码展示-采集用户行为-分析用户行为流程，用户只需要产生指定的行为轨迹，不需要键盘手动输入，极大优化了传统验证码用户体验不佳的问题；同时，快速、准确的返回人机判定结果。
 目前对外提供两种类型的验证码，其中包含滑动拼图、文字点选。如图2-1、2-2所示。
 
![滑动拼图](https://github.com/anji-plus/captcha/blob/master/images/blockPuzzle.png "滑动拼图")

 图2-1 滑动拼图
 
![点选文字](https://github.com/anji-plus/captcha/blob/master/images/clickWord.png "点选文字")

 图2-2 文字点选

## 1.2 概念术语描述
| 术语  | 描述  |
| ------------ | ------------ |
|  验证码类型 | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord|
| 验证  |  用户拖动/点击一次验证码拼图即视为一次“验证”，不论拼图/点击是否正确 |
| 二次校验  | 验证数据随表单提交到产品后台后，产品后台需要将验证数据发送到集成jar包的/captcha/verify接口做二次校验，目的是核实验证数据的有效性。  |

## 1.3 基本设计描述
#### 1.3.1 组件工作流程图
①	用户访问产品应用页面，请求显示行为验证码
②	用户按照提示要求完成验证码拼图/点击
③	用户提交表单
④	验证数据随表单提交到产品后台后，产品后台需要将验证数据发送到集成jar包的/captcha/verify接口做二次校验，目的是核实验证数据的有效性。
⑤	集成jar包返回校验通过/失败到产品应用后端，再返回到前端。
如图1-3所示。
![时序图](https://github.com/anji-plus/captcha/blob/master/images/shixu.png "时序图")
###### 图 1-3 流程时序图

# 目录结构

## -core-captcha，maven编译
后端java源码，依赖redis
启动前请确认application.properties中配置(底图路径请确认无误)
启动成功后地址：http://127.0.0.1:8086

## -view-web vue项目
进去当前文件夹，npm install
vue源码

启动前请确认\view\web\src\components\verifition\utils\axios.js
```javascript
axios.defaults.baseURL = "http://127.0.0.1:8086"
//本地启动请注释当前行
//config.data = signUtil.sign(token, config.data);
```
地址是否正确
启动命令：
```javascript
npm start
```
访问地址
http://127.0.0.1:8080

## -view-uni-app 微信小程序
进去当前文件夹，npm install
vue源码

启动前请确认:<br>
\view\uni-app\src\pages\verify\verifySlider\verifySlider.vue 115行<br>
\view\uni-app\src\pages\verify\verifyPoint\verifyPoint.vue 111行
```javascript
baseUrl:'http://127.0.0.1:8086'
```
地址是否正确

打包命令：
```javascript
npm run  dev:mp-weixin
```
打包成功，会在当前目录生成dist/dev/mp-weixin文件夹

将mp-weixin添加到微信开发者工具小程序启动

appId请在微信公众平台自行申请


若访问报跨域问题，将com.anji.captcha.config.CorsFilter注释打开。

# 更详细的前后端接入文档，请查看


[链接](https://github.com/anji-plus/captcha/blob/master/core/captcha/README.md "链接")
