package service

// CaptchaCacheService
// 缓存服务接口
type CaptchaCacheService interface {
	Set(key string, value string, expiresInSeconds int)

	Exists(key string) bool

	Delete(key string)

	Get(key string) string

	/**
	 * 缓存类型-local/redis/memcache/..
	 * @return
	 */
	CacheType() string

	/***
	 *
	 * @param key
	 * @param val
	 * @return
	 */
	Increment(key string, val int) int
}
