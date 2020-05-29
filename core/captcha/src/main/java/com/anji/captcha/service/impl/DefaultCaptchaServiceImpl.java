/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.service.impl;

import com.anji.captcha.model.common.RepCodeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * Created by raodeming on 2019/12/25.
 */
public class DefaultCaptchaServiceImpl extends AbstractCaptchaService{

    private static Logger logger = LoggerFactory.getLogger(DefaultCaptchaServiceImpl.class);

    protected static String REDIS_SECOND_CAPTCHA_KEY = "RUNNING:CAPTCHA:second-%s";
    private CaptchaCacheService captchaCacheService;

    private volatile static Map<String,CaptchaService> instances = new HashMap();

    @Override
    public String captchaType() {
        return "default";
    }
    public static CaptchaService getInstance(String type){
        return instances.get(type);
    }
    public static CaptchaService getDefault(Properties config){
        return instances.get("default");
    }
    @Override
    public void init(Properties config) {
        super.init(config);
        captchaCacheService = CaptchaServiceFactory.getCache(cacheType);
        ServiceLoader<CaptchaService> services = ServiceLoader.load(CaptchaService.class);
        for(CaptchaService item : services){
           instances.put(item.captchaType(), item);
        }
        if(captchaCacheService == null){
            throw new RuntimeException("captchaCacheService is null,[captcha.cacheType]="+config.getProperty("captcha.cacheType"));
        }
        System.out.println("supported-captchaTypes-service:"+instances.keySet().toString());
    }

    private CaptchaService getService(String captchaType){
        return instances.get(captchaType);
    }

    @Override
    public ResponseModel get(CaptchaVO captchaVO) {
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        if (StringUtils.isEmpty(captchaVO.getCaptchaType())) {
            return RepCodeEnum.NULL_ERROR.parseError("类型");
        }
        return getService(captchaVO.getCaptchaType()).get(captchaVO);
    }

    @Override
    public ResponseModel check(CaptchaVO captchaVO) {
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        if (StringUtils.isEmpty(captchaVO.getCaptchaType())) {
            return RepCodeEnum.NULL_ERROR.parseError("类型");
        }
        if (StringUtils.isEmpty(captchaVO.getToken())) {
            return RepCodeEnum.NULL_ERROR.parseError("token");
        }
        return getService(captchaVO.getCaptchaType()).check(captchaVO);
    }

    @Override
    public ResponseModel verification(CaptchaVO captchaVO) {
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        if (StringUtils.isEmpty(captchaVO.getCaptchaVerification())) {
            return RepCodeEnum.NULL_ERROR.parseError("二次校验参数");
        }
        return getService(captchaVO.getCaptchaType()).verification(captchaVO);
    }

}
