<?php
declare(strict_types=1);

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
        /*类型string|array|function, 使用以访问回调的方式获得缓存实例;
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
