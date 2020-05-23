package com.anji.captcha.web.config;

import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.DefaultCaptchaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaConfig {


    @Bean(initMethod = "init")
    public CaptchaService captchaService(){
        //DefaultCaptchaServiceImpl.getInstance(CaptchaTypeEnum.BLOCKPUZZLE.getCodeValue());
        return new DefaultCaptchaServiceImpl();
    }


}
