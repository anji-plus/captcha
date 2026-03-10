package com.anji.captcha.config;

import com.anji.captcha.properties.AjCaptchaProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@AutoConfiguration
@EnableConfigurationProperties(AjCaptchaProperties.class)
@ComponentScan("com.anji.captcha")
@Import({AjCaptchaServiceAutoConfiguration.class, AjCaptchaStorageAutoConfiguration.class})
public class AjCaptchaAutoConfiguration {
}
