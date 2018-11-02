package com.gavin.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;



/**
 * 
 * @author Gavin
 *
 */
@Component
public class RedisTemplateUtil {
	Logger logger = LoggerFactory.getLogger(RedisTemplateUtil.class);
	
	private RedisTemplate<String, Object> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public Object get(String key) {
		return key==null ? null : redisTemplate.opsForValue().get(key);
	}
	
	
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			logger.error("",e);
			return false;
		}
	}
	
}
