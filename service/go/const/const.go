package constant

const (
	// CodeKeyPrefix 缓存key前缀
	CodeKeyPrefix = "RUNNING:CAPTCHA:%s"

	// BlockPuzzleCaptcha 滑动验证码服务标识
	BlockPuzzleCaptcha = "blockPuzzle"

	// ClickWordCaptcha 点击验证码服务标识
	ClickWordCaptcha = "clickWord"

	// MemCacheKey 内存缓存标识
	MemCacheKey = "mem"

	// DefaultFont 字体文件地址
	DefaultFont = "/resources/fonts/WenQuanZhengHei.ttf"
)

const (
	// DefaultTemplateImageDirectory 滑动模板图文件目录地址
	DefaultTemplateImageDirectory = "/resources/defaultImages/jigsaw/slidingBlock"
	// DefaultBackgroundImageDirectory 背景图片目录地址
	DefaultBackgroundImageDirectory = "/resources/defaultImages/jigsaw/original"
	// DefaultClickBackgroundImageDirectory 点击背景图默认地址
	DefaultClickBackgroundImageDirectory = "/resources/defaultImages/pic-click"
)
