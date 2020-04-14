/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

//给java文件，增加版权信息
public class CopyrightUtil {

    public static void main(String[] args) throws Exception {
        //java文件所在目录
        String dir = "D:\\anji-code\\githup\\behavior-captcha\\core\\captcha";
        File file = new File(dir);
        addCopyright4Directory(file);
    }

    public static void addCopyright4Directory(File file) throws Exception {
        File[] files = file.listFiles();
        if (files == null || files.length == 0){
            return;
        }

        for (File f : files) {
            if (f.isFile()) {
                addCopyright4File(f);
                System.out.println("文件===" + f.getName());
            } else {
                System.out.println("目录==" + f.getName());
                addCopyright4Directory(f);
            }
        }
    }

    public static void addCopyright4File(File file) throws Exception {
        String fileName = file.getName();
        boolean isJava = fileName.endsWith(".java");
        if (!isJava) {
//            log.info("This file is not java source file,filaName=" + fileName);
            return;
        }

        if (isJava) {
            // 版权字符串
            String copyright = "/*\n *Copyright © 2018 anji-plus\n "
                    + "*安吉加加信息技术有限公司\n " + "*http://www.anji-plus.com\n "
                    + "*All rights reserved.\n */\n";
            //尝试使用了RandomAccessFile.writeUTF，问题是开头字符是“NUL”，没能解决。
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String content = "";
            //读取一行，一定要加上“换行符”,Windows下可以直接用“\n”
            String lineSeperator ="\n";
            //lineSeperator = System.getProperty("line.separator")
            while ((line = br.readLine()) != null) {
                content += line + lineSeperator;
            }
            br.close();
            //把拼接后的字符串写回去
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(copyright);
            fileWriter.write(content);
            fileWriter.close();
        }

    }
}
