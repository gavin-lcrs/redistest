/**
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.gavin.redis.action 
 * @author: Gavin   
 * @date: 2018年11月5日 下午4:30:39
 * 
 */
package com.gavin.redis.action;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gavin.redis.util.RedisCacheUtil;

import net.sf.json.JSONObject;

/**
 * @Description: 
 *
 * @author: Gavin
 *
 * @date: 2018年11月5日 下午4:30:39 
 *
 */
@Controller
public class RedisController {
	Logger logger = LoggerFactory.getLogger(RedisController.class);

	@Autowired
	private RedisCacheUtil redisCache;

	@RequestMapping(value = "/cache", method = RequestMethod.POST)
	@ResponseBody
	public void redisCache(HttpServletRequest request, HttpServletResponse response) {
		String sb = getData(request);
		JSONObject redisData = JSONObject.fromObject(sb);
		logger.info("===\n收到的报文："+redisData);
		String actCode = redisData.getString("actCode");
		
		String key = redisData.getString("key");
		String value = redisData.getString("value");
		Long time = redisData.optString("time") == null ? 0 : redisData.getLong("time");
		if ("1".equals(actCode)) {
			logger.info("action:"+actCode+",增加操作");
			if (time == 0L) {
				redisCache.set(key, value);
			} else {
				redisCache.set(key, value, time);
			}
		} else if("2".equals(actCode)) {
			logger.info("action:"+actCode+",删除操作");
			Object vl = redisCache.get(key);
			
			if (vl != null) {
				logger.info("key:"+key+", value:"+vl+",执行删除操作");
				redisCache.del(key);
			} else {
				logger.info("key:"+vl+"无数据！");
			}
		}
	}
	
	public String getData(HttpServletRequest request) {
		String sb = null;
		try {
			ServletInputStream in = request.getInputStream();
			byte[] bytes = new byte[request.getContentLength()];
			int r = 0;
			while ((r = in.read(bytes, 0, bytes.length)) != -1) {
				sb = new String(bytes, "UTF-8");
			}
		} catch (Exception e) {
			logger.error("获取请求数据异常",e);
		}
		return sb;
	}
}
