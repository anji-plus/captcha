<?php
declare(strict_types=1);

namespace Fastknife\Domain\Vo;
use Intervention\Image\Image;
class TemplateVo
{
    /**
     * @var Image
     */
    public $image;
    public $offset;
    public $src;


    /**
     * TemplateVo constructor.
     * @param \Intervention\Image\Image $image
     * @param $src
     */
    public function __construct(Image $image, $src, $offset)
    {
        $this->image = $image;
        $this->src = $src;
        $this->offset = $offset;
    }



}
