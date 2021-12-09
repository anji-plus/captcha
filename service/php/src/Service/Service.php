<?php
declare(strict_types=1);

namespace Fastknife\Service;


use Fastknife\Domain\Factory;

abstract class Service
{
    /**
     * @var array 工厂
     */
    protected $factory;

    public function __construct($config)
    {
        $defaultConfig = require dirname(__DIR__) . '/config.php';
        $config = array_merge($defaultConfig, $config);
        $this->factory = new Factory($config);
    }

    abstract public function get();

    abstract public function check($token, $pointJson);

    abstract public function verification($token, $pointJson);

}
