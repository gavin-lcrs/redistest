package com.gavin.redis.action;


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


@Controller
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private RedisCacheUtil redisCache;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public void qmfGetTranid(HttpServletRequest request, HttpServletResponse response) {

		String t =  request.getParameter("t");
		logger.info("----\ntest:"+t);
		
		redisCache.set("test", t);
		Object obj = redisCache.get("test");
		logger.info("----\nobject test:"+obj.toString());
		
	}
	
}