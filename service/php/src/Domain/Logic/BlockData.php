<?php
declare(strict_types=1);

namespace Fastknife\Domain\Logic;


use Fastknife\Domain\Vo\OffsetVo;
use Fastknife\Domain\Vo\TemplateVo;
use Fastknife\Exception\BlockException;
use Fastknife\Utils\RandomUtils;
use Intervention\Image\ImageManagerStatic;
use Intervention\Image\Image;

class BlockData extends BaseData
{

    public function getBackground($backgrounds = null): Image
    {
        $dir = dirname(__DIR__, 3) . '/resources/defaultImages/jigsaw/original/';
        $backgrounds = $this->getDefaultImage($dir, $backgrounds);
        $src = $this->getRandImage($backgrounds);
        return ImageManagerStatic::make($src);
    }

    protected function getTemplateImages($templates = null)
    {
        $dir = dirname(__DIR__, 3) . '/resources/defaultImages/jigsaw/slidingBlock/';
        return $this->getDefaultImage($dir, $templates);
    }

    protected function exclude($templates, $exclude)
    {
        $temp = [];
        foreach ($templates as $template) {
            if ($template != $exclude) {
                $temp[] = $template;
            }
        }
        return $temp;
    }

    public function getTemplate(Image $background, $templates = []): TemplateVo
    {
        $bgWidth = intval($background->getWidth() / 2);
        $src = $this->getRandImage($this->getTemplateImages($templates));
        $image = ImageManagerStatic::make($src);
        $offset = $this->getRandOffset($bgWidth, $image->getWidth());
        $offsetVO = new OffsetVo($offset + $bgWidth, 0);
        return new TemplateVo($image, $src, $offsetVO);
    }

    public function getInterfere(Image $background, TemplateVo $template, $templates = [], $maxOffset = 0)
    {
        $templates = $this->exclude($this->getTemplateImages($templates), $template->src);
        $src = $this->getRandImage($this->getTemplateImages($templates));
        $image = ImageManagerStatic::make($src);
        $maxOffset = $maxOffset ?: $image->getWidth();
        do {
            $offset = $this->getRandOffset($background->getWidth(), $image->getWidth());
            if(abs($template->offset->x - $offset) > $maxOffset){
                $offsetVO = new OffsetVo($offset, 0);
                return new TemplateVo($image, $src, $offsetVO);
            }
        }while(true);
    }
    protected function getRandOffset(int $bgWidth,int $tempWidth){
        $diffWidth = $bgWidth - $tempWidth;
        return  RandomUtils::getRandomInt(0, $diffWidth-1);
    }

    /**
     * @param $originPoint
     * @param $targetPoint
     * @param $offset
     * @return void
     */
    public function check($originPoint, $targetPoint, $offset)
    {
        if (
            abs($originPoint->x - $targetPoint->x) <= $offset
            && $originPoint->y == $targetPoint->y
        ) {
            return ;
        }
        throw new BlockException('验证失败！');
    }

}
