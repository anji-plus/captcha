package service

import (
	constant "anjiplus/captcha/const"
	model "anjiplus/captcha/model"
)

type FrequencyLimitHandler interface {
	ValidateGet(captcha model.Captcha) model.ResponseModel

	ValidateCheck(captcha model.Captcha) model.ResponseModel

	ValidateVerification(captcha model.Captcha) model.ResponseModel
}

type DefaultFreLimitHandler struct {
}

func (t *DefaultFreLimitHandler) ValidateGet(captcha model.Captcha) model.ResponseModel {
	return model.ResponseModel{RepCode: constant.SuccessFlag}
}

func (t *DefaultFreLimitHandler) ValidateCheck(captcha model.Captcha) model.ResponseModel {
	return model.ResponseModel{RepCode: constant.SuccessFlag}
}

func (t *DefaultFreLimitHandler) ValidateVerification(captcha model.Captcha) model.ResponseModel {
	return model.ResponseModel{RepCode: constant.SuccessFlag}
}
