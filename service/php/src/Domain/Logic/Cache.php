<?php
declare(strict_types=1);

namespace Fastknife\Domain\Logic;

//use  Psr\SimpleCache;

use Fastknife\Exception\CacheException;
use Psr\SimpleCache\CacheInterface;

class Cache
{
    protected $config;

    protected $driver;

    protected $methodMap = [
        'get' => 'get',
        'set' => 'set',
        'delete' => 'delete',
        'has' => 'has'
    ];

    public function __construct($config)
    {
        if (isset($config['methods'])) {
            $this->methodMap = array_merge($this->methodMap, $config['methods']);
        }
        $this->driver = $this->getDriver($config['constructor']);
    }

    public function getDriver($callback)
    {
        if ($callback instanceof \Closure) {
            $result = $callback();
        } elseif (is_object($callback)) {
            $result = $callback;
        } elseif (is_array($callback)) {
            $result = call_user_func($callback);
        } elseif(is_string($callback) && class_exists($callback)){
            $result = new $callback;
        } else{
            throw new CacheException('缓存构造配置项错误：constructor');
        }
        return $this->checkDriver($result);
    }

    public function checkDriver($result){
        if ($result instanceof CacheInterface) {
            return $result;
        }
        foreach ($this->methodMap as $method) {
            if (! method_exists($result, $method)) {
                throw new CacheException('缓存构造配置项错误：methods,' . $method . "方法未设置");
            }
        }
        return $result;
    }

    public function getDriverMethod($name){
        return $this->methodMap[$name];
    }

    public function get($key, $default = null)
    {
        $method = $this->getDriverMethod('get');
        return $this->driver->$method($key, $default);
    }

    public function set($key, $value, $ttl = null)
    {
        $method = $this->getDriverMethod('set');
        return $this->driver->$method($key, $value, $ttl);
    }

    public function delete($key)
    {
        $method = $this->getDriverMethod('delete');
        return $this->driver->$method($key);
    }

    public function has($key)
    {
        $method = $this->getDriverMethod('has');
        return $this->driver->$method($key);
    }

}
