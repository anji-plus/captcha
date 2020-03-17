package com.anji.captcha.service;

import java.util.List;
import java.util.Set;

/**
 * @Title: redis通用方法
 * @author lide
 * @date 2018-08-21
 */
public interface CaptchaRedisService {

	void set(String key, String value);

	void set(String key, String value, long expiresInSeconds);

	void setObject(String key, Object value);

	void setObject(String key, Object value, long expiresInSeconds);

	Object getObject(String key);

	long incr(String key, long by, long expiresInSeconds);

	long incr(String key, long by);

	boolean exists(String key);

	void delete(String key);

	String get(String key);

	long getExpire(String key);

	void rightPush(String key, String value, int expiresInSeconds);

	void hset(String hash, String hashKey, Object hashValue);

	Object hget(String hash, String hashKey);

	/**生成自动增长的id
	 * @param key
	 * @return
	 */
	long getNextId(String key);

	Set<Object> getHashKeys(String key);

	void deleteHashKeys(String key, Object... hashKeys);

	List<String> hmget(String key, List hashKeys);
}