<?php
declare(strict_types=1);

namespace Fastknife\Domain\Logic;


use Fastknife\Utils\RandomUtils;
use Intervention\Image\ImageManagerStatic;

class BaseData
{
    public function __construct()
    {
        ImageManagerStatic::configure(
            array_merge(['driver' => 'gd'])
        );
    }

    public const FONTSIZE = 25;

    /**
     * 获取字体包文件
     * @param string $file
     * @return string
     */
    public function getFontFile($file = '')
    {
        return $file && is_file($file) ?
            $file :
            dirname(dirname(dirname(__DIR__))) . '/resources/fonts/WenQuanZhengHei.ttf';
    }

    /**
     * 获得随机图片
     * @param $images
     * @return string
     * @throws \Exception
     */
    protected function getRandImage($images): string
    {
        $index = RandomUtils::getRandomInt(0, count($images) - 1);
        return $images[$index];
    }

    /**
     * 获取默认图片
     * @param $dir
     * @param $images
     * @return array|false
     */
    protected function getDefaultImage($dir, $images)
    {
        if(!empty($images)){
            if(is_array($images)){
                return $images;
            }
            if(is_string($images)) {
                $dir = $images;
            }
        }
        return  glob($dir . '*.png');
    }


}
