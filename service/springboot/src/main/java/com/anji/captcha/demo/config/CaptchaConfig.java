package com.anji.captcha.demo.config;

import com.anji.captcha.demo.service.CaptchaCacheServiceRedisImpl;
import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CaptchaConfig {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean(name = "AjCaptchaCacheService")
    @Primary
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config){
        //缓存类型redis/local/....
        CaptchaCacheService ret = CaptchaServiceFactory.getCache(config.getCacheType().name());
        if(ret instanceof CaptchaCacheServiceRedisImpl){
            ((CaptchaCacheServiceRedisImpl)ret).setStringRedisTemplate(redisTemplate);
        }
        return ret;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages/messages","captcha/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
