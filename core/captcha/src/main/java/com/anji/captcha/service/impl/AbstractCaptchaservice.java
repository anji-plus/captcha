/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.service.impl;

import com.anji.captcha.config.Container;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.AESUtil;
import com.anji.captcha.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

/**
 * Created by raodeming on 2019/12/25.
 */
public abstract class AbstractCaptchaservice implements CaptchaService, InitializingBean {


    protected static final String URL_PREFIX_HTTP = "http://";
    protected static final String URL_PREFIX_HTTPS = "https://";
    protected static final String IMAGE_TYPE_PNG = "png";

    protected static String HAN_ZI_FONT = "宋体";  //华文彩云   华文行楷   黑体  华文楷体  微软雅黑  隶书 华文彩云...

    protected static int HAN_ZI_SIZE = 25;

    protected static int HAN_ZI_SIZE_HALF = HAN_ZI_SIZE/2;

    //check校验坐标
    protected static String REDIS_CAPTCHA_KEY = "RUNNING:CAPTCHA:%s";

    //后台二次校验坐标
    protected static String REDIS_SECOND_CAPTCHA_KEY = "RUNNING:CAPTCHA:second-%s";

    protected static Long EXPIRESIN_SECONDS = 2 * 60L;

    protected static Long EXPIRESIN_THREE = 3 * 60L;

    protected CaptchaCacheService captchaCacheService;

    //判断应用是否实现了自定义缓存，没有就使用内存


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, CaptchaCacheService> map = Container.getBeanOfType(CaptchaCacheService.class);
        if(map == null || map.isEmpty()){
            captchaCacheService = Container.getBean("captchaCacheServiceMemImpl", CaptchaCacheService.class);
            return;
        }
        if(map.size()==1){
            captchaCacheService = Container.getBean("captchaCacheServiceMemImpl", CaptchaCacheService.class);
            return;
        }
        if(map.size()>=2){
            map.entrySet().stream().forEach(item ->{
                if(captchaCacheService != null){
                    return;
                }
                if(!"captchaCacheServiceMemImpl".equals(item.getKey())){
                    captchaCacheService = item.getValue();
                    return;
                }
            });
        }
    }

    /**
     *
     * 获取原生图片
     * @param urlOrPath
     * @return
     */
    protected static BufferedImage getBufferedImage (String urlOrPath) {
        if (StringUtils.isBlank(urlOrPath)) {
//            throw new CaptchaException("urlOrPath is empty.");
        }
        BufferedImage sampleImage = null;
        try {
            if (urlOrPath.startsWith(URL_PREFIX_HTTP) || urlOrPath.startsWith(URL_PREFIX_HTTPS)) {
                sampleImage = ImageIO.read(new URL(urlOrPath));
            } else {
                sampleImage = ImageIO.read(new File(urlOrPath));
            }
        } catch (IOException e ){
            e.printStackTrace();
        }
        return sampleImage;
    }

    /**
     * 图片转base64 字符串
     * @param templateImage
     * @return
     */
    protected String getImageToBase64Str (BufferedImage templateImage){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(templateImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();

        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes).trim();
    }


    public String getJigsawUrlOrPath() {
        return jigsawUrlOrPath;
    }

    public void setJigsawUrlOrPath(String jigsawUrlOrPath) {
        this.jigsawUrlOrPath = jigsawUrlOrPath;
    }

    public int getWordTotalCount() {
        return wordTotalCount;
    }

    public void setWordTotalCount(int wordTotalCount) {
        this.wordTotalCount = wordTotalCount;
    }

    public boolean isFontColorRandom() {
        return fontColorRandom;
    }

    public void setFontColorRandom(boolean fontColorRandom) {
        this.fontColorRandom = fontColorRandom;
    }

    /** 滑块拼图图片地址 */
    private String jigsawUrlOrPath;
    /** 点选文字 字体总个数 */
    private int wordTotalCount = 4;
    /** 点选文字 字体颜色是否随机 */
    private boolean fontColorRandom = Boolean.TRUE;

    public static boolean base64StrToImage(String imgStr, String path) {
        if (imgStr == null) {
            return false;
        }

        Base64.Decoder decoder = Base64.getDecoder();
        try {
            // 解密
            byte[] b = decoder.decode(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //文件夹不存在则自动创建
            File tempFile = new File(path);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解密前端坐标aes加密
     * @param point
     * @return
     * @throws Exception
     */
    public static String decrypt(String point, String key) throws Exception {
        return AESUtil.aesDecrypt(point, key);
    }


}
