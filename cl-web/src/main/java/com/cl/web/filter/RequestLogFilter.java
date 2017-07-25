package com.cl.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @FileName RequestLogFilter.java
 * @Description
 * @Version 1.0
 * @Author liugy.
 * @Email liu5156@126.com
 * @History <br>CrossScriptingFilter
 * <li>Author: liugy</li>
 * <li>Date: 2017-03-21</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class RequestLogFilter implements Filter {

    public static final String SESSION_KEY_PRE_PAGE_URL = "SESSION_KEY_PRE_PAGE_URL";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void destroy() {

    }


    private static final String[] urls = { "/resources/", "/img/", "/mobile/", "/favicon.ico", "/styles/",
            "/mainIndex.htm", "/login/" };

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
            throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        String uri = request.getServletPath();

        for (String s : urls) {
            if (uri.startsWith(s)) {
                chain.doFilter(request, response);
                return;
            }
        }
        if (!uri.equals("/do/message/ajaxLoadUnReadData.json")) {
            logger.info("请求的URI:" + uri);
        }else {
            chain.doFilter(request, response);
            return;
        }

        if (uri.equals("/index.htm") || uri.equals("/index.html") || uri.equals("//index.htm")) {
            response.sendRedirect("/");
            return;
        }
        String newUrl = uri;
        if (uri.indexOf("?") > 0) {
            newUrl = newUrl.substring(0, uri.indexOf("?"));
        }
        if (newUrl.endsWith(".htm") && !uri.equals("/front/platform/mall/redirctIndex.htm")) {
            request.getSession().setAttribute(this.SESSION_KEY_PRE_PAGE_URL, uri);
        } else if (uri.equals("/front/platform/mall/redirctIndex.htm")) {
            String oldUrl = (String) request.getSession().getAttribute(
                    this.SESSION_KEY_PRE_PAGE_URL);
            if (oldUrl != null) {
                if (oldUrl.indexOf("/do/") > -1) {
                    response.sendRedirect("/login/login.htm");
                } else if (oldUrl.indexOf("/admin/") > -1) {
                    request.getSession().removeAttribute(this.SESSION_KEY_PRE_PAGE_URL);
                    response.sendRedirect("/admin/login.htm");
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder("http请求URL：" + uri + "，传入参数：{");
        Enumeration<String> names = request.getParameterNames();
        int count = 0;
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (StringUtils.isNotEmpty(name) && name.toLowerCase().indexOf("password") > -1) {
                continue;
            }
            String[] values = request.getParameterValues(name);
            if (count > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(name + "=[");
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    stringBuilder.append(",");
                }
                if (values[i].length() <= 50)
                    stringBuilder.append(values[i]);
            }
            stringBuilder.append("]");
            count++;
        }
        stringBuilder.append("}");
        if (!uri.equals("/do/message/ajaxLoadUnReadData.htm")) {
            logger.info(stringBuilder.toString());
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
