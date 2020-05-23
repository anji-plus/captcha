package com.anji.captcha.demo.config;

import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.DefaultCaptchaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public CaptchaService captchaService(){
        CaptchaService s = new DefaultCaptchaServiceImpl();
        Properties config = new Properties();
        //TODO 从application.properties文件读取...
        s.init(config);
        return s;
    }


}
