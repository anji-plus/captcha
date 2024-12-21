<?php

namespace app\controller;

use Fastknife\Exception\ParamException;
use Fastknife\Service\ClickWordCaptchaService;
use Fastknife\Service\BlockPuzzleCaptchaService;
use Fastknife\Service\Service;
use think\exception\HttpResponseException;
use think\facade\Validate;
use think\Response;


class Index
{
    public function index()
    {
        try {
            $service = $this->getCaptchaService();
            $data = $service->get();
        } catch (\Exception $e) {
            $this->error($e->getMessage());
        }
       /*  如果开发框架开启了debug、trace，导致前端项目无法正确获取到后台接口的返回数据，可以修改为本注释内的接口返回方式
        $response = [
            'error' => false,
            'repCode' => '0000',
            'repData' => $data,
            'repMsg' => null,
            'success' => true,
        ];
        return json($response);*/

        $this->success($data);
    }

    public function check()
    {
        $data = request()->post();
        try {
            $this->validate($data);
            $service = $this->getCaptchaService();
            $service->check($data['token'], $data['pointJson']);
        } catch (\Exception $e) {
            $this->error($e->getMessage());
        }
        /*  如果开发框架开启了debug、trace，导致前端项目无法正确获取到后台接口的返回数据，可以修改为本注释内的接口返回方式
        $response = [
            'error' => false,
            'repCode' => '0000',
            'repData' => [],
            'repMsg' => null,
            'success' => true,
        ];
        return json($response);*/
        $this->success([]);
    }

    protected function getCaptchaService()
    {
        $captchaType = request()->post('captchaType', null);
        $config = config('captcha');
        switch ($captchaType) {
            case "clickWord":
                $service = new ClickWordCaptchaService($config);
                break;
            case "blockPuzzle":
                $service = new BlockPuzzleCaptchaService($config);
                break;
            default:
                throw new ParamException('captchaType参数不正确！');
        }
        return $service;
    }

    protected function validate($data)
    {
        $rules = [
            'token' => ['require'],
            'pointJson' => ['require']
        ];
        $validate = Validate::rule($rules)->failException(true);
        $validate->check($data);
    }

    protected function success($data)
    {
        $response = [
            'error' => false,
            'repCode' => '0000',
            'repData' => $data,
            'repMsg' => null,
            'success' => true,
        ];
        throw new HttpResponseException(Response::create($response, 'json'));
    }


    protected function error($msg)
    {
        $response = [
            'error' => true,
            'repCode' => '6111',
            'repData' => null,
            'repMsg' => $msg,
            'success' => false,
        ];
        throw new HttpResponseException(Response::create($response, 'json'));
    }


}
