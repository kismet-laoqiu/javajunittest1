package com.example.demo.controller;

import com.example.demo.pojo.form.LoginFormInfo;
import com.example.demo.service.MedicalRecordService;
import com.example.demo.service.RecordApplyService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MiniProgramController
 * @Description: 微信小程序API
 *
 **/
@Controller
@RequestMapping("/miniProgram")
public class MiniProgramController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private RecordApplyService recordApplyService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object loginSubmit(LoginFormInfo loginFormInfo) {
        loginFormInfo.setType("patient");
        return userService.miniProgramLogin(loginFormInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public Object getUserInfo(long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", userService.getUserInfo(userId));
        return map;
    }

    //list患者未确认的医疗记录
    @ResponseBody
    @RequestMapping(value = "/listUnConfirmRecord", method = RequestMethod.POST)
    public Object listUnConfirmRecord(long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("records", medicalRecordService.listUnConfirmRecordByPatient(userId));
        return map;
    }

    //查看患者未确认的医疗记录
    @ResponseBody
    @RequestMapping(value = "/confirmRecord", method = RequestMethod.POST)
    public Object confirmRecord(long userId, long recordId) {
        Map<String, Object> map = new HashMap<>();
        map.put("record", medicalRecordService.lookRecordByPatient(recordId));
        return map;
    }

    //确认病历
    @ResponseBody
    @RequestMapping(value = "/confirmRecordSubmit", method = RequestMethod.POST)
    public Object confirmRecordSubmit(long userId, long recordId) {
        return medicalRecordService.confirmRecordSubmit(recordId);
    }

    //list患者已确认的医疗记录
    @ResponseBody
    @RequestMapping(value = "/listPatientRecord", method = RequestMethod.POST)
    public Object listPatientRecord(long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("records", medicalRecordService.listConfirmedRecordByPatient(userId));
        return map;
    }

    // 部分授权
    @ResponseBody
    @RequestMapping(value = "/listUnApplyRecord", method = RequestMethod.POST)
    public Object listUnApplyRecord(long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("applies", recordApplyService.listUnApplyByPatient(userId));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/listApplyRecord", method = RequestMethod.POST)
    public Object listApplyRecord(long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("applies", recordApplyService.listApplyByPatient(userId));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/applyRecord", method = RequestMethod.POST)
    public Object applyRecord(long applyId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        recordApplyService.confirmApply(applyId);
        map.put("state", 200);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/revokeApply", method = RequestMethod.POST)
    public Object revokeApply(long applyId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        recordApplyService.revokeApply(applyId);
        map.put("state", 200);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getKey", method = RequestMethod.POST)
    public Object getKey(long recordId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder buffer = new StringBuilder();
        InputStream is = new FileInputStream(System.getProperty("user.dir") + "\\output\\" + recordId + "\\master_key");
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
        map.put("masterKey", buffer.toString());
        buffer = new StringBuilder();
        is = new FileInputStream(System.getProperty("user.dir") + "\\output\\" + recordId + "\\pub_key");
        reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append("\n");
            line = reader.readLine();
        }
        reader.close();
        is.close();
        map.put("publicKey", buffer.toString());
        return map;
    }
}
