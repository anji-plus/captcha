<?php
declare(strict_types=1);
/**
 * 请将该文件放置于config目录
 */
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
        'constructor' => function(){
            //在构造函数中传入自已的配置
            return \think\facade\Cache::store();
        }
    ]
];
