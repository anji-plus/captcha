package main

import (
	"anjiplus/captcha/const"
	"anjiplus/captcha/model"
	service "anjiplus/captcha/service"
	"encoding/json"
	"fmt"
)

func main() {
	fmt.Println("Hello world")
	var s service.CaptchaService
	base := service.CaptchaServiceBase{}
	base.InitDefault()

	var clickService = service.ClickWordCaptchaService{Base: base}
	s = &clickService
	s.Init(model.Properties{Dict: map[string]string{"cacheType": "local"}})

	getRet := s.Get(model.Captcha{CaptchaId: "123"})
	getJson, _ := json.Marshal(getRet)
	fmt.Println("get=", string(getJson))

	checkRet := s.Check(model.Captcha{
		CaptchaId: "1212121", CaptchaType: constant.DefaultType,
	})
	checkJson, _ := json.Marshal(checkRet)
	fmt.Println("check=" + string(checkJson))

	verifyRet := s.Verification(model.Captcha{CaptchaId: "12221"})
	verifyJson, _ := json.Marshal(verifyRet)
	fmt.Println("verify=" + string(verifyJson))
}
