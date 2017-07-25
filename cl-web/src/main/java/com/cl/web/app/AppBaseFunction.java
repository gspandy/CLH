package com.cl.web.app;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @FileName AppBaseFunction.java
 * @Description
 * @Version 1.0
 * @Author liugy.
 * @Email liu5156@126.com
 * @History <br>
 * <li>Author: liugy</li>
 * <li>Date: 2017-04-01</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class AppBaseFunction {
    static final Logger logger	= LoggerFactory.getLogger(AppBaseFunction.class);

    protected void successRseult(JSONObject jsonObject, String msg){
        jsonObject.put("code",1);
        jsonObject.put("message","操作成功");
        jsonObject.put("message",msg);
    }
    
    protected void failResult(JSONObject jsonObject, String msg){
        jsonObject.put("code",0);
        jsonObject.put("message",msg);
    }
    

    protected void login(HttpServletRequest request, HttpServletResponse response,
                         JSONObject jsonObject, String des) {
							
	}
	
	protected void logout(HttpServletRequest request, JSONObject JSONObject, String desc){
	    
    }
}
