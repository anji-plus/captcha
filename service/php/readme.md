AJ-Captcha · php
----

#### 介绍

这是一个使用PHP实现行为验证码的类库。

 [范例演示](./demo.md)

#### 注意事项

* 您需要开启gd、openssl拓展
* 您的PHP版本至少需要7.1
* 本包自带缓存在，如有需要请自行替换

#### 如何使用
* 方式一，如果您没有使用composer工具，可以使用git下载本包。然后使用apache/nginx，将最外层目录做为根站点。然后您手动引入对应service层文件进行相应操作。
* 方式二，composer安装，输入命令 `composer require fastknife/ajcaptcha`; 
* 方式三，自定义重写本包。使用git下载到本地，自建目录 xxx。然后安装本包的依赖 `intervention/image、 ext-openssl、ext-gd、psr/simple-cache`。然后修改composer.json 在autoload项中配置psr4自动加载。示例：
    ```
     "autoload": {
        "psr-4": {
          "app\\": "app"
        }
      }
    ```
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
│	├─pear.config.yml 配置文件
│	│
│	└─pear.config.json 配置文件
└─config.php 配置参考

```



如果对您有帮助，您可以点右上角 💘Star💘支持
