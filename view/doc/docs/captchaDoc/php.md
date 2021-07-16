# php


### 贡献者
隔壁老李@fastknife [https://gitee.com/fastknife][https://gitee.com/fastknife]

这是一个使用PHP实现行为验证码的类库。

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
AJ-Captcha for php service/php目录
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

### 范例
详情请查看test目录的PHP源码
#### 配置说明
```php
return [
    'font_file' => '', //自定义字体包路径， 不填使用默认值
    //文字验证码
    'click_world' => [
        'backgrounds' => [] 
    ],
    //滑动验证码
    'block_puzzle' => [
        'backgrounds' => [], //背景图片路径， 不填使用默认值
        'templates' => [], //模板图
        'offset' => 10, //容错偏移量
    ],
    //水印
    'watermark' => [
        'fontsize' => 12,
        'color' => '#ffffff',
        'text' => '我的水印'
    ],
    'cache' => [
        'constructor' => \Fastknife\Utils\CacheUtils::class,//若您使用了框架，不推荐使用该配置
        'method' => [
            //遵守PSR-16规范不需要设置此项目（tp6, laravel,hyperf）。如tp5就不支持（delete => rm）,
            'get' => 'get', //获取
            'set' => 'set', //设置
            'delete' => 'delete',//删除
            'has' => 'has' //key是否存在
        ]
    ]
];
```
##### 缓存配置
    config.cache.constructor类型为string|array|function  使用以访问回调的方式获得缓存实例;
            laravel 配置：
                'constructor' => [Illuminate\Support\Facades\Cache::class, 'getFacadeRoot'] 或者 [Illuminate\Cache\CacheManager::class, 'store']
            tp6 配置
                 'constructor' => [think\Facade\Cache::class, 'instance'] 或者 [think\Cache::class, 'store']
            hyperf 配置
             'constructor' => [Hyperf\Cache\CacheManager::class, 'getDriver'] 或者 [think\Cache::class, 'store']
        自定义：
            'constructor' => function(){
                //在构造函数中传入自已的配置
                return think\Cache::store('redis');
            }
        */
缓存类遵守psr-16规范，生成缓存源码如下：
```php
    public function getDriver($callback)
    {
        if ($callback instanceof \Closure) {
            $result = $callback();
        } elseif (is_object($callback)) {
            $result = $callback;
        } elseif (is_array($callback)) {
            $result = call_user_func($callback);
        } elseif(is_string($callback) && class_exists($callback)){
            $result = new $callback;
        } else{
            throw new CacheException('缓存构造配置项错误：constructor');
        }
        return $this->checkDriver($result);
    }
    public function checkDriver($result){
        if ($result instanceof CacheInterface) {
            return $result;
        }
        foreach ($this->methodMap as $method) {
            if (! method_exists($result, $method)) {
                throw new CacheException('缓存构造配置项错误：methods,' . $method . "方法未设置");
            }
        }
        return $result;
    }
```

#### 获取滑动验证码
```php
public function get(){
        $config = require '../src/config.php';
        $service = new BlockPuzzleCaptchaService($config);
        $data = $service->get();
        echo json_encode([
            'error' => false,
            'repCode' => '0000',
            'repData' => $data,
            'repMsg' => null,
            'success' => true,
        ]);
```
#### 滑动验证
```php
 $service = new BlockPuzzleCaptchaService($config);
        $data = $_REQUEST;
        $msg = null;
        $error = false;
        $repCode = '0000';
        try {
            $service->check($data['token'], $data['pointJson']);
        } catch (\Exception $e) {
            $msg = $e->getMessage();
            $error = true;
            $repCode = '6111';
        }
        echo json_encode([
            'error' => $error,
            'repCode' => $repCode,
            'repData' => null,
            'repMsg' => $msg,
            'success' => ! $error,
        ]);
```
#### 获取文字验证码
```php
    public function get()
    {
        $config = require '../src/config.php';
        $service = new ClickWordCaptchaService($config);
        $data = $service->get();
        echo json_encode([
            'error' => false,
            'repCode' => '0000',
            'repData' => $data,
            'repMsg' => null,
            'success' => true,
        ]);
    }
```
#### 文字验证
```php
    public function check()
    {
        $config = require '../src/config.php';
        $service = new ClickWordCaptchaService($config);
        $data = $_REQUEST;
        $msg = null;
        $error = false;
        $repCode = '0000';
        try {
            $service->check($data['token'], $data['pointJson']);
        } catch (\Exception $e) {
            $msg = $e->getMessage();
            $error = true;
            $repCode = '6111';
        }
        echo json_encode([
            'error' => $error,
            'repCode' => $repCode,
            'repData' => null,
            'repMsg' => $msg,
            'success' => ! $error,
        ]);
    }
```

本包后续更新 ThinkPHP、Hyperf 等框架，请持续关注 https://gitee.com/fastknife/aj-cachapt


如果对您有帮助，您可以点右上角 💘Star💘支持

