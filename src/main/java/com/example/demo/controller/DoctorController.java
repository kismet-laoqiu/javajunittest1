package com.example.demo.controller;

import com.example.demo.pojo.form.MedicalRecordFormInfo;
import com.example.demo.pojo.table.MedicalRecord;
import com.example.demo.pojo.table.User;
import com.example.demo.service.MedicalRecordService;
import com.example.demo.service.RecordApplyService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PatientController
 * @Description: 患者端
 * @Author:
 * @Date:
 **/
@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private RecordApplyService recordApplyService;

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home() {
        return "doctor/home";
    }

    @RequestMapping("/addRecord")
    public String addRecord() {
        return "doctor/addRecord";
    }

    //显示医生信息
    @RequestMapping("/userInfo")
    public String showDoctorInfo(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long doctorId = (long) session.getAttribute("userId");
        modelMap.addAttribute("user", userService.findUserById(doctorId));
        return "doctor/userInfo";
    }

    //医生修改个人信息后提交操作
    @ResponseBody
    @RequestMapping(value = "/doctorInfoSubmit", method = RequestMethod.POST)
    public Object patientInfoSubmit(User user, @RequestParam("picture") MultipartFile picture) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", userService.doctorInfoSubmit(user,picture));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/recordSubmit", method = RequestMethod.POST)
    public Object recordSubmit(MedicalRecordFormInfo medicalRecordFormInfo, HttpServletRequest request)  {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        long doctorId = (long) session.getAttribute("userId");
        map.put("state", medicalRecordService.addRecord(medicalRecordFormInfo, doctorId));
        return map;
    }

    @RequestMapping("/listSubmitRecord")
    public String listSubmitRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long doctorId = (long) session.getAttribute("userId");
        List<MedicalRecord> records = medicalRecordService.listSubmitRecordByDoctor(doctorId);
        modelMap.addAttribute("records", records);
        return "doctor/listSubmitRecord";
    }

    @RequestMapping("/applyRecord")
    public String applyRecord() {
        return "doctor/applyRecord";
    }

    @RequestMapping(value = "/sendApply")
    public String sendApply(HttpServletRequest request, String recordId) {
        HttpSession session = request.getSession();
        long doctorId = (long) session.getAttribute("userId");
        recordApplyService.addApply(doctorId, Long.parseLong(recordId));
        return "redirect:/doctor/listApplyRecord ";
    }


    @RequestMapping(value = "/reSendApply")
    public String reSendApply(String applyId) {
        recordApplyService.reSendApply(Long.parseLong(applyId));
        return "redirect:/doctor/listApplyRecord ";
    }

    @RequestMapping(value = "/listApplyRecord")
    public String listApplyRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long doctorId = (long) session.getAttribute("userId");
        modelMap.addAttribute("applies", recordApplyService.listApplyByDoctor(doctorId));
        return "doctor/listApplyRecord";
    }

    @RequestMapping("/lookRecord")
    public String lookRecord(ModelMap modelMap, String applyId) throws Exception {
        modelMap.addAttribute("record", recordApplyService.lookRecord(Long.parseLong(applyId)));
        return "doctor/lookRecord";
    }

}
