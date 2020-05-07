package com.example.demo.controller;

import com.example.demo.pojo.form.LoginFormInfo;
import com.example.demo.pojo.form.SignUpFormInfo;
import com.example.demo.pojo.table.MedicalRecord;
import com.example.demo.service.MedicalRecordService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MainController
 * @Description: 主控制类
 *
 *
 **/
@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private MedicalRecordService medicalRecordService;


    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(ModelMap modelMap, String type) {
        modelMap.addAttribute("type", type);
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
    public Object loginSubmit(LoginFormInfo loginFormInfo, HttpServletResponse response, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", userService.login(loginFormInfo, response, request));
        return map;
    }

    @RequestMapping("/signUp")
    public String signUp(ModelMap modelMap, String type) {
        modelMap.addAttribute("type", type);
        return "signUp";
    }


    @ResponseBody
    @RequestMapping(value = "/signUpSubmit", method = RequestMethod.POST)
    public Object signUpSubmit(SignUpFormInfo signUpFormInfo, HttpServletResponse response, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", userService.signUp(signUpFormInfo, response));
        return map;
    }

    @RequestMapping("/signOut")
    public String signOut(HttpServletResponse response) {
        userService.signOut(response);
        return "redirect:/login?type=patient";
    }

    @ResponseBody
    @RequestMapping(value = "/searchPatientSubmit", method = RequestMethod.POST)
    public Object searchPatientSubmit(@RequestBody Map<String, Object> map) {
        return userService.searchPatient((String) map.get("searchKey"));
    }

    @ResponseBody
    @RequestMapping(value = "/searchRecordSubmit", method = RequestMethod.POST)
    public Object searchRecordSubmit(@RequestBody Map<String, Object> map) {
        Map<String, Object> result = userService.searchPatient((String) map.get("searchKey"));
        Map<String, Object> data = (Map<String, Object>) result.get("data");
        List<MedicalRecord> records = medicalRecordService.listConfirmedRecordByDoctor((Long) data.get("patientId"));
        result.put("records", records);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/searchUnApplyRecordSubmit", method = RequestMethod.POST)
    public Object searchUnApplyRecordSubmit(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long doctorId = (long) session.getAttribute("userId");
        Map<String, Object> result = userService.searchPatient((String) map.get("searchKey"));
        Map<String, Object> data = (Map<String, Object>) result.get("data");
        List<MedicalRecord> records = medicalRecordService.listUnApplyRecord(doctorId, (Long) data.get("patientId"));
        result.put("records", records);
        return result;
    }

    //上传页面
    @RequestMapping("/fate/updateData")
    public String updateData() {
        return "fate/updateData";
    }


    //上传数据
    @RequestMapping("/fate/updateDataSubmit")
    public String updateDataSubmit() {
        return "fate/updateModel";
    }

    //上传模型
    @RequestMapping("/fate/updateModelSubmit")
    public String updateModelSubmit() {
        return "fate/show";
    }

}
