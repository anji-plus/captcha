package service

import "captcha/model"

type CaptchaService interface {
	/**
	 * 配置初始化
	 */
	Init(config map[string]string)

	/**
	 * 获取验证码
	 * @param captchaVO
	 * @return
	 */
	Get(captcha model.Captcha) model.ResponseModel

	/**
	 * 核对验证码(前端)
	 * @param captchaVO
	 * @return
	 */
	Check(captcha model.Captcha) model.ResponseModel

	/**
	 * 二次校验验证码(后端)
	 * @param captchaVO
	 * @return
	 */
	Verification(captcha model.Captcha) model.ResponseModel

	/***
	 * 验证码类型
	 * 通过java SPI机制，接入方可自定义实现类，实现新的验证类型
	 * @return
	 */
	CaptchaType() string

	/**
	 * 历史资源清除(过期的图片文件，生成的临时图片...)
	 * @param config 配置项 控制资源清理的粒度
	 */
	Destroy(config map[string]string)
}
