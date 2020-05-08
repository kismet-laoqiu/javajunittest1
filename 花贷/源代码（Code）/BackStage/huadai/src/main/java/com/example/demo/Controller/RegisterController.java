package com.example.demo.Controller;

import com.example.demo.API.Authorize;
import com.example.demo.Utils.SendMessageUtil;
import com.example.demo.Service.CardService;
import com.example.demo.Service.RegisterService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/regist")
public class RegisterController {

    /*
    *
    *       注册操作相关信息：
    *           企业注册
    *           企业上传文件
    *           员工注册发送短信验证码
    *           员工注册
    *
    * */
    @Autowired
    private RegisterService registerService;
    @Autowired
    private CardService cardService;

    private Map<String ,String> vercodehash = new HashMap<>();

    @Value("${my.upload.imgPath}")
    private String imgPath;

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/enterprise")
    public JSONObject enterprise(HttpServletRequest request){
        boolean flag = registerService.enterpriseRegister(request);
        Map<String,Object> map = new HashMap<>();
        if(flag){
            String businesslicence = request.getParameter("businesslicence");
            map.put("state", "true");
            map.put("msg","信息注入成功");
        }else{
            map.put("state","flase");
            map.put("msg","注入信息失败");
        }
        return JSONObject.fromObject(map);
    }

    // 认证资料的上传
    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/upload")
    public JSONObject upload(@RequestParam("fileUpload") MultipartFile file, HttpServletRequest request){
        String businesslicence = request.getParameter("businesslicence");
        String ownernumber = request.getParameter("ownernumber");
        Map<String,Object> map = new HashMap<>();
        if (file.isEmpty()){
            map.put("state","false");
            map.put("msg","上传文件失败");
            return JSONObject.fromObject(map);
        }
        String fileName =  file.getOriginalFilename();
        File dest = new File(imgPath+fileName);
        try{
            file.transferTo(dest);
            if (registerService.enterpriseUpload(businesslicence, ownernumber, fileName)){
                map.put("state","true");
                map.put("msg","上传文件成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state","false");
            map.put("msg","上传文件失败");
        }

        return JSONObject.fromObject(map);
    }
    @CrossOrigin(origins = "*")
    @ResponseBody
    @RequestMapping(value = "/SMS")
    public JSONObject sms( HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            String phonenum = request.getParameter("phonenum");

            String vercode = registerService.getRandomString(4);
            if (vercodehash.get(phonenum)== null)
                vercodehash.put(phonenum, vercode);
            else
                vercode = vercodehash.get(phonenum);
            Integer number = SendMessageUtil.send("hexianbo","d41d8cd98f00b204e980",phonenum,
                    "【花贷】您好，您在花贷平台的验证码为" + vercode + "，请勿告诉他人！");
            System.out.println("vercode" + vercode);
            map.put("state","true");
            map.put("msg","短信发送成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("state","false");
            map.put("msg","短信发送失败");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/person")
    public JSONObject person(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String vercode = request.getParameter("vercode");
        String phonenum = request.getParameter("phonenum");
        System.out.println(vercodehash.get(phonenum));
        if (vercodehash.get(phonenum).equals(vercode)){
            vercodehash.remove(phonenum);
            if (registerService.personRegister(request) && cardService.add_card(request)){
                String userid = request.getParameter("identification");
                String code = Authorize.getURL("personal_domestic_transfers meta_data customers_profiles","HK","GCB","en_US",userid,"http://duanbanyu.picp.net:25345/bindCard");
                map.put("url",code);
                map.put("state", "true");
                map.put("msg","请等待信息审核结果");
            }else {
                map.put("state","false");
                map.put("msg","注入信息失败");
            }
        }else {
            map.put("state","false");
            map.put("msg","验证码错误");
        }
        return JSONObject.fromObject(map);
    }
}
