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

        'is_cache_pixel' => true, //是否开启缓存图片像素值，开启后能提升服务端响应性能（但要注意更换图片时，需要清除缓存）
    ],
    //水印
    'watermark' => [
        'fontsize' => 12,
        'color' => '#ffffff',
        'text' => '我的水印'
    ],
    'cache' => [
        'constructor' => \Fastknife\Utils\CacheUtils::class,//建议使用您自己项目框架的缓存驱动
        'options' => [
            // Fastknife\Utils\CacheUtils::class 中的配置
        ],
    ]
];
