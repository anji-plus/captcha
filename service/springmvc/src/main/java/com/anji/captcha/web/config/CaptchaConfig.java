package com.anji.captcha.web.config;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean(name = "AjCaptchaCacheService")
    public CaptchaCacheService captchaCacheService(){
        //缓存类型redis/local/....
        return CaptchaServiceFactory.getCache("local");
    }

    @Bean
    @DependsOn("AjCaptchaCacheService")
    public CaptchaService captchaService(){
        Properties config = new Properties();
//        try {
//            try (InputStream input = CaptchaConfig.class.getClassLoader()
//                    .getResourceAsStream("application.properties")) {
//                config.load(input);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
        //各种参数设置....
        //缓存类型redis/local/....
        config.put("captcha.cacheType","local");
        config.put("captcha.water.mark", "我是底图");
        config.put("captcha.font.type", "隶书");
        config.put("captcha.type", CaptchaTypeEnum.DEFAULT.getCodeValue());  //default blockPuzzle  clickWord
        CaptchaService s = CaptchaServiceFactory.getInstance(config);
        s.init(config);
        return s;
    }

}