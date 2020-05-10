/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.service;

import java.util.List;
import java.util.Set;

/**
 * @Title: redis通用方法
 * @author lide
 * @date 2018-08-21
 */
public interface CacheService {

	void set(String key, String value, long expiresInSeconds);

	boolean exists(String key);

	void delete(String key);

	String get(String key);

}
