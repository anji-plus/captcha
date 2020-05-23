package com.anji.captcha.web.config;

import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.DefaultCaptchaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public CaptchaService captchaService(){
        CaptchaService s = DefaultCaptchaServiceImpl.getDefault();
        Properties config = new Properties();
        s.init(config);
        return s;
    }

}
