package com.anji.captcha.util;

import java.util.Random;
import java.util.UUID;


public class RandomUtils {

    /**
     * 生成UUID
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    /**
     * 获取随机中文
     *
     * @return
     */
    public static String getRandomHan(String hanZi) {
        String ch = hanZi.charAt(new Random().nextInt(hanZi.length())) + "";
        return ch;
    }

    /**
     * 随机范围内数字
     * @param startNum
     * @param endNum
     * @return
     */
    public static Integer getRandomInt(int startNum, int endNum) {
        return new Random().nextInt(endNum-startNum) + startNum;
    }

}
