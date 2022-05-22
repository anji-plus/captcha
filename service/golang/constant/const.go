package constant

/***
 * @author wongbin
 */
const (
	ClickWord   string = "clickWord"
	BlockPuzzle string = "blockPuzzle"
	DefaultType string = "default"
	SuccessFlag string = "0000"
	/**
	 *滑块底图路径
	 */
	ORIGINAL_PATH_JIGSAW string = "captcha.captchaOriginalPath.jigsaw"

	/***
	 *点选底图路径
	 */
	ORIGINAL_PATH_PIC_CLICK string = "captcha.captchaOriginalPath.pic-click"

	/**
	 * 缓存local/redis...
	 */
	CAPTCHA_CACHETYPE string = "captcha.cacheType"

	/**
	 * 右下角水印文字(我的水印)
	 */
	CAPTCHA_WATER_MARK string = "captcha.water.mark"

	/**
	 * 点选文字验证码的文字字体(宋体)
	 */
	CAPTCHA_FONT_TYPE  string = "captcha.font.type"
	CAPTCHA_FONT_STYLE string = "captcha.font.style"
	CAPTCHA_FONT_SIZE  string = "captcha.font.size"

	/**
	 * 验证码类型default两种都实例化。
	 */
	CAPTCHA_TYPE string = "captcha.type"

	/**
	 * 滑动干扰项(0/1/2)
	 */
	CAPTCHA_INTERFERENCE_OPTIONS string = "captcha.interference.options"

	/**
	 * 底图自定义初始化
	 */
	CAPTCHA_INIT_ORIGINAL string = "captcha.init.original"

	/**
	 * 滑动误差偏移量
	 */
	CAPTCHA_SLIP_OFFSET string = "captcha.slip.offset"

	/**
	 * aes加密开关
	 */
	CAPTCHA_AES_STATUS string = "captcha.aes.status"

	/**
	 * 右下角水印字体(宋体)
	 */
	CAPTCHA_WATER_FONT string = "captcha.water.font"

	/**
	 * local缓存的阈值
	 */
	CAPTCHA_CACAHE_MAX_NUMBER string = "captcha.cache.number"

	/**
	 * 定时清理过期local缓存，秒
	 */
	CAPTCHA_TIMING_CLEAR_SECOND string = "captcha.timing.clear"

	/**
	 * 历史资源清除开关 0禁用,1 开启
	 */
	HISTORY_DATA_CLEAR_ENABLE string = "captcha.history.data.clear.enable"

	/**
	 * 接口限流开关 0禁用 1启用
	 */
	REQ_FREQUENCY_LIMIT_ENABLE string = "captcha.req.frequency.limit.enable"

	/**
	 * get 接口 一分钟请求次数限制
	 */
	REQ_GET_MINUTE_LIMIT string = "captcha.req.get.minute.limit"

	/**
	 * 验证失败后，get接口锁定时间
	 */
	REQ_GET_LOCK_LIMIT string = "captcha.req.get.lock.limit"
	/**
	 * 验证失败后，get接口锁定时间
	 */
	REQ_GET_LOCK_SECONDS string = "captcha.req.get.lock.seconds"

	/**
	 * verify 接口 一分钟请求次数限制
	 */
	REQ_VALIDATE_MINUTE_LIMIT string = "captcha.req.verify.minute.limit"
	/**
	 * check接口 一分钟请求次数限制
	 */
	REQ_CHECK_MINUTE_LIMIT string = "captcha.req.check.minute.limit"

	/***
	 * 点选文字个数
	 */
	CAPTCHA_WORD_COUNT string = "captcha.word.count"
)
