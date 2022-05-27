package service

type CaptchaServiceFactory struct {
	cacheType string
}

func (f *CaptchaServiceFactory) GetCache(cache string) CaptchaCacheService {
	return nil
}
