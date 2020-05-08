package com.example.demo.Controller;

import com.example.demo.Service.CardService;
import com.example.demo.Service.PersonalCenterService;
import com.example.demo.entity.Card;
import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Message;
import com.example.demo.entity.Person;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/personalcenter")
public class PersonalCenterController {

    /*
    *   个人中心系列操作：
    *       进入企业中心， 收集信息
    *       更新头像
    *       更新基本密码
    *       进入个人中心， 收集信息
    *       进入消息中心， 收集信息
    *       更新用户信息
    *
    * */
    @Autowired
    private PersonalCenterService personalCenterService;
    @Autowired
    private CardService cardService;

    @Value("${my.upload.imgPath}")
    private String imgPath;

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/enterprise")
    public JSONObject enterprise(HttpServletRequest request ){
        String businesslicence = request.getParameter("businesslicence");
        Map<String, Object> map = new HashMap<>();
        Enterprise enterprise = personalCenterService.select_enterprise(businesslicence);
        map.put("enterprise",enterprise);
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/update_avatar")
    public JSONObject update_avatar(@RequestParam("fileUpload") MultipartFile file, HttpServletRequest request ){
        String businesslicence = request.getParameter("businesslicence");
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
            if (personalCenterService.update_avatar(businesslicence, fileName)){
                map.put("state","true");
                map.put("msg","更改头像成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/update_password")
    public JSONObject update_password( HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String businesslicence = request.getParameter("businesslicence");
        String password = request.getParameter("old_password");
        if(personalCenterService.inspect_password(businesslicence, password) != null){
            password = request.getParameter("new_password");
            if(personalCenterService.update_password(businesslicence, password)){
                map.put("state","true");
                map.put("msg","密码修改成功");
            }else {
                map.put("state","false");
                map.put("msg","密码修改失败");
            }
        }else {
            map.put("state","false");
            map.put("msg","旧密码错误");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/person")
    public JSONObject person(HttpServletRequest request ){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");
        Person person = personalCenterService.select_person(identification);
        List<Card> cardList = cardService.select_card(identification);
        map.put("person", person);
        map.put("cardList", cardList);
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/message")
    public JSONObject message(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");
        if (personalCenterService.update_message(identification)){
            List<Message> messageList = personalCenterService.select_message(identification);
            System.out.println(messageList);
            map.put("message", messageList);
            map.put("state","true");
        }else {
            map.put("state","false");
            map.put("msg","系统异常");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/update_information")
    public JSONObject update_information(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");
        String password = request.getParameter("old_password");
        Person person = personalCenterService.inspect_password_person(identification, password);
        if(person != null){
            String username = request.getParameter("username");
            password = request.getParameter("new_password");
            String phonenum = request.getParameter("phonenum");
            if (username != null)
                person.setUsername(username);
            if (password != null)
                person.setPassword(password);
            if (phonenum != null)
                person.setPhonenum(phonenum);
            if(personalCenterService.update_person(person)){
                map.put("state","true");
                map.put("msg","更改成功");
            }else {
                map.put("state","false");
                map.put("msg","更改失败");
            }
        }else {
            map.put("state","false");
            map.put("msg","更改失败");
        }
        return JSONObject.fromObject(map);
    }
}
