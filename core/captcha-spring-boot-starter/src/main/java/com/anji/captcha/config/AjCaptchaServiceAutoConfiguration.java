package com.anji.captcha.config;


import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AjCaptchaServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaService captchaService(AjCaptchaProperties ajCaptchaProperties) {
        Properties config = new Properties();
        config.put("captcha.cacheType", ajCaptchaProperties.getCacheType().name());
        config.put("captcha.water.mark", ajCaptchaProperties.getWaterMark());
        config.put("captcha.font.type", ajCaptchaProperties.getFontType());
        config.put("captcha.type", ajCaptchaProperties.getType().getCodeValue());
        CaptchaService s = CaptchaServiceFactory.getInstance(config);
        s.init(config);
        return s;
    }
}
