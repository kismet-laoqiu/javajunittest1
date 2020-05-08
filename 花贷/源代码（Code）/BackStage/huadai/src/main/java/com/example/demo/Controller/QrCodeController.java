package com.example.demo.Controller;

import com.example.demo.Service.LoginService;
import com.example.demo.Service.QrCodeService;
import com.example.demo.Utils.QrCodeUtils;
import com.example.demo.entity.TD_code;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QrCodeController {

    /*
    *
    *       二维码系列操作：
    *           以流的形式返回二维码
    *           进入二维码中心
    *
    * */

    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins = "*")
    @RequestMapping("/qrcode")
    public void qrcode(HttpServletRequest request, HttpServletResponse response) {
//        StringBuffer url = request.getRequestURL();
//        // 域名
//        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
//
//        // 再加上请求链接
//        String requestUrl = tempContextUrl + "/index";
//        requestUrl = "付款二维码";
        String requestUrl = request.getParameter("td_code");
        JSONObject jsonObject = JSONObject.fromObject(requestUrl);
        try {
            String codenum = jsonObject.get("codenum").toString();
            OutputStream os = response.getOutputStream();
            QrCodeUtils.encode(requestUrl,codenum,  "/static/images/logo.jpg", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/codecenter")
    public JSONObject codecenter(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");
        if (loginService.login_person(request)){
            List<TD_code> td_codes = qrCodeService.select_code(identification);
            map.put("td_codes", td_codes);
        }else{
            map.put("state","false");
            map.put("msg", "请求不安全");
        }
        return JSONObject.fromObject(map);
    }
}
