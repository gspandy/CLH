package com.cl.web.controller;

import com.cl.ws.order.test.TestOrder;
import com.cl.ws.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: T-XUE
 * @Date: Created in 10:08 2017/3/15 0015
 * @Description:
 * @Version: 1.0
 * @Email: xguangqi@yiji.com
 * @History: <li>Author: xguangqi</li> <li>Date: 2017-03-15</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@Controller
@RequestMapping("/mainPage")
public class BaseController {
    static String vm_path = "mainPage/";
    @Autowired
    protected TestService testService;

    @RequestMapping("index.htm")
    public String buyerRefundList(HttpServletRequest request, HttpServletResponse response,
                                  Model model) {
        TestOrder order=new TestOrder();
        order.setName("xxx");
        order.setProcessAdvice("127.0.0.1");
        order.setProcessName("55");
        testService.add(order);
        model.addAttribute("helloWorld", "helloWorld");

        return vm_path + "index.vm";
    }
}
