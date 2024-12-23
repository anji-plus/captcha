package com.anji.captcha.demo.service;

import com.anji.captcha.service.CaptchaCacheService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 对于分布式部署的应用，我们建议应用自己实现CaptchaCacheService，比如用Redis，参考service/spring-boot代码示例。
 * 如果应用是单点的，也没有使用redis，那默认使用内存。
 * 内存缓存只适合单节点部署的应用，否则验证码生产与验证在节点之间信息不同步，导致失败。
 *
 * ☆☆☆ SPI： 在resources目录新建META-INF.services文件夹(两层)，参考当前服务resources。
 * @Title: 使用redis缓存
 * @author Devli
 * @date 2020-05-12
 */
public class CaptchaCacheServiceRedisImpl implements CaptchaCacheService {

    @Override
    public String type() {
        return "redis";
    }

    private static final String LUA_SCRIPT = "local key = KEYS[1] " +
            "local incrementValue = tonumber(ARGV[1]) " +
            "if redis.call('EXISTS', key) == 1 then " +
            "    return redis.call('INCRBY', key, incrementValue) " +
            "else " +
            "    return incrementValue " +
            "end";

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value, long expiresInSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
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
	public Long increment(String key, long val) {
        // 执行 Lua 脚本
        RedisScript<Long> script = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);
        // 执行 Lua 脚本
        return stringRedisTemplate.execute(
                script,
                Collections.singletonList(key),
                String.valueOf(val)
        );
	}

    @Override
    public void setExpire(String key, long l) {
        stringRedisTemplate.expire(key, l, TimeUnit.SECONDS);
    }
}
