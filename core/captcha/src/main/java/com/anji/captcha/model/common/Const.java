package com.anji.captcha.model.common;

/***
 * @author wongbin
 */
public interface Const {

    /**
     *滑块底图路径
     */
    String ORIGINAL_PATH_JIGSAW = "captcha.captchaOriginalPath.jigsaw";

    /***
     *点选底图路径
     */
    String ORIGINAL_PATH_PIC_CLICK = "captcha.captchaOriginalPath.pic-click";

    /**
     * 缓存local/redis...
     */
    String CAPTCHA_CACHETYPE = "captcha.cacheType";

    /**
     * 右下角水印文字(我的水印)
     */
    String CAPTCHA_WATER_MARK = "captcha.water.mark";

    /**
     * 点选文字验证码的文字字体(宋体)
     */
    String CAPTCHA_FONT_TYPE = "captcha.font.type";

    /**
     * 验证码类型default两种都实例化。
     */
    String CAPTCHA_TYPE = "captcha.type";

    /**
     * 滑动干扰项(0/1/2)
     */
    String CAPTCHA_INTERFERENCE_OPTIONS = "captcha.interference.options";

    /**
     * 底图自定义初始化
     */
    String CAPTCHA_INIT_ORIGINAL = "captcha.init.original";

    /**
     * 滑动误差偏移量
     */
    String CAPTCHA_SLIP_OFFSET = "captcha.slip.offset";

    /**
     * aes加密开关
     */
    String CAPTCHA_AES_STATUS = "captcha.aes.status";

    /**
     * 右下角水印字体(宋体)
     */
    String CAPTCHA_WATER_FONT = "captcha.water.font";

    /**
     * local缓存的阈值
     */
    String CAPTCHA_CACAHE_MAX_NUMBER = "captcha.cache.number";

    /**
     * 定时清理过期local缓存，秒
     */
    String CAPTCHA_TIMING_CLEAR_SECOND = "captcha.timing.clear";
}
