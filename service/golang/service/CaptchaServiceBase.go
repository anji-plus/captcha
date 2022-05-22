package service

/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
import (
	"captcha/model"
	"captcha/util"
	"fmt"
)

type CaptchaServiceBase struct {
	// logger := LoggerFactory.getLogger(getClass())

	ImageTypePng string

	HanZiSize int

	HanZiSizeHalf int // HanZiSize / 2
	//check校验坐标
	RedisCaptchaKey string //= "RUNNING:CAPTCHA:%s"

	//后台二次校验坐标
	RedisSecondCaptchaKey string //= "RUNNING:CAPTCHA:second-%s"

	ExpiredInSeconds int //= 2 * 60L

	ExpiredInThree int //= 3 * 60L

	waterMark string //= "我的水印"

	waterMarkFontStr string //= "WenQuanZhengHei.ttf"

	waterMarkFont string //水印字体

	slipOffset string //= "5"

	captchaAesStatus bool //= true

	clickWordFontStr string // = "WenQuanZhengHei.ttf"

	clickWordFont string //点选文字字体

	cacheType string //= "local"

	captchaInterferenceOptions int //= 0
}

func (t *CaptchaServiceBase) InitDefault() {
	fmt.Println("init...CaptchaServiceBase")
	t.cacheType = "local"
	t.HanZiSize = 24
	t.HanZiSizeHalf = t.HanZiSize / 2
	t.ExpiredInSeconds = 120
	t.ImageTypePng = "png"
	t.waterMark = ""
	t.waterMarkFontStr = "WenQuanZhengHei.ttf"
	t.captchaAesStatus = true
	t.slipOffset = "5"
	t.RedisSecondCaptchaKey = "RUNNING:CAPTCHA:second-%s"
	t.RedisCaptchaKey = "RUNNING:CAPTCHA:%s"
}

// Init 判断应用是否实现了自定义缓存，没有就使用内存
func (t *CaptchaServiceBase) Init(config map[string]string) {
	fmt.Println("init %s", t)
	//初始化底图
	/*boolean aBoolean = Boolean.parseBoolean(config.getProperty(Const.CAPTCHA_INIT_ORIGINAL))
	  if (!aBoolean) {
	      ImageUtils.cacheImage(config.getProperty(Const.ORIGINAL_PATH_JIGSAW),
	              config.getProperty(Const.ORIGINAL_PATH_PIC_CLICK))
	  }
	  logger.info("--->>>初始化验证码底图<<<---" + captchaType())
	  waterMark = config.getProperty(Const.CAPTCHA_WATER_MARK, "我的水印")
	  slipOffset = config.getProperty(Const.CAPTCHA_SLIP_OFFSET, "5")
	  waterMarkFontStr = config.getProperty(Const.CAPTCHA_WATER_FONT, "WenQuanZhengHei.ttf")
	  captchaAesStatus = Boolean.parseBoolean(config.getProperty(Const.CAPTCHA_AES_STATUS, "true"))
	  clickWordFontStr = config.getProperty(Const.CAPTCHA_FONT_TYPE, "WenQuanZhengHei.ttf")
	  //clickWordFontStr = config.getProperty(Const.CAPTCHA_FONT_TYPE, "SourceHanSansCN-Normal.otf")
	  cacheType = config.getProperty(Const.CAPTCHA_CACHETYPE, "local")
	  captchaInterferenceOptions = Integer.parseInt(
	          config.getProperty(Const.CAPTCHA_INTERFERENCE_OPTIONS, "0"))

	  // 部署在linux中，如果没有安装中文字段，水印和点选文字，中文无法显示，
	  // 通过加载resources下的font字体解决，无需在linux中安装字体
	  loadWaterMarkFont()

	  if (cacheType.equals("local")) {
	      logger.info("初始化local缓存...")
	      CacheUtil.init(Integer.parseInt(config.getProperty(Const.CAPTCHA_CACAHE_MAX_NUMBER, "1000")),
	              Long.parseLong(config.getProperty(Const.CAPTCHA_TIMING_CLEAR_SECOND, "180")))
	  }
	  if (config.getProperty(Const.HISTORY_DATA_CLEAR_ENABLE, "0").equals("1")) {
	      logger.info("历史资源清除开关...开启..." + captchaType())
	      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	          @Override
	          public void run() {
	              destroy(config)
	          }
	      }))
	  }
	  if (config.getProperty(Const.REQ_FREQUENCY_LIMIT_ENABLE, "0").equals("1")) {
	      if (limitHandler == null) {
	          logger.info("接口分钟内限流开关...开启...")
	          limitHandler = new FrequencyLimitHandler.DefaultLimitHandler(config, getCacheService(cacheType))
	      }
	  }*/
}

func (t *CaptchaServiceBase) getCacheService(cacheType string) CacheService {
	fmt.Printf("getCacheService %s\n", cacheType)
	return serviceFactory.GetCache(cacheType)
}

func (t *CaptchaServiceBase) Destroy(config map[string]string) {
	fmt.Printf("destroy %s\n", config)
}

var limitHandler FrequencyLimitHandler = &DefaultFreLimitHandler{}
var serviceFactory = CaptchaServiceFactory{}
var md5Util = util.MD5Util{}

