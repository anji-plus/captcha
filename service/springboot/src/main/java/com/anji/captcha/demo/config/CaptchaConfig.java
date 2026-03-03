package com.anji.captcha.demo.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.anji.captcha.demo.service.CaptchaCacheServiceRedisImpl;
import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.anji.captcha.service.impl.DefaultCaptchaServiceImpl;

@Configuration
public class CaptchaConfig {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean(name = "AjCaptchaCacheService")
    @Primary
    public CaptchaCacheService captchaCacheService(){
        //缓存类型redis/local/....
        CaptchaCacheService ret = CaptchaServiceFactory.getCache("local");
        if(ret instanceof CaptchaCacheServiceRedisImpl){
            ((CaptchaCacheServiceRedisImpl)ret).setStringRedisTemplate(redisTemplate);
        }
        return ret;
    }
    
//    @Bean(name = "AjCaptchaProperties")
//    @Primary
//    public AjCaptchaProperties ajCaptchaProperties() {
//    			AjCaptchaProperties prop = new AjCaptchaProperties();
//		prop.setCacheType(ajCaptchaProperties().getCacheType().local);
//		prop.setWaterMark("Anji");
//		prop.setFontType("STHeiti-Light.ttc");
//		prop.setType(ajCaptchaProperties().getType().DEFAULT);
//		prop.setInterferenceOptions("1");
//		prop.setJigsaw("classpath:static/images/jigsaw.png");
//		prop.setPicClick("classpath:static/images/picclick.png");
//		prop.setSlipOffset("5");
//		prop.setAesStatus(true);
//		prop.setWaterFont("STHeiti-Light.ttc");
//		prop.setCacheNumber("100000");
//		prop.setTimingClear("3600");
//		return prop;
//    }
    
    @Bean(name = "CaptchaService")
    @Primary
    public CaptchaService captchaService(Properties config)	{
    	Properties prop = new Properties();
    	prop.setProperty("captcha.type", "blockPuzzle");
//    	prop.setProperty("water-font", "DejaVuSansMono-Bold.ttf");
    	prop.setProperty("captcha.font.type", "DejaVuSansMono-Bold.ttf");
    	return CaptchaServiceFactory.getInstance(prop);
	}
    
    
}
