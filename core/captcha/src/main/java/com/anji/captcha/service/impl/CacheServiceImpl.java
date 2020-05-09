/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.service.impl;

import com.anji.captcha.service.CacheService;
import com.anji.captcha.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
/**
 *
 */
@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void set(String key, String value, long expiresInSeconds) {
		if(stringRedisTemplate != null){
			stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
		}else{
			CacheUtil.set(key, value, expiresInSeconds);
		}
	}

	@Override
	public boolean exists(String key) {
		if(stringRedisTemplate != null){
			return stringRedisTemplate.hasKey(key);
		}else{
			return CacheUtil.exists(key);
		}
	}

	@Override
	public void delete(String key) {
		if(stringRedisTemplate != null){
			stringRedisTemplate.delete(key);
		}else{
			CacheUtil.delete(key);
		}
	}

	@Override
	public String get(String key) {
		if(stringRedisTemplate != null){
			return stringRedisTemplate.opsForValue().get(key);
		}else{
			return CacheUtil.get(key);
		}
	}

}
