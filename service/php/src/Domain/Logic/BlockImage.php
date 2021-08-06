<?php
declare(strict_types=1);

namespace Fastknife\Domain\Logic;


use Intervention\Image\ImageManagerStatic as ImageManager;
use Intervention\Image\Image;
use Fastknife\Domain\Vo\PointVo;
use Fastknife\Domain\Vo\TemplateVo;

class BlockImage extends BaseImage
{
    const WHITE = [255, 255, 255, 1];

    /**
     * @var Image
     */
    protected $background;
    /**
     * @var TemplateVo
     */
    protected $templateVo;

    /**
     * @var TemplateVo
     */
    protected $interfereVo;

    /**
     * @return TemplateVo
     */
    public function getTemplateVo(): TemplateVo
    {
        return $this->templateVo;
    }

    /**
     * @param  $template
     * @return self
     */
    public function setTemplateVo(TemplateVo $templateVo): self
    {
        $this->templateVo = $templateVo;
        return $this;
    }

    /**
     * @return \Fastknife\Domain\Vo\TemplateVo
     */
    public function getInterfereVo(): TemplateVo
    {
        return $this->interfereVo;
    }

    /**
     * @param \Fastknife\Domain\Vo\TemplateVo $interfereVo
     * @return BlockImage
     */
    public function setInterfereVo(TemplateVo $interfereVo): self
    {

        $this->interfereVo = $interfereVo;
        return $this;
    }


    public function run()
    {
        $flag = false;
        $this->cutByTemplate($this->templateVo, $this->background, function ($param) use (&$flag) {
            if (! $flag) {
                //记录第一个点
                $this->setPoint(new PointVo($param[0], 5));//前端已将y值写死
                $flag = true;
            }
        });
        $this->cutByTemplate($this->interfereVo, $this->background);
        $this->makeWatermark($this->background);
    }

    public function cutByTemplate(TemplateVo $target, Image $background, $callable = null)
    {
        $template = $target->image;
        $width = $template->getWidth();
        $height = $template->getHeight();
        for ($x = 0; $x < $width; $x++) {
            for ($y = 0; $y < $height; $y++) {
                $a = $template->pickColor($x, $y)[3];
                $targetX = $x + $target->offset->x;
                $targetY = $y + $target->offset->y;
                if ($isOpacity = $this->isOpacity($a)) {    //如果不透明
                    if ($callable instanceof \Closure) {
                        $callable([$targetX, $targetY]);
                    }
                    $bgRgba = $background->pickColor($targetX, $targetY);
                    $template->pixel($bgRgba, $x, $y); //复制背景图片给模板
                    $blur = $this->getBlurValue($background, $targetX, $targetY); //模糊背景图选区
                    $this->background->pixel($blur, $targetX, $targetY);
                }
                if ($this->isBoundary($template, $isOpacity, $x, $y)) {
                    $background->pixel(self::WHITE, $targetX, $targetY);
                    $template->pixel(self::WHITE, $x, $y);
                }
            }
        }

    }

    /**
     * 是否为边框
     * @param Image $image
     * @param int $x
     * @param int $y
     * @param bool $isOpacity
     */
    public function isBoundary(Image $image, $isOpacity, int $x, int $y)
    {
        if ($x >= $image->width() - 1 || $y >= $image->height() - 1) {
            return false;
        }
        $right = $image->pickColor($x + 1, $y)[3];
        $down = $image->pickColor($x, $y + 1)[3];
        if (
            $isOpacity && ! $this->isOpacity($right)
            || ! $isOpacity && $this->isOpacity($right)
            || $isOpacity && ! $this->isOpacity($down)
            || ! $isOpacity && $this->isOpacity($down)
        ) {
            return true;
        }
        return false;
    }

    /**
     * @param Image $image
     * @param int $x
     * @param int $y
     */
    public function getBlurValue($image, $x, $y): array
    {
        $red = [];
        $green = [];
        $blue = [];
        $alpha = [];
        foreach ([
                     [0, 1], [0, -1],
                     [1, 0], [-1, 0],
                     [1, 1], [1, -1],
                     [-1, 1], [-1, -1],
                 ] as $distance) //周围八个点
        {
            $pointX = $x + $distance[0];
            $pointY = $y + $distance[1];
            if ($pointX < 0 || $pointX >= $image->getWidth() || $pointY < 0 || $pointY >= $image->height()) {
                continue;
            }

            [$r, $g, $b, $a] = $image->pickColor($pointX, $pointY);
            //边框取5个点，4个角取3个点，其余取8个点
            if ($this->isOpacity($a)) {
                $red[] = $r;
                $green[] = $g;
                $blue[] = $b;
                $alpha[] = $a;
            }
        }
        return [$this->avg($red), $this->avg($green), $this->avg($blue), $this->avg($alpha)];
    }

    protected function avg($array)
    {
        return intval(array_sum($array) / count($array));
    }

    /**
     * 是否不透明
     * @param $value
     * @return bool
     */
    public function isOpacity($value): bool
    {
        return $value > 0.5;
    }

    /**
     * 返回前端需要的格式
     * @return false|string[]
     */
    public function response($type = 'background')
    {
        $image = $type == 'background' ? $this->background : $this->templateVo->image;
        $result = $image->encode('data-url')->getEncoded();
        //返回图片base64的第二部分
        return explode(',', $result)[1];
    }

    /**
     * 用来调试
     */
    public function echo($type = 'background')
    {
        $image = $type == 'background' ? $this->background : $this->templateVo->image;
        die($image->response());
    }
}
