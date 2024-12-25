package com.anji.captcha.service.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.anji.captcha.demo.StartApplication;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.ClickWordCaptchaServiceImpl;
import com.anji.captcha.util.RandomUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by raodeming on 2020/4/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class CaptchaServiceImplTest {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private CaptchaCacheService cacheService;
    @Test
    public void get() {
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaType("blockPuzzle");
        ResponseModel responseModel = captchaService.get(captchaVO);
        Object repData = responseModel.getRepData();
        CaptchaVO captchaVO1 = (CaptchaVO)repData;
        String token = captchaVO1.getToken();
        System.out.println("token:" + token);
    }

    @Test
    public void testRandom(){
        int t = 10000;
        int wordCount = 4;
        List<Set> ret = new ArrayList();
        for(int i=0;i<t;i++) {
            Set s = getRandomWords(wordCount);
            //assert s.size()==wordCount;
            ret.add(s);
        }
        List<Set> ret1 = new ArrayList();
        for(int i=0;i<t;i++) {
            Set s = getRandomWords1(wordCount);
            //assert s.size()==wordCount;
            ret1.add(s);
        }
        System.out.println(ret.stream().filter(i->i.size()==wordCount).count());
        System.out.println(ret1.stream().filter(i->i.size()==wordCount).count());
        assert ret1.stream().filter(i->i.size()==wordCount).count() == t;
    }

    private Set<String> getRandomWords(int wordCount) {
        Set<String> currentWords  = new HashSet<>();
        for (int i = 0; i < wordCount; i++) {
            String word;
            do {
                word = RandomUtils.getRandomHan(ClickWordCaptchaServiceImpl.HAN_ZI);
                currentWords.add(word);
            } while (!currentWords.contains(word));
        }
        return currentWords;
    }

    private Set<String> getRandomWords1(int wordCount) {
        Set<String> words = new HashSet<>();
        int size = ClickWordCaptchaServiceImpl.HAN_ZI.length();
        for (; ; ) {
            String t = ClickWordCaptchaServiceImpl.HAN_ZI.charAt(RandomUtils.getRandomInt(size)) + "";
            words.add(t);
            if (words.size() >= wordCount) {
                break;
            }
        }
        return words;
    }

    @Test
    public void test(){
        Long increment = cacheService.increment("11", 2);

    }
}
