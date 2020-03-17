# 1 总体功能概述
## 1.1 组件介绍
 行为验证码采用嵌入式集成方式，接入方便，安全，高效。抛弃了传统字符型验证码展示-填写字符-比对答案的流程，采用验证码展示-采集用户行为-分析用户行为流程，用户只需要产生指定的行为轨迹，不需要键盘手动输入，极大优化了传统验证码用户体验不佳的问题；同时，快速、准确的返回人机判定结果。
 目前对外提供两种类型的验证码，其中包含滑动拼图、文字点选。如图2-1、2-2所示。
 
![滑动拼图](https://github.com/raodeming/captcha/blob/master/images/blockPuzzle.png "滑动拼图")

 图2-1 滑动拼图
 
![点选文字](https://github.com/raodeming/captcha/blob/master/images/clickWord.png "点选文字")

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
![时序图](https://github.com/raodeming/captcha/blob/master/images/shixu.png "点选文字")
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
```
地址是否正确
启动命令：
```javascript
npm start
```
访问地址
http://127.0.0.1:8080

###更详细的接入文档，请查看
[链接](https://github.com/raodeming/captcha/blob/master/core/captcha/README.md "链接")
