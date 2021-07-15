<?php
declare(strict_types=1);

namespace Fastknife\Domain\Logic;


use Intervention\Image\AbstractFont as Font;
use Intervention\Image\Image;

class BaseImage
{
    protected $watermark;
    /**
     * @var \Intervention\Image\Image
     */
    protected $background;

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
    public function setPoint($point)
    {
        $this->point = $point;
        return $this;
    }


    protected function makeWatermark(Image $image)
    {
        if (! empty($this->watermark)) {
            $x = $image->getWidth() - intval(strlen($this->watermark['text']) / 3 * $this->watermark['fontsize'] * 0.618); //1汉字3个字节。  汉字长宽比约0.618; fontsize是以高度来计算像素的。
            $y = $image->getHeight() - intval($this->watermark['fontsize'] / 2) - 5;
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
     * @param mixed $background
     */
    public function setBackground(Image $background)
    {
        $this->background = $background;
        return $this;
    }

    /**
     * @return \Intervention\Image\Image
     */
    public function getBackground()
    {
        return $this->background;
    }

    public function setFontFile($file)
    {
        $this->fontFile = $file;
        return $this;
    }

}
