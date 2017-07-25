package com.cl.web.app;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName AppBaseController.java
 * @Description 接口访问
 * @Version 1.0
 * @Author liugy.
 * @Email liu5156@126.com
 * @History <br>
 * <li>Author: liugy</li>
 * <li>Date: 2017-04-01</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Controller
@RequestMapping("app")
public class AppBaseController extends AppChoiceFunction {
	
	public JSONObject appService(@PathVariable String service, HttpServletRequest request,
								 HttpServletResponse response, HttpSession session) {
									
		JSONObject jsonObject = new JSONObject();
        if(checkService(request,service,jsonObject)){
            reqLog(request,service);
            gotoFunction(request, response, session, jsonObject, service);
        }
        resultLog(jsonObject,service);
        if(service.equals("deposit")){
            try {
                response.sendRedirect( "http://127.0.0.1:8080/app/result.htm?code="
                        + jsonObject.getInteger("code") + "&message="
                        + URLEncoder.encode(jsonObject.getString("message"), "utf-8"));
            } catch (IOException e) {
                logger.error("app接口访问发生异常：serveice={},e={}", service, e);
                e.printStackTrace();
            }

        }
		return jsonObject;
	}
	
	/**
	 * 检测请求方法
	 * @param request
	 * @param service
	 * @param jsonObject
	 * @return
	 */
	private boolean checkService(	HttpServletRequest request, String service,
									JSONObject jsonObject) {
		if (!AppServiceEnum.isExist(service)) {
			jsonObject.put("code", -2);
			jsonObject.put("message", "该服务[" + service + ".htm]不存在，请检查");
			return false;
		}
		return checkLogin(jsonObject);
	}
	
	/**
	 * 检测是否登录
	 * @param jsonObject
	 * @return
	 */
	private boolean checkLogin(JSONObject jsonObject) {
		if (false) {
			jsonObject.put("code", -1);
			jsonObject.put("message", "未登录或登录失效");
			return false;
		}
		return false;
	}

    /**
     * 入参日志
     * @param request
     * @param service
     */
	public void reqLog(HttpServletRequest request, String service) {
		Map<String, String> reqMap = new HashMap<>();
//        reqMap = WebUtil.getRequestMap(request);
		logger.info(
//		        "app请求接口入参：service={},  des={},reqMap={}", 
                service + ".htm",
			AppServiceEnum.getByService(service), reqMap);
	}
	
	public void resultLog(JSONObject jsonObject, String service){
	    String jsonStr = jsonObject.toString();
        if (!AppServiceEnum.printResultLog(service) && (int) jsonObject.get("code") == 1) {
            jsonStr = "成功响应";
        }
//        logger.info("app接口请求结果：service={},des={},json={},user={}", service + ".htm", AppServiceEnum
//                        .getMsgByservice(service), jsonStr,
//                ShiroSessionUtils.getSessionLocal() != null ? ShiroSessionUtils.getSessionLocal()
//                        .getUserName() : "未登录");

    }
}
