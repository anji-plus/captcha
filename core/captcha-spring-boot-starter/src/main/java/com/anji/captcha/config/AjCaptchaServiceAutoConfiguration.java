package com.anji.captcha.config;


import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.anji.captcha.util.ImageUtils;
import com.anji.captcha.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class AjCaptchaServiceAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(AjCaptchaServiceAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public CaptchaService captchaService(AjCaptchaProperties ajCaptchaProperties) {
        logger.info("自定义配置项：{}", ajCaptchaProperties.toString());
        Properties config = new Properties();
        config.put("captcha.cacheType", ajCaptchaProperties.getCacheType().name());
        config.put("captcha.water.mark", ajCaptchaProperties.getWaterMark());
        config.put("captcha.font.type", ajCaptchaProperties.getFontType());
        config.put("captcha.type", ajCaptchaProperties.getType().getCodeValue());
        config.put("captcha.captchaOriginalPath.jigsaw", ajCaptchaProperties.getJigsaw());
        config.put("captcha.captchaOriginalPath.pic-click", ajCaptchaProperties.getPicClick());
        CaptchaService s = CaptchaServiceFactory.getInstance(config);
        if ((StringUtils.isNotBlank(ajCaptchaProperties.getJigsaw()) && ajCaptchaProperties.getJigsaw().startsWith("classpath:"))
                || (StringUtils.isNotBlank(ajCaptchaProperties.getPicClick()) && ajCaptchaProperties.getPicClick().startsWith("classpath:"))) {
            //自定义resources目录下初始化底图
            config.put("captcha.init.original", "true");
            initializeBaseMap(ajCaptchaProperties.getJigsaw(), ajCaptchaProperties.getPicClick());
        }
        s.init(config);
        return s;
    }

    private static void initializeBaseMap(String jigsaw, String picClick) {
        ImageUtils.cacheBootImage(getResourcesImagesFile(jigsaw + "/original/*.png"),
                getResourcesImagesFile(jigsaw + "/slidingBlock/*.png"),
                getResourcesImagesFile(picClick + "/*.png"));
    }

    public static Map<String, String> getResourcesImagesFile(String path) {
        Map<String, String> imgMap = new HashMap<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(path);
            for (Resource resource : resources) {
                byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String string = Base64Utils.encodeToString(bytes);
                String filename = resource.getFilename();
                imgMap.put(filename, string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgMap;
    }
}
