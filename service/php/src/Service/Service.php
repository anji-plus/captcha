<?php
declare(strict_types=1);

namespace Fastknife\Service;


abstract class Service
{
    /**
     * @var array 配置
     */
    protected $config;

    public function __construct($config)
    {
        $this->config = $config;
    }

}
