package service

type CacheService interface {
	set(key string, value string, expiresInSeconds int)

	exists(key string)

	delete(key string)

	get(key string)

	/**
	 * 缓存类型-local/redis/memcache/..
	 * @return
	 */
	cacheType() string

	/***
	 *
	 * @param key
	 * @param val
	 * @return
	 */
	increment(key string, val int)
}
