AJ-Captcha · php
----

### 贡献者
隔壁老李@fastknife [https://gitee.com/fastknife][https://gitee.com/fastknife]


#### 介绍

这个类库使用 PHP实现了行为验证码。基于gd扩展生成滑动验证码和文字验证码。允许 phper定制验证码规则，并且不再使用 curl来请求第三方验证。


[范例演示](../../../../service/php/demo.md)


#### 注意事项
* 你需要打开 gd、 openssl扩展
* PHP版本至少需要7.1
* 此软件包自带缓存，如有需要请自行更换
* anji-plus/captcha前端默认请求头是 application/json请将自己替换为 application/x-www-form-urlencode

#### 如何使用
* 方法一：如果你没有使用 composer工具，你可以用 git下载这个软件包。为 apache/nginx配置的 Web站点使用最外层的目录作为根目录。接着手动引入对应的 service层文件以执行相应的操作。
* 方式二， composer安装，输入命令` require fastknife/ajcaptcha dev-master`，建议使用`https://mirrors.aliyun.com/composer`；
* 方式三，自定义覆盖了这个包。用 git下载到本地自建目录 xxx。接下来，安装这个包的依赖项` intervention/image,ext-openssl,ext-gd, psr/simple-cache`。接着修改 composer. json配置 autoload条目中的psr4自动装入。示例：
    ```
     "autoload": {
        "psr-4": {
          "app\\": "app"
        }
      }
    ```
  最后 composer update

* test 目录下示例了三种使用方式，phper可以参考使用。
#### 项目结构

```
AJ-Captcha for php
│
├─resources 资源
│	│
│	├─defaultImages 图片资源
│	│
│	└─fonts 字体
│
├─src 源码
│	│
│	├─Domian 领域层
│	│
│	├─Exception 异常
│	│
│	├─Service 服务层
│	│
│	└─Utils 工具类
│
├─test 测试实例
│	│
│	├─thinkphp thinkphp框架测试示例
│	│
│	├─laravel laravel框架测试示例
│	│
│	└─*.php 原生测试文件 配置文件
└─config.php 配置参考

```


这个软件如果对您有帮助，您可以点右上角 💘Star💘支持
