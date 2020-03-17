package com.anji.captcha.service.impl;

import com.anji.captcha.service.CaptchaRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaRedisServiceImpl implements CaptchaRedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void set(String key, String value){
		stringRedisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void set(String key, String value, long expiresInSeconds) {
		stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public void setObject(String key, Object value){
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void setObject(String key, Object value, long expiresInSeconds){
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public Object getObject(String key) {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate.opsForValue().get(key);
	}
	@Override
	public long incr(String key, long by){
		return stringRedisTemplate.opsForValue().increment(key, by);
	}
	@Override
	public long incr(String key, long by, long expiresInSeconds){
		long result = stringRedisTemplate.opsForValue().increment(key, by);
		stringRedisTemplate.expire(key, expiresInSeconds, TimeUnit.SECONDS);
		return result;
	}

	@Override
	public boolean exists(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	@Override
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}

	@Override
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	@Override
	public long getExpire(String key) {
		return stringRedisTemplate.hasKey(key) ? stringRedisTemplate.getExpire(key).longValue() : -1;
	}

	@Override
	public void rightPush(String key, String value, int expiresInSeconds) {
		stringRedisTemplate.opsForList().rightPush(key, value);
		stringRedisTemplate.expire(key, expiresInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public void hset(String hash, String hashKey, Object hashValue){
		stringRedisTemplate.opsForHash().put(hash, hashKey, hashValue);
	}

	@Override
	public Object hget(String hash, String hashKey){
		return stringRedisTemplate.opsForHash().get(hash, hashKey);
	}

	@Override
	public long getNextId(String key) {
		RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
		Long increment = counter.incrementAndGet();
		return increment;
	}


	@Override
	public Set<Object> getHashKeys(String key) {
		return stringRedisTemplate.opsForHash().keys(key);
	}

	@Override
	public void deleteHashKeys(String key, Object... hashKeys) {
		if(null == hashKeys || hashKeys.length < 1){
			return;
		}
		stringRedisTemplate.opsForHash().delete(key,hashKeys);
	}

	@Override
	public List<String> hmget(String key, List hashKeys) {
		if(null == hashKeys || hashKeys.size() < 1){
			return new ArrayList<>();
		}
		return stringRedisTemplate.opsForHash().multiGet(key,hashKeys);
	}

}
