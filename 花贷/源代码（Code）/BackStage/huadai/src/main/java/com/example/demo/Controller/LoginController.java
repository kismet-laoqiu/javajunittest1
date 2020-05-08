package com.example.demo.Controller;

import com.example.demo.Service.LoginService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/login")
public class LoginController {

    /*
    *   企业 、  员工端注册
    * */

    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/enterprise")
    public JSONObject enterprise(HttpServletRequest request){
        Map<String,Object>map = new HashMap<>();
        if (loginService.login_enterprise(request)){
            String businesslicence = request.getParameter("businesslicence");

            map.put("state","true");
            map.put("businesslicence",businesslicence);
        }else{
            map.put("state","false");
            map.put("msg","账号或密码错误");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/person")
    public JSONObject person(HttpServletRequest request){
        Map<String,Object>map = new HashMap<>();
        if (loginService.login_person(request)){
            String identification = request.getParameter("identification");
            map.put("state","true");
            map.put("identification",identification);
        }else{
            map.put("state","false");
            map.put("msg","账号或密码错误");
        }
        return JSONObject.fromObject(map);
    }

}
