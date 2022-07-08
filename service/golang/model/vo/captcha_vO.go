package vo

import "time"

type CaptchaVO struct {

	// 验证码类型 (clickWord,blockPuzzle)
	CaptchaType string

	//
	SecretKey string

	// UUID(每次请求的验证码唯一标识)
	Token string

	// 客户端UI组件，初始化时设置的 UUID
	ClientUuId string

	// 校验结果
	Result bool

	// 客户端的请求时间
	Ts time.Time
}
