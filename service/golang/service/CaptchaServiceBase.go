package service

/*
 *Copyright © 2018 anjiplus
 *安吉加加信息技术有限公司
 *http://www.anjiplus.com
 *All rights reserved.
 */
import (
	"anjiplus/captcha/const"
	"anjiplus/captcha/model"
	"anjiplus/captcha/util"
	"fmt"
	"os"
	"strconv"
	"unicode/utf8"
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
func (t *CaptchaServiceBase) Init(config model.Properties) {
	fmt.Printf("init %s\n", t)
	//初始化底图
	b := config.GetProperty(constant.CAPTCHA_INIT_ORIGINAL)
	if b == "true" {
		util.ImageUtils{}.CacheImage(config.GetProperty(constant.ORIGINAL_PATH_JIGSAW),
			config.GetProperty(constant.ORIGINAL_PATH_PIC_CLICK))
	}
	//logger.info("--->>>初始化验证码底图<<<---" + captchaType())
	t.waterMark = config.GetPropertyDef(constant.CAPTCHA_WATER_MARK, "我的水印")
	t.slipOffset = config.GetPropertyDef(constant.CAPTCHA_SLIP_OFFSET, "5")
	t.waterMarkFontStr = config.GetPropertyDef(constant.CAPTCHA_WATER_FONT, "WenQuanZhengHei.ttf")
	//t.captchaAesStatus = bool(config.GetProperty(constant.CAPTCHA_AES_STATUS, "true"))
	t.clickWordFontStr = config.GetPropertyDef(constant.CAPTCHA_FONT_TYPE, "WenQuanZhengHei.ttf")
	t.clickWordFontStr = config.GetPropertyDef(constant.CAPTCHA_FONT_TYPE, "SourceHanSansCN-Normal.otf")
	t.cacheType = config.GetPropertyDef(constant.CAPTCHA_CACHETYPE, "local")
	t.captchaInterferenceOptions, _ = strconv.Atoi(config.GetPropertyDef(constant.CAPTCHA_INTERFERENCE_OPTIONS, "0"))

	// 部署在linux中，如果没有安装中文字段，水印和点选文字，中文无法显示，
	// 通过加载resources下的font字体解决，无需在linux中安装字体
	t.loadWaterMarkFont()

	if t.cacheType == "local" {
		//logger.info("初始化local缓存...")
		util.CacheUtil{}.Init(config.GetPropertyDef(constant.CAPTCHA_CACAHE_MAX_NUMBER, "1000"),
			config.GetPropertyDef(constant.CAPTCHA_TIMING_CLEAR_SECOND, "180"))
	}
	if config.GetPropertyDef(constant.HISTORY_DATA_CLEAR_ENABLE, "0") == "1" {
		//logger.info("历史资源清除开关...开启..." + captchaType())
		/*Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    @Override
		    public void run() {
		        destroy(config)
		    }
		}))*/
	}
	if config.GetPropertyDef(constant.REQ_FREQUENCY_LIMIT_ENABLE, "0") == "1" {
		if limitHandler == nil {
			//logger.info("接口分钟内限流开关...开启...")
			//limitHandler = DefaultFreLimitHandler{}(config, t.getCacheService(t.cacheType))
		}
	}
}

func (t *CaptchaServiceBase) getCacheService(cacheType string) CaptchaCacheService {
	fmt.Printf("getCacheService %s\n", cacheType)
	return serviceFactory.GetCache(cacheType)
}

func (t *CaptchaServiceBase) Destroy(config model.Properties) {
	fmt.Printf("destroy %s\n", config)
}

var limitHandler FrequencyLimitHandler = &DefaultFreLimitHandler{}
var serviceFactory = CaptchaServiceFactory{}
var md5Util = util.MD5Util{}

func (t *CaptchaServiceBase) Get(data model.Captcha) model.ResponseModel {
	fmt.Printf("get %s\n", data)
	if limitHandler != nil {
		data.ClientUid = t.getValidateClientId(data)
		return limitHandler.ValidateGet(data)
	}
	return model.ResponseModel{}
}

func (t *CaptchaServiceBase) Check(data model.Captcha) model.ResponseModel {
	fmt.Printf("check %s\n", data)
	if limitHandler != nil {
		// 验证客户端
		ret := limitHandler.ValidateCheck(data)
		if !t.ValidatedReq(ret) {
			return ret
		}
		// 服务端参数验证
		data.ClientUid = t.getValidateClientId(data)
		return limitHandler.ValidateCheck(data)
	}
	return model.ResponseModel{RepCode: constant.SuccessFlag}
}

func (t *CaptchaServiceBase) Verification(data model.Captcha) model.ResponseModel {
	fmt.Printf("verification %s\n", data)
	/*if data == nil {
	      return RepCodeEnum.NULL_ERROR.parseError("captchaVO")
	}
	if StringUtils.isEmpty(captchaVO.getCaptchaVerification())) {
	      return RepCodeEnum.NULL_ERROR.parseError("captchaVerification")
	}*/
	if limitHandler != nil {
		return limitHandler.ValidateVerification(data)
	}
	return model.ResponseModel{RepCode: constant.SuccessFlag}
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

func (t *CaptchaServiceBase) afterValidateFail(data model.Captcha) {
	if limitHandler != nil {
		// 验证失败 分钟内计数
		fails := fmt.Sprintf(constant.LIMIT_KEY, "FAIL", data.ClientUid)
		var cs CaptchaCacheService = t.getCacheService(data.CaptchaType)
		if !cs.exists(fails) {
			cs.set(fails, "1", 60)
		}
		cs.increment(fails, 1)
	}
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
	if len(imgStr) == 0 {
		return false
	}
	var decoder = util.Base64Util{}
	// 解密
	b, _ := decoder.Decode(imgStr)
	// 处理数据
	for i := 0; i < len(b); i++ {
		/*if b[i] < 0 {
			b[i] += uint8(256)
		}*/
	}
	//文件夹不存在则自动创建
	fs, _ := os.Create(path)
	defer fs.Close()
	fs.Write(b)

	return true
}

/**
 * 解密前端坐标aes加密
 *
 * @param point
 * @return
 */
func (t CaptchaServiceBase) decrypt(point string, key string) string {
	// return AESUtil.aesDecrypt(point, key)
	return ""
}

func (t CaptchaServiceBase) GetEnOrChLength(s string) int {
	return utf8.RuneCountInString(s)
}
