package com.anji.captcha.demo.config;

import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.DefaultCaptchaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
public class CaptchaConfig {

    @Bean
    public CaptchaService captchaService(){
        CaptchaService s = new DefaultCaptchaServiceImpl();
        Properties config = new Properties();
        s.init(config);
        return s;
    }


}