func (t *CaptchaServiceBase) Get(data model.Captcha) model.ResponseModel {
	fmt.Printf("get %s\n", data)
	if limitHandler != nil {
		data.ClientUid = t.getValidateClientId(data)
		return limitHandler.validateGet(data)
	}
	return model.ResponseModel{}
}

func (t *CaptchaServiceBase) Check(data model.Captcha) model.ResponseModel {
	fmt.Printf("check %s\n", data)
	/*if (limitHandler != null) {
	      // 验证客户端
	     /* ResponseModel ret = limitHandler.validateCheck(captchaVO)
	      if(!validatedReq(ret)){
	          return ret
	      }
	      // 服务端参数验证
	      captchaVO.setClientUid(getValidateClientId(captchaVO))
	      return limitHandler.validateCheck(captchaVO)
	  }
	  return null*/
	return model.ResponseModel{}
}

func (t *CaptchaServiceBase) Verification(data model.Captcha) model.ResponseModel {
	fmt.Printf("verification %s\n", data)
	/*if (captchaVO == null) {
	      return RepCodeEnum.NULL_ERROR.parseError("captchaVO")
	  }
	  if (StringUtils.isEmpty(captchaVO.getCaptchaVerification())) {
	      return RepCodeEnum.NULL_ERROR.parseError("captchaVerification")
	  }
	  if (limitHandler != null) {
	      return limitHandler.validateVerify(captchaVO)
	  }
	  return null*/
	return model.ResponseModel{}
}

func (t *CaptchaServiceBase) ValidatedReq(resp model.ResponseModel) bool {
	return resp.IsSuccess()
}

func (t *CaptchaServiceBase) getValidateClientId(req model.Captcha) string {
	// 以服务端获取的客户端标识 做识别标志
	if len(req.BrowserInfo) > 0 {
		return md5Util.Md5(req.BrowserInfo)
	}
	// 以客户端Ui组件id做识别标志
	if len(req.ClientUid) > 0 {
		return req.ClientUid
	}
	return ""
}

func (t *CaptchaServiceBase) afterValidateFail(data model.ResponseModel) {
	/*if (limitHandler != null) {
	    // 验证失败 分钟内计数
	    String fails = String.format(FrequencyLimitHandler.LIMIT_KEY, "FAIL", data.getClientUid())
	    CaptchaCacheService cs = getCacheService(cacheType)
	    if (!cs.exists(fails)) {
	        cs.set(fails, "1", 60)
	    }
	    cs.increment(fails, 1)
	}*/

}

/**
 * 加载resources下的font字体，add by lide1202@hotmail.com
 * 部署在linux中，如果没有安装中文字段，水印和点选文字，中文无法显示，
 * 通过加载resources下的font字体解决，无需在linux中安装字体
 */
func (t CaptchaServiceBase) loadWaterMarkFont() {
	/*if (waterMarkFontStr.toLowerCase().endsWith(".ttf") || waterMarkFontStr.toLowerCase().endsWith(".ttc")
	          || waterMarkFontStr.toLowerCase().endsWith(".otf")) {
	      this.waterMarkFont = Font.createFont(Font.TRUETYPE_FONT,
	              getClass().getResourceAsStream("/fonts/" + waterMarkFontStr))
	              .deriveFont(Font.BOLD, HAN_ZI_SIZE / 2)
	  } else {
	      this.waterMarkFont = new Font(waterMarkFontStr, Font.BOLD, HAN_ZI_SIZE / 2)
	  }*/
	return
}

func (t CaptchaServiceBase) base64StrToImage(imgStr string, path string) bool {
	/*if (imgStr == null) {
	      return false
	  }
	  Base64.Decoder decoder = Base64.getDecoder()
	  try {
	      // 解密
	      byte[] b = decoder.decode(imgStr)
	      // 处理数据
	      for (int i = 0 i < b.length ++i) {
	          if (b[i] < 0) {
	              b[i] += 256
	          }
	      }
	      //文件夹不存在则自动创建
	      File tempFile = new File(path)
	      if (!tempFile.getParentFile().exists()) {
	          tempFile.getParentFile().mkdirs()
	      }
	      OutputStream out = new FileOutputStream(tempFile)
	      out.write(b)
	      out.flush()
	      out.close()
	      return true
	  } catch (Exception e) {
	      return false
	  }*/
	return false
}

/**
 * 解密前端坐标aes加密
 *
 * @param point
 * @return
 * @throws Exception
 */
func (t CaptchaServiceBase) decrypt(point string, key string) string {
	// return AESUtil.aesDecrypt(point, key)
	return ""
}

func (t CaptchaServiceBase) GetEnOrChLength(s string) int {
	/*enCount = 0
	  chCount = 0
	  for (i = 0 ; i < len(s) ;i++) {
	      int length = String.valueOf(s.charAt(i)).getBytes(StandardCharsets.UTF_8).length
	      if (length > 1) {
	          chCount++
	      } else {
	          enCount++
	      }
	  }
	  int chOffset = (HAN_ZI_SIZE / 2) * chCount + 5
	  int enOffset = enCount * 8
	  return chOffset + enOffset*/
	return 0
}
