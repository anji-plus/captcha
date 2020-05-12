## 2.3 前端接入
### 2.3.1 兼容性
IE8+、Chrome、Firefox.(其他未测试)
### 2.3.2 引入对应的css以及js文件
将view/html 文件夹copy到自己项目中

### 2.3.3 按顺序引入对应的文件
　　1.引入css文件verify.css　<br/>
　　2.按顺序引入js文件下js文件 jquery.min.js ,　md5.js, 　signUtil.js, 　crypto-js.js, 　ase.js, 　verify.js


基础示例
```javascript
准备初始化的容器

 
     <div id="content"></div>

       
滑动式 调用slideVerify方法初始化; 点选调用pointsVerify方法初始化

<script> 
            $('#content').slideVerify({
                vOffset : 5,	//误差量，根据需求自行调整
                mode:'fixed',
                imgSize : {       //图片的大小对象
                    width: '400px',
                    height: '200px',
                },
                barSize:{
                    width: '400px',
                    height: '40px',
                },
                ready : function() {  //加载完毕的回调
                },
                success : function(params) { //成功的回调
                    // 返回的二次验证参数
                    console.log(params,"params");
                },
                error : function() {        //失败的回调
                    //alert('验证失败！');
                }
            });
</script> 
```


## 注意事项
#### 其中 md5.js,  signUtil.js 根据参数传输格式 在自行修改 verify/js/verify.js  13行31行 自行修改
#### 后端请求地址根据部署情况到:verify/js/verify.js 第五行 var baseUrl = "https://mirror.anji-plus.com/api" 修改路劲

### 2.3.3 事件## 

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
