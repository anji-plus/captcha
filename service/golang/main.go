package main

import (
	"captcha/constant"
	"captcha/model"
	service "captcha/service"
	"fmt"
)

func main() {
	fmt.Println("Hello world")
	var s service.CaptchaService
	base := service.CaptchaServiceBase{}
	base.InitDefault()

	var clickService = service.ClickWordCaptchaService{Base: base}
	s = &clickService
	s.Init(map[string]string{"cacheType": "local"})

	s.Get(model.Captcha{CaptchaId: "123"})
	s.Check(model.Captcha{
		CaptchaId: "1212121", CaptchaType: constant.DefaultType,
	})
	s.Verification(model.Captcha{CaptchaId: "12221"})

}
