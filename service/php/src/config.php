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
        /*背景图片路径， 不填使用默认值， 支持string与array两种数据结构。string为默认图片的目录，array索引数组则为具体图片的地址*/
        'backgrounds' => [],

        /*模板图,格式同上支持string与array*/
        'templates' => [],

        'offset' => 10, //容错偏移量
    ],
    //水印
    'watermark' => [
        'fontsize' => 12,
        'color' => '#ffffff',
        'text' => '我的水印'
    ],
    'cache' => [
        /*类型string|array|function, 以call_user_fun方式获得缓存实例;
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
