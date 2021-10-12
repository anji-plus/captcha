<?php
declare(strict_types=1);

namespace Fastknife\Domain;

use Fastknife\Domain\Logic\BaseData;
use Fastknife\Domain\Logic\BaseImage;
use Fastknife\Domain\Logic\BlockImage;
use Fastknife\Domain\Logic\WordImage;
use Fastknife\Domain\Logic\BlockData;
use Fastknife\Domain\Logic\WordData;

class Factory
{
    /**
     * 工厂方法
     * @param $type
     * @param array $config
     * @return BaseImage
     */
    public static function make($type, array $config): BaseImage
    {
        if ($type == 'block') {
            $data = new BlockData();
            $image = new BlockImage();
        } else {
            $data = new WordData();
            $image = new WordImage();
        }
        self::setCommon($image, $data, $config);
        if ($type == 'block') {
            self::setBlock($image, $data, $config);
        } else {
            self::setWord($image, $data);
        }
        return $image;
    }

    /**
     * 设置公共配置
     * @param BaseImage $image
     * @param BaseData $data
     * @param $config
     */
    protected static function setCommon(BaseImage $image, BaseData $data, $config)
    {
        //设置背景
        $background = $data->getBackground($config['block_puzzle']['backgrounds']);
        $image->setBackground($background);
        //获得字体数据
        $fontFile = $data->getFontFile($config['font_file']);
        $image
            ->setFontFile($fontFile)
            ->setWatermark($config['watermark']);
    }

    /**
     * 设置滑动验证码的配置
     * @param BlockImage $image
     * @param BlockData $data
     * @param $config
     */
    protected static function setBlock(BlockImage $image, BlockData $data, $config)
    {
        $templateVo = $data->getTemplate($image->getBackground(), $config['block_puzzle']['templates']);
        $interfereVo = $data->getInterfere($image->getBackground(), $templateVo, $config['block_puzzle']['templates']);
        $image
            ->setTemplateVo($templateVo)
            ->setInterfereVo($interfereVo);
    }

    /**
     * 设置文字验证码的配置
     * @param WordImage $image
     * @param WordData $data
     */
    protected static function setWord(WordImage $image, WordData $data)
    {
        //随机文字坐标
        $pointList = $data->getPointList(
            $image->getBackground()->getWidth(),
            $image->getBackground()->getHeight(),
            3
        );
        $worldList = $data->getWordList(count($pointList));
        $image
            ->setWordList($worldList)
            ->setWordList($worldList)
            ->setPoint($pointList);
    }

}
