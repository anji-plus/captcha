<?php
declare(strict_types=1);

namespace Fastknife\Service;

use Fastknife\Domain\Vo\PointVo;
use Fastknife\Exception\ParamException;
use Fastknife\Utils\AesUtils;
use Fastknife\Utils\RandomUtils;

class BlockPuzzleCaptchaService extends Service
{
    /**
     * 获取验证码图片信息
     * @return array
     */
    public function get(): array
    {
        $cacheEntity = $this->factory->makeCacheEntity();
        $blockImage = $this->factory->makeBlockImage();
        $blockImage->run();
        $data = [
            'originalImageBase64' => $blockImage->response(),
            'jigsawImageBase64' => $blockImage->response('template'),
            'secretKey' => RandomUtils::getRandomCode(16, 3),
            'token' => RandomUtils::getUUID(),
        ];
        //缓存
        $cacheEntity->set($data['token'], [
            'secretKey' => $data['secretKey'],
            'point' => $blockImage->getPoint()
        ]);
        return $data;
    }

    /**
     * 一次验证
     * @param $token
     * @param $pointJson
     */
    public function check($token, $pointJson)
    {
        $this->validate($token, $pointJson);
    }

    /**
     * 二次验证
     * @param $token
     * @param $pointJson
     */
    public function verification($token, $pointJson)
    {
        $this->validate($token, $pointJson, function($cacheEntity, $token){
            $cacheEntity->delete($token);
        });
    }


    /**
     * 验证
     * @param $token
     * @param $pointJson
     * @param null $callback
     */
    public function validate($token, $pointJson, $callback = null)
    {
        $cacheEntity = $this->factory->makeCacheEntity();
        $blockData = $this->factory->makeBlockData();

        $originData = $cacheEntity->get($token);
        if (empty($originData)) {
            throw new ParamException('参数校验失败：token');
        }
        $originPoint = $originData['point'];
        $secretKey = $originData['secretKey'];
        $pointJson = AesUtils::decrypt($pointJson, $secretKey);
        if ($pointJson === false) {
            throw new ParamException('aes验签失败！');
        }
        $targetPoint = json_decode($pointJson, true);
        $targetPoint = new PointVo($targetPoint['x'], $targetPoint['y']);
        $blockData->check($originPoint, $targetPoint);
        if($callback instanceof \Closure){
            $callback($cacheEntity, $token);
        }
    }
}
