package com.anji.captcha.service.impl;

import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * Created by raodeming on 2020/5/26.
 */
public class CaptchaServiceFactory {

    public static CaptchaService getInstance(Properties config){
        return instances.get(config.getProperty("captcha.type", "default"));
    }

    public static CaptchaCacheService getCache(String cacheType){
        return cacheService.get(cacheType);
    }

    public volatile static Map<String,CaptchaService> instances = new HashMap();
    public volatile static Map<String,CaptchaCacheService> cacheService = new HashMap();
    static {
        ServiceLoader<CaptchaCacheService> cacheServices = ServiceLoader.load(CaptchaCacheService.class);
        for(CaptchaCacheService item : cacheServices){
                cacheService.put(item.type(), item);
        }
        //Object t = this;
        ServiceLoader<CaptchaService> services = ServiceLoader.load(CaptchaService.class);
        for(CaptchaService item : services){
            instances.put(item.captchaType(), item);
        };
        System.out.println("supported-captchaTypes-service:"+instances.keySet().toString());
    }
}
