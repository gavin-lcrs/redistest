package com.gavin.redis.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONObject;

@RestController   
public class SessionController {
	Logger logger = LoggerFactory.getLogger(SessionController.class);

	@ResponseBody
    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public String firstResp (HttpServletRequest request){  
    	logger.info("===request : first");
        Map<String, Object> map = new HashMap<>();  
        request.getSession().setAttribute("request Url", request.getRequestURL());  
        map.put("requestUrl", request.getRequestURL().toString()); 
        JSONObject json = JSONObject.fromObject(map);
        logger.info("==========\ndate : "+json.toString());
        
        return json.toString();  
    }  

	@ResponseBody
    @RequestMapping(value = "/second", method = RequestMethod.GET)  
    public String sessions (HttpServletRequest request){  
    	logger.info("===request : second");
        Map<String, Object> map = new HashMap<>();  
        map.put("sessionId", request.getSession().getId());  
        map.put("message", request.getSession().getAttribute("map"));  
        JSONObject json = JSONObject.fromObject(map);
        logger.info("==========\ndate : "+json.toString());
        return json.toString(); 
    }  
}