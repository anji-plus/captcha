package com.anji.captcha.service.test;

import com.anji.captcha.demo.StartApplication;
import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 针对同一个客户端组件的请求，做如下限制:
 * get
 * 1分钟内失败5次，锁定5分钟
 * 1分钟内不能超过120次。
 * check:
 * 1分钟内不超过600次
 * verify:
 * 1分钟内不超过600次
 *
 * @author WongBin
 * @date 2021/1/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class FrequencyLimitTest {

    @Autowired
    private CaptchaService captchaService;
    private CaptchaVO req = new CaptchaVO();
    private Logger logger = LoggerFactory.getLogger(getClass());
    int cnt = 100;
    boolean enableInterval = true;
    private String clientUid = "login-"+UUID.randomUUID().toString();

    @Before
    public void init() {
        req.setCaptchaType(CaptchaTypeEnum.BLOCKPUZZLE.getCodeValue());
        req.setClientUid(clientUid);
        req.setBrowserInfo("sssssssssssssssssss");
        req.setTs(System.currentTimeMillis());
        /*Properties config = new Properties();
        try {
            try (InputStream input = FrequencyLimitTest.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                config.load(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        config.setProperty("captcha.req.frequency.limit.enable", "1");
        config.setProperty("captcha.req.get.minute.limit", "10");
        config.setProperty("captcha.req.check.minute.limit", "30");
        config.setProperty("captcha.req.verify.minute.limit", "30");
        //config.setProperty("captcha.cacheType","redis");
        captchaService.init(config);*/
    }
    private void waitFor()throws Exception{
        if(enableInterval){
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testGet() throws Exception {
        int i = 0;
        while (i++ < cnt) {
            ResponseModel res = captchaService.get(req);
            logger.info(i + "=" + res.getRepCode() + "," + res.getRepMsg());
            //TimeUnit.SECONDS.sleep(1);
            waitFor();
        }

        //testCheck();
        //testVerify();
    }

    @Test
    public void testCheck() throws Exception {
        int i = 0;
        while (i++ < cnt) {
            req.setToken("xddfdf"+i);
            ResponseModel res = captchaService.check(req);
            logger.info(i + "=" + res.getRepCode() + "," + res.getRepMsg());
            waitFor();
        }
    }

    @Test
    public void testVerify() throws Exception {
        int i = 0;
        while (i++ < cnt) {
            req.setToken("xddfdf"+i);
            req.setCaptchaVerification("sdfddfdd");
            ResponseModel res = captchaService.verification(req);
            logger.info(i + "=" + res.getRepCode() + "," + res.getRepMsg());
            waitFor();
        }
    }

}
