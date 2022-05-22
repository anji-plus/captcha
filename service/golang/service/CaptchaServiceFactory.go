package service

type CaptchaServiceFactory struct {
}

func (f *CaptchaServiceFactory) GetCache(cache string) CacheService {
	return nil
}
