package com.anji.captcha.demo.config;

import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.DefaultCaptchaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public CaptchaService captchaService(){
        CaptchaService s = new DefaultCaptchaServiceImpl();
        Properties config = new Properties();
        try {
            try (InputStream input = CaptchaConfig.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                config.load(input);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //各种参数设置....
        //缓存类型redis/local/....
        config.put("captcha.cacheType","redis");
        s.init(config);
        return s;
    }


}
