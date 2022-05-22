package model

import (
	"fmt"
	"math/big"
)

type Captcha struct {
	CaptchaId string

	ProjectCode string

	/**
	 * 验证码类型:(clickWord,blockPuzzle)
	 */
	CaptchaType string

	CaptchaOriginalPath string

	CaptchaFontType string

	CaptchaFontSize int

	SecretKey string

	/**
	 * 原生图片base64
	 */
	OriginalImageBase64 string

	/**
	 * 滑块点选坐标
	 */
	Point Point

	/**
	 * 滑块图片base64
	 */
	JigsawImageBase64 string

	/**
	 * 点选文字
	 */
	WordList []string

	/**
	 * 点选坐标
	 */
	PointList []string

	/**
	 * 点坐标(base64加密传输)
	 */
	PointJson string

	/**
	 * UUID(每次请求的验证码唯一标识)
	 */
	Token string

	/**
	 * 校验结果
	 */
	Result bool

	/**
	 * 后台二次校验参数
	 */
	CaptchaVerification string

	/***
	 * 客户端UI组件id,组件初始化时设置一次，UUID
	 */
	ClientUid string
	/***
	 * 客户端的请求时间，预留字段
	 */
	Ts big.Int

	/***
	 * 客户端ip+userAgent
	 */
	BrowserInfo string
}

func (t *Captcha) String() string {
	return fmt.Sprintf("%s", t)
}
