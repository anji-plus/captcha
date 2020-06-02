package com.anji.captcha.properties;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.anji.captcha.properties.AjCaptchaProperties.PREFIX;
import static com.anji.captcha.properties.AjCaptchaProperties.StorageType.local;

@Data
@ConfigurationProperties(PREFIX)
public class AjCaptchaProperties {
    public static final String PREFIX = "aj.captcha";

    /**
     * 验证码类型.
     */
    private CaptchaTypeEnum type = CaptchaTypeEnum.DEFAULT;

    /**
     * 滑动拼图底图路径.
     */
    private String jigsaw = "";

    /**
     * 点选文字底图路径.
     */
    private String picClick = "";


    /**
     * 右下角水印文字(我的水印).
     */
    private String waterMark = "我的水印";

    /**
     * 右下角水印字体(宋体).
     */
    private String waterFont = "宋体";

    /**
     * 点选文字验证码的文字字体(宋体).
     */
    private String fontType = "宋体";

    /**
     * 校验滑动拼图允许误差偏移量(默认5像素).
     */
    private String slipOffset = "5";

    /**
     * aes加密坐标开启或者禁用(true|false).
     */
    private Boolean aesStatus = true;

    /**
     * 缓存类型redis/local/....
     */
    private StorageType cacheType = local;

    public enum StorageType {
        /**
         * 内存.
         */
        local,
        /**
         * redis.
         */
        redis
    }
}
