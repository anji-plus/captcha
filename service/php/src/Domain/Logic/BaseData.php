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

    public function getFontFile($file = '')
    {
        return $file && is_file($file) ? $file : dirname(dirname(dirname(__DIR__))) . '/resources/fonts/WenQuanZhengHei.ttf';
    }

    protected function getRandImage($images): string
    {
        $index = RandomUtils::getRandomInt(0, count($images) - 1);
        return $images[$index];
    }

    protected function getDefaultImage($dir, $images)
    {
        if (empty($images)) {
            $images = glob($dir . '*.png');
        }
        return $images;
    }


}
