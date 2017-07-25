package com.cl.web.app;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @FileName AppChoiceFunction.java
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
public class AppChoiceFunction extends AppBaseFunction {
	
	public void gotoFunction(HttpServletRequest request, HttpServletResponse response,
                             HttpSession session, JSONObject jsonObject, String service) {
	    switch (service){
            case "login" :login(request,response,jsonObject,"登录");
                break;
            case "logout":logout(request,jsonObject,"退出登录");
                break;
            default:
                jsonObject.put("code",-3);
                jsonObject.put("message","待开发功能");
                
        }
								
	}
}
