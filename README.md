
[AjPlus Captcha Official Website](https://captcha.anji-plus.com/)
============
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)[![Total Lines](https://tokei.rs/b1/github/anji-plus/captcha?category=lines)](https://github.com/anji-plus/captcha)

> AjPlus Captcha 

[![Stargazers over time](https://starchart.cc/anji-plus/captcha.svg)](https://starchart.cc/anji-plus/captcha)

[![EN doc](https://img.shields.io/badge/document-English-blue.svg)](README.md)[![CN doc](https://img.shields.io/badge/文档-中文版-blue.svg)](README_CN.md)


# 1. Online Demo
### &emsp; 1.1 [Have a try](https://captcha.anji-plus.com/ "链接")
### &emsp; 1.2 [Document](https://captcha.anji-plus.com/#/doc "doc")
### &emsp; 1.3 Wechat/H5 demo（depends on uni-app)
 &emsp;&emsp; see also [gitee]( https://gitee.com/anji-plus/captcha "码云")
 ![Wechat](https://captcha.anji-plus.com/static/8cm.jpg  "")&emsp;&emsp;![h5](https://images.gitee.com/uploads/images/2020/0429/174246_c33e3fa3_1728982.png "h5.png")
 &emsp;&emsp;&emsp;&emsp;Wechat Demo&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
uni-app H5 demo

# 2. Design Details
### &emsp; 2.1 UI Component
 &emsp;&emsp; 前端提供了Android、iOS、Futter、Uni-App、ReactNative、Vue、Angular、Html、Php等多端示例。
 
| blockPuzzle | clickWord |
| --- | --- |
|![blockPuzzle](https://captcha.anji-plus.com/static/blockPuzzle.png "滑动拼图")&emsp;|![clickWord](https://captcha.anji-plus.com/static/clickWord.png "点选文字")|
| 1-1 | 1-2 |
 

### &emsp; 2.2 Concept Related
| concept  | desc  |
| ------------ | ------------ |
| Captcha Type | blockPuzzle, clickWord|
| Check  |  user action: drag block or click workds,then check if it was human did|
| Verify  | bind user action with backend service. call captchaService.verification in backend service to prevent invalid access ,for example,directly call it |

### &emsp; 2.3 Main Features 
CAPTCHA stands for Completely Automated Public Turing test to tell Computers and Humans Apart. CAPTCHA determines whether the user is real or a spam robot. CAPTCHAs stretch or manipulate letters and numbers, and rely on human ability to determine which symbols they are.
 
Ajplus Captcha , an open source toolset for users,its main Features are as follows:
- Easy to integrate ui Component in your apps,support varies frontend UI,
- Support Integrate with Android、iOS、Futter、Uni-App、ReactNative、Vue、Angular、Html、Php
- No dependencies lib in core source,Easy to include in your backend service
- Core api is simple and Open to Extend,all instance initialized by JAVA SPI,Easy to add your custom Implement to form a new Captcha type。
- Support security feature

# 3. How to Integrate
①用户访问应用页面，请求显示行为验证码
②用户按照提示要求完成验证码拼图/点击
③用户提交表单，前端将第二步的输出一同提交到后台
④验证数据随表单提交到后台后，后台需要调用captchaService.verification做二次校验。
⑤第4步返回校验通过/失败到产品应用后端，再返回到前端。如下图所示。
![Sequence Diagram](https://captcha.anji-plus.com/static/shixu.png "时序图")

# 4. SourceCode Structure

![输入图片说明](https://images.gitee.com/uploads/images/2021/0207/112335_bd789fff_1600789.png "屏幕截图.png")

# 5. Dev & Run 
#### &emsp; 
- start backend service
  import source code into Eclipse or Intellij,
  start StartApplication class in package service/springboot。[online images](https://gitee.com/anji-plus/AJ-Captcha-Images)
- start frontend ui
  open source files in view/vue with your IDE like Visual Code，
```js
    npm install
    npm run dev

    DONE  Compiled successfully in 29587ms                       12:06:38
    I  Your application is running here: http://localhost:8081
``` 

# 6. Work Plan
  [issues](issues)

# 7. Connect Us

[Wechat group] (https://captcha.anji-plus.com/static/weixin.png)
<img src="https://captcha.anji-plus.com/static/weixin.png" width = "200" height = "200" div align=left />

 ### Have a try & enjoy it !!!  ☺

