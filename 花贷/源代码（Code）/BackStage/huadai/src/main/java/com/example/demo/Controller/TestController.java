package com.example.demo.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.Service.*;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    /*
    *   调试中心，测试一些相关操作
    *
    * */

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private ReimburseService reimburseService;
    @Autowired
    private PersonalCenterService personalCenterService;
    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private EnterpriseIndexService enterpriseIndexService;

    @CrossOrigin(origins = "*")
    @ResponseBody
    @RequestMapping("/test")
    public String test(HttpServletRequest request){
        try {
            System.out.println(request.getParameter("businesslicence"));
            return request.getParameter("businesslicence");
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @RequestMapping(value = "/test1")
    public String test1(String businesslicence){
        try {
            return businesslicence;
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
