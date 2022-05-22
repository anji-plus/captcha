package service

import (
	"captcha/constant"
	"captcha/model"
)

type FrequencyLimitHandler interface {
	validateGet(captcha model.Captcha) model.ResponseModel

	validateCheck(captcha model.Captcha) model.ResponseModel

	validateVerification(captcha model.Captcha) model.ResponseModel
}
type DefaultFreLimitHandler struct {
}

func (t *DefaultFreLimitHandler) validateGet(captcha model.Captcha) model.ResponseModel {
	return model.ResponseModel{RepCode: constant.SuccessFlag}
}

func (t *DefaultFreLimitHandler) validateCheck(captcha model.Captcha) model.ResponseModel {
	return model.ResponseModel{RepCode: constant.SuccessFlag}
}

func (t *DefaultFreLimitHandler) validateVerification(captcha model.Captcha) model.ResponseModel {
	return model.ResponseModel{RepCode: constant.SuccessFlag}
}
