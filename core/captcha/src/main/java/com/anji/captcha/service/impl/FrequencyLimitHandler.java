package com.anji.captcha.service.impl;

import com.anji.captcha.model.common.Const;
import com.anji.captcha.model.common.RepCodeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;

import java.util.Objects;
import java.util.Properties;

/**
 *
 *
 *@author WongBin
 *@date 2021/1/21
 */
public interface FrequencyLimitHandler {

	/**
	 * get 接口限流
	 *
	 * @param captchaVO
	 * @return
	 */
	ResponseModel validateGet(CaptchaVO captchaVO);

	/**
	 * check接口限流
	 * @param captchaVO
	 * @return
	 */
	ResponseModel validateCheck(CaptchaVO captchaVO);

	/**
	 * verify接口限流
	 * @param captchaVO
	 * @return
	 */
	ResponseModel validateVerify(CaptchaVO captchaVO);


	/***
	 * 验证码接口限流: 客户端ClientUid 组件实例化时初始化一次，如UUID
	 *
	 * 针对同一个客户端的请求，做如下限制:
	 * get
	 * 	 1分钟内失败5次，锁定5分钟
	 * 	 1分钟内不能超过120次。
	 * check:
	 *   1分钟内不超过600次
	 * verify:
	 *   1分钟内不超过600次
	 */
	class DefaultLimitHandler implements FrequencyLimitHandler{
		private Properties config;
		private CaptchaCacheService cacheService;

		public DefaultLimitHandler(Properties config,CaptchaCacheService cacheService) {
			this.config = config;
			this.cacheService = cacheService;
		}

		private String getClientCId(CaptchaVO input,String type){
			return "CAPTCHA.REQ.LIMIT-"+type+"-"+input.getClientUid();
		}

		@Override
		public ResponseModel validateGet(CaptchaVO d) {
			String key = getClientCId(d,"GET");
			String lockKey = getClientCId(d,"LOCK");
			// 失败次数过多，锁定
			if(Objects.nonNull(cacheService.get(lockKey))){
				return ResponseModel.errorMsg(RepCodeEnum.API_REQ_LOCK_GET_ERROR);
			}
			String v = cacheService.get(key);
			if(Objects.isNull(v)){
				cacheService.set(key,"1",60);
				v = "1";
			}
			cacheService.increment(key,1);
			// 1分钟内请求次数过多
			if(Long.valueOf(v) > Long.valueOf(
					config.getProperty(Const.CAPTCHA_REQ_GET_MINUTE_LIMIT,"120"))){
				return ResponseModel.errorMsg(RepCodeEnum.API_REQ_LIMIT_GET_ERROR);
			}
			// 失败次数验证
			String failKey = getClientCId(d,"FAIL");
			v = cacheService.get(failKey);
			if(Objects.isNull(v)) {
				return null;
			}
			if(Long.valueOf(v)>=Long.valueOf(
					config.getProperty(Const.CAPTCHA_REQ_GET_LOCK_LIMIT,"5"))){
				cacheService.set(lockKey,"1",Long.valueOf(
						config.getProperty(Const.CAPTCHA_REQ_GET_LOCK_LIMIT,"300")));
				return ResponseModel.errorMsg(RepCodeEnum.API_REQ_LOCK_GET_ERROR);
			}
			return null;
		}

		@Override
		public ResponseModel validateCheck(CaptchaVO d) {
			String key = getClientCId(d,"CHECK");
			String v = cacheService.get(key);
			if(Objects.isNull(v)){
				cacheService.set(key,"1",60);
			}
			cacheService.increment(key,1);
			if(Long.valueOf(v)>=Long.valueOf(
					config.getProperty(Const.CAPTCHA_REQ_CHECK_MINUTE_LIMIT,"600"))){
				return ResponseModel.errorMsg(RepCodeEnum.API_REQ_LIMIT_CHECK_ERROR);
			}
			return null;
		}

		@Override
		public ResponseModel validateVerify(CaptchaVO d) {
			String key = getClientCId(d,"VERIFY");
			String v = cacheService.get(key);
			if(Objects.isNull(v)){
				cacheService.set(key,"1",60);
			}
			cacheService.increment(key,1);
			if(Long.valueOf(v)>=Long.valueOf(
					config.getProperty(Const.CAPTCHA_REQ_VALIDATE_MINUTE_LIMIT,"600"))){
				return ResponseModel.errorMsg(RepCodeEnum.API_REQ_LIMIT_VERIFY_ERROR);
			}
			return null;
		}
	}

}