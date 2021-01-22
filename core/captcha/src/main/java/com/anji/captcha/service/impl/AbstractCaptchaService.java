/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.service.impl;

import com.anji.captcha.model.common.Const;
import com.anji.captcha.model.common.RepCodeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.AESUtil;
import com.anji.captcha.util.CacheUtil;
import com.anji.captcha.util.ImageUtils;
import com.anji.captcha.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

/**
 * Created by raodeming on 2019/12/25.
 */
public abstract class AbstractCaptchaService implements CaptchaService {

    private static Logger logger = LoggerFactory.getLogger(AbstractCaptchaService.class);

    protected static final String IMAGE_TYPE_PNG = "png";

    protected static String HAN_ZI = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";

    protected static int HAN_ZI_SIZE = 25;

    protected static int HAN_ZI_SIZE_HALF = HAN_ZI_SIZE / 2;

    //check校验坐标
    protected static String REDIS_CAPTCHA_KEY = "RUNNING:CAPTCHA:%s";

    //后台二次校验坐标
    protected static String REDIS_SECOND_CAPTCHA_KEY = "RUNNING:CAPTCHA:second-%s";

    protected static Long EXPIRESIN_SECONDS = 2 * 60L;

    protected static Long EXPIRESIN_THREE = 3 * 60L;

    protected static String waterMark = "我的水印";

    protected static String waterMarkFontStr = "NotoSerif-Light.ttf";

    protected Font waterMarkFont;//水印字体

    protected static String slipOffset = "5";

    protected static Boolean captchaAesStatus = true;

    protected static String clickWordFontStr = "NotoSerif-Light.ttf";

    protected Font clickWordFont;//点选文字字体

    protected static String cacheType = "local";

    protected static int captchaInterferenceOptions = 0;

