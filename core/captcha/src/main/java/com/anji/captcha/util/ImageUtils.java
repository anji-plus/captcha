/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.util;

import java.io.File;


public class ImageUtils {

    public static String getClickWordBgPath(String imagePath) {

        File file = new File(imagePath);
        String[] files = file.list();
        int randomNum = RandomUtils.getRandomInt(0, files.length);

        String path = files[randomNum];

        return imagePath + File.separator + path;
    }


    public static String getBlockPuzzleBgPath(String imagePath) {

        imagePath = imagePath + File.separator + "original";
        File file = new File(imagePath);
        String[] files = file.list();
        int randomNum = RandomUtils.getRandomInt(0, files.length);

        String path = files[randomNum];

        return imagePath + File.separator + path;
    }


    public static String getBlockPuzzleJigsawPath(String imagePath) {

        imagePath = imagePath + File.separator + "slidingBlock";
        File file = new File(imagePath);
        String[] files = file.list();
        int randomNum = RandomUtils.getRandomInt(0, files.length);

        String path = files[randomNum];

        return imagePath + File.separator + path;
    }


    public static void main(String[] args) {
        System.out.println(getBlockPuzzleJigsawPath("D:\\anji-code\\verification\\core\\captcha\\images\\jigsaw"));
    }
}
