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
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.AESUtil;
import com.anji.captcha.util.ImageUtils;
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
//@Component(value = "defaultCaptchaServiceImpl")
//@Primary
//@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultCaptchaServiceImpl extends AbstractCaptchaservice implements CaptchaService{

    private static Logger logger = LoggerFactory.getLogger(DefaultCaptchaServiceImpl.class);

    //@Value("${captcha.captchaOriginalPath.jigsaw:}")
    private String captchaOriginalPathJigsaw;

    //@Value("${captcha.captchaOriginalPath.pic-click:}")
    private String captchaOriginalPathClick;

    //@Value("${captcha.aes.key:XwKsGlMcdPMEhR1B}")
    private String aesKey;

    protected static String REDIS_SECOND_CAPTCHA_KEY = "RUNNING:CAPTCHA:second-%s";
    //protected CaptchaCacheService captchaCacheService;

    private volatile static Map<String,CaptchaService> instances = new HashMap();

    @Override
    public String captchaType() {
        return "default";
    }
    public static CaptchaService getInstance(String type){
        return instances.get(type);
    }
    public static CaptchaService getDefault(){
        return instances.get("default");
    }
    @Override
    public void init(Properties config) {
        super.init(config);

        captchaOriginalPathJigsaw = config.getProperty("captcha.captchaOriginalPath.jigsaw");
        captchaOriginalPathClick = config.getProperty("captcha.captchaOriginalPath.pic-click");
        aesKey = config.getProperty("captcha.aes.key");

        //Object t = this;
        ServiceLoader<CaptchaService> services = ServiceLoader.load(CaptchaService.class);
        for(CaptchaService item : services){
           instances.put(item.captchaType(), item);
        };
        System.out.println("supported-captchaTypes-service:"+instances.keySet().toString());
        //初始化底图
        ImageUtils.cacheImage(captchaOriginalPathJigsaw, captchaOriginalPathClick);
        logger.info("--->>>初始化验证码底图<<<---");
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
        if (captchaVO.getCaptchaType().equals("blockPuzzle")) {
            captchaVO.setCaptchaOriginalPath(captchaOriginalPathJigsaw);
        } else {
            captchaVO.setCaptchaOriginalPath(captchaOriginalPathClick);
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
            return RepCodeEnum.NULL_ERROR.parseError("captchaVerification");
        }
        try {
            //aes解密
            String s = AESUtil.aesDecrypt(captchaVO.getCaptchaVerification(), aesKey);
            String token = s.split("---")[0];
            String pointJson = s.split("---")[1];
            //取坐标信息
            String codeKey = String.format(REDIS_SECOND_CAPTCHA_KEY, token);
            if (!captchaCacheService.exists(codeKey)) {
                return ResponseModel.errorMsg(RepCodeEnum.API_CAPTCHA_INVALID);
            }
            String redisData = captchaCacheService.get(codeKey);
            //二次校验取值后，即刻失效
            captchaCacheService.delete(codeKey);
            if (!pointJson.equals(redisData)) {
                return ResponseModel.errorMsg(RepCodeEnum.API_CAPTCHA_COORDINATE_ERROR);
            }
        } catch (Exception e) {
            logger.error("验证码坐标解析失败", e);
            return ResponseModel.errorMsg(e.getMessage());
        }
        return ResponseModel.success();
    }

}