    //判断应用是否实现了自定义缓存，没有就使用内存
    @Override
    public void init(final Properties config) {
        //初始化底图
        boolean aBoolean = Boolean.parseBoolean(config.getProperty(Const.CAPTCHA_INIT_ORIGINAL));
        if (!aBoolean) {
            ImageUtils.cacheImage(config.getProperty(Const.ORIGINAL_PATH_JIGSAW),
                    config.getProperty(Const.ORIGINAL_PATH_PIC_CLICK));
        }
        logger.info("--->>>初始化验证码底图<<<---"+captchaType());
        waterMark = config.getProperty(Const.CAPTCHA_WATER_MARK, "我的水印");
        slipOffset = config.getProperty(Const.CAPTCHA_SLIP_OFFSET, "5");
        waterMarkFontStr = config.getProperty(Const.CAPTCHA_WATER_FONT, "SourceHanSansCN-Normal.otf");
        captchaAesStatus = Boolean.parseBoolean(config.getProperty(Const.CAPTCHA_AES_STATUS, "true"));
        clickWordFontStr = config.getProperty(Const.CAPTCHA_FONT_TYPE, "SourceHanSansCN-Normal.otf");
        cacheType = config.getProperty(Const.CAPTCHA_CACHETYPE, "local");
        captchaInterferenceOptions = Integer.parseInt(
        		config.getProperty(Const.CAPTCHA_INTERFERENCE_OPTIONS, "0"));

        //部署在linux中，如果没有安装中文字段，水印和点选文字，中文无法显示，通过加载resources下的font字体解决，无需在linux中安装字体
        loadWaterMarkFont();

        if (cacheType.equals("local")) {
            logger.info("初始化local缓存...");
            CacheUtil.init(Integer.parseInt(
            		config.getProperty(Const.CAPTCHA_CACAHE_MAX_NUMBER, "1000")),
                    Long.parseLong(config.getProperty(Const.CAPTCHA_TIMING_CLEAR_SECOND, "180")));
        }
        if(config.getProperty(Const.CAPTCHA_HISTORY_DATA_CLEAR,"0").equals("1")){
			logger.info("历史资源清除开关=1，开启...");
        	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					destroy(config);
				}
			}));
		}
		if(config.getProperty(Const.CAPTCHA_REQ_FREQUENCY_LIMIT,"0").equals("1")){
        	if(limitHandler == null) {
				logger.info("开启接口限流开关..");
				limitHandler = new FrequencyLimitHandler.DefaultLimitHandler(config, getCacheService(cacheType));
			}
		}
    }

    protected CaptchaCacheService getCacheService(String cacheType){
    	return CaptchaServiceFactory.getCache(cacheType);
	}

	@Override
	public void destroy(Properties config) {

	}

	private static FrequencyLimitHandler limitHandler;

	@Override
	public ResponseModel get(CaptchaVO captchaVO) {
		if(limitHandler!=null){
			return limitHandler.validateGet(captchaVO);
		}
		return null;
	}

	@Override
	public ResponseModel check(CaptchaVO captchaVO) {
		if(limitHandler!=null){
			return limitHandler.validateCheck(captchaVO);
		}
    	return null;
	}

	@Override
	public ResponseModel verification(CaptchaVO captchaVO) {
		if (captchaVO == null) {
			return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
		}
		if (StringUtils.isEmpty(captchaVO.getCaptchaVerification())) {
			return RepCodeEnum.NULL_ERROR.parseError("captchaVerification");
		}
		if(limitHandler!=null){
			return limitHandler.validateVerify(captchaVO);
		}
    	return null;
	}

	protected boolean validatedReq(ResponseModel resp){
    	return resp == null || resp.isSuccess();
	}

    /**
     * 加载resources下的font字体，add by lide1202@hotmail.com
     * 部署在linux中，如果没有安装中文字段，水印和点选文字，中文无法显示，
     * 通过加载resources下的font字体解决，无需在linux中安装字体
     */
	private void loadWaterMarkFont(){

        try{
            if(waterMarkFontStr.toLowerCase().endsWith(".ttf") || waterMarkFontStr.toLowerCase().endsWith(".ttc") || waterMarkFontStr.toLowerCase().endsWith(".otf")){
                this.waterMarkFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/" + waterMarkFontStr))
                        .deriveFont(Font.BOLD, HAN_ZI_SIZE / 2);
            }else{
                this.waterMarkFont = new Font(waterMarkFontStr, Font.BOLD, HAN_ZI_SIZE / 2);
            }
            if(clickWordFontStr.toLowerCase().endsWith(".ttf") || clickWordFontStr.toLowerCase().endsWith(".ttc") || clickWordFontStr.toLowerCase().endsWith(".otf")){
                this.clickWordFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/" + clickWordFontStr))
                        .deriveFont(Font.BOLD, HAN_ZI_SIZE);
            }else {
                this.clickWordFont = new Font(clickWordFontStr, Font.BOLD, HAN_ZI_SIZE);
            }
        }catch (Exception e){
            logger.error("load font error:{}", e);
        }
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

    /**
     * 滑块拼图图片地址
     */
    private String jigsawUrlOrPath;
    /**
     * 点选文字 字体总个数
     */
    private int wordTotalCount = 4;
    /**
     * 点选文字 字体颜色是否随机
     */
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
     *
     * @param point
     * @return
     * @throws Exception
     */
    public static String decrypt(String point, String key) throws Exception {
        return AESUtil.aesDecrypt(point, key);
    }

    protected static int getEnOrChLength(String s) {
        int enCount = 0;
        int chCount = 0;
        for (int i = 0; i < s.length(); i++) {
            int length = String.valueOf(s.charAt(i)).getBytes(StandardCharsets.UTF_8).length;
            if (length > 1) {
                chCount++;
            } else {
                enCount++;
            }
        }
        int chOffset = (HAN_ZI_SIZE / 2) * chCount + 5;
        int enOffset = enCount * 8;
        return chOffset + enOffset;
    }


}
