package main

import (
	"anjiplus/captcha/api"
	constant "anjiplus/captcha/const"
	model "anjiplus/captcha/model"
	service "anjiplus/captcha/service"
	"encoding/json"
	"fmt"
)

func main() {
	fmt.Println("Hello world")
	var s service.CaptchaService
	var api = api.Api{}
	api.Init()
	s = api.CaptchaService()

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
