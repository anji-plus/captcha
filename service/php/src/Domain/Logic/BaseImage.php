<?php
declare(strict_types=1);

namespace Fastknife\Domain\Logic;


use Fastknife\Domain\Vo\BackgroundVo;
use Intervention\Image\AbstractFont as Font;
use Intervention\Image\Image;

abstract class BaseImage
{
    protected $watermark;

    /**
     * @var BackgroundVo
     */
    protected $backgroundVo;

    protected $fontFile;
    protected $point;

    /**
     * @return mixed
     */
    public function getPoint()
    {
        return $this->point;
    }

    /**
     * @param $point
     * @return WordImage
     */
    public function setPoint($point):self
    {
        $this->point = $point;
        return $this;
    }


    protected function makeWatermark(Image $image)
    {
        if (! empty($this->watermark)) {
            //1汉字3个字节。  汉字长宽比约0.618; fontsize是以高度来计算像素的。
            $offsetX = intval(strlen($this->watermark['text']) / 3 * $this->watermark['fontsize'] * 0.618);
            $offsetY = intval($this->watermark['fontsize'] / 2);
            $x = $image->getWidth() - $offsetX;
            $y = $image->getHeight() - $offsetY;
            $image->text($this->watermark['text'], $x, $y, function (Font $font) {
                $font->file($this->fontFile);
                $font->size($this->watermark['fontsize']);
                $font->color($this->watermark['color']);
                $font->align('center');
                $font->valign('center');
            });
        }
    }


    /**
     * @param mixed $watermark
     * @return self
     */
    public function setWatermark($watermark): self
    {
        $this->watermark = $watermark;
        return $this;
    }


    /**
     * @param BackgroundVo $backgroundVo
     * @return $this
     */
    public function setBackgroundVo(BackgroundVo $backgroundVo):self
    {
        $this->backgroundVo = $backgroundVo;
        return $this;
    }

    /**
     * @return BackgroundVo
     */
    public function getBackgroundVo(): BackgroundVo
    {
        return $this->backgroundVo;
    }

    /**
     * @param $file
     * @return static
     */
    public function setFontFile($file): self
    {
        $this->fontFile = $file;
        return $this;
    }

    public abstract function run();
}
