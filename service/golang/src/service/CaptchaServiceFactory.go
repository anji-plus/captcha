package service

import (
	constant "anjiplus/captcha/const"
	"anjiplus/captcha/model"
	"fmt"
)

type CaptchaServiceFactory struct {
	cacheService   map[string]*CaptchaCacheService
	captChaService map[string]*CaptchaService
}

func (f *CaptchaServiceFactory) GetCache(cache string) CaptchaCacheService {
	if f.cacheService[cache] != nil {
		return *f.cacheService[cache]
	}
	panic(model.ResponseModel{RepCode: constant.CAPTCHA_CACHETYPE})
}
func (f *CaptchaServiceFactory) SetCacheService(s CaptchaCacheService) {
	f.cacheService[s.CacheType()] = &s
}

func (f *CaptchaServiceFactory) SetCaptchaService(s CaptchaService) {
	f.captChaService[s.CaptchaType()] = &s
}
func init() {
	fmt.Println("captcha-service-factory-init....")
}
