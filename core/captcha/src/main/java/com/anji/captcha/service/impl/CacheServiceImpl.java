/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.service.impl;

import com.anji.captcha.service.CacheService;
import com.anji.captcha.util.CacheUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
@Service
public class CacheServiceImpl implements CacheService {

	//@Autowired
	//private StringRedisTemplate stringRedisTemplate;

	@Override
	public void set(String key, String value, long expiresInSeconds) {
		CacheUtil.set(key, value, expiresInSeconds);
		//stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public boolean exists(String key) {
		return CacheUtil.exists(key);
		//return stringRedisTemplate.hasKey(key);
	}

	@Override
	public void delete(String key) {
		CacheUtil.delete(key);
		//stringRedisTemplate.delete(key);
	}

	@Override
	public String get(String key) {
		return CacheUtil.get(key);
		// return stringRedisTemplate.opsForValue().get(key);
	}

}
