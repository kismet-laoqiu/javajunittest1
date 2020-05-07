package com.example.demo.controller;

import com.example.demo.pojo.table.InsurancePolicy;
import com.example.demo.pojo.table.InsuranceProject;
import com.example.demo.pojo.table.User;
import com.example.demo.pojo.util.RecordCipherInfo;
import com.example.demo.service.InsurancePolicyService;
import com.example.demo.service.InsuranceProjectService;
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
import java.util.Map;

/**
 * @ClassName: InsurerController
 * @Description: 保险公司端
 * @Author:
 * @Date:
 **/
@Controller
@RequestMapping("/insurer")
public class InsurerController {

    @Autowired
    private InsurancePolicyService insurancePolicyService;

    @Autowired
    private InsuranceProjectService insuranceProjectService;

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(ModelMap modelMap, HttpServletRequest request) {
        return "insurer/home";
    }

    //显示保险机构基本信息
    @RequestMapping("/userInfo")
    public String showInsurerInfo(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long insurerId = (long) session.getAttribute("userId");
        modelMap.addAttribute("user", userService.findUserById(insurerId));
        return "insurer/userInfo";
    }

    //修改保险公司信息后提交操作
    @ResponseBody
    @RequestMapping(value = "/insurerInfoSubmit", method = RequestMethod.POST)
    public Object insurerInfoSubmit(User user, @RequestParam("picture") MultipartFile picture) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", userService.insurerInfoSubmit(user,picture));
        return map;
    }

    @RequestMapping("/addInsuranceProject")
    public String addInsuranceProject() {
        return "insurer/addInsuranceProject";
    }

    @ResponseBody
    @RequestMapping(value = "/insuranceProjectSubmit", method = RequestMethod.POST)
    public Object insuranceProjectSubmit(InsuranceProject insuranceProject, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long insurerId = (long) session.getAttribute("userId");
        return insuranceProjectService.addInsuranceProject(insuranceProject, insurerId);
    }

    @RequestMapping("/listInsuranceProject")
    public String listInsuranceProject(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long insurerId = (long) session.getAttribute("userId");
        modelMap.addAttribute("insuranceProjects", insuranceProjectService.listInsuranceProject(insurerId));
        return "insurer/listInsuranceProject";
    }

    @RequestMapping("/lookInsuranceProject")
    public String lookInsuranceProject(String insuranceProjectId, ModelMap modelMap) {
        modelMap.addAttribute("insuranceProject", insuranceProjectService.lookInsuranceProject(Long.parseLong(insuranceProjectId)));
        return "insurer/lookInsuranceProject";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteInsuranceProject", method = RequestMethod.POST)
    public Object deleteInsuranceProject(String insuranceProjectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", insuranceProjectService.deleteInsuranceProject(Long.parseLong(insuranceProjectId)));
        return map;
    }

    @RequestMapping("/listInsurancePolicy")
    public String listInsurancePolicy(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long insurerId = (long) session.getAttribute("userId");
        modelMap.addAttribute("insurancePolicies", insurancePolicyService.listInsurancePolicyByInsurer(insurerId));
        return "insurer/listInsurancePolicy";
    }

    @RequestMapping("/lookInsurancePolicy")
    public String lookInsurancePolicy(String insurancePolicyId, ModelMap modelMap) {
        modelMap.addAttribute("insurancePolicy", insurancePolicyService.lookInsurancePolicyByInsurer(Long.parseLong(insurancePolicyId)));
        return "insurer/lookInsurancePolicy";
    }

    @ResponseBody
    @RequestMapping(value = "/agreeInsurancePolicy", method = RequestMethod.POST)
    public Object agreeInsurancePolicy(String insurancePolicyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", insurancePolicyService.agreeInsurancePolicy(Long.parseLong(insurancePolicyId)));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/refuseInsurancePolicy", method = RequestMethod.POST)
    public Object refuseInsurancePolicy(String insurancePolicyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", insurancePolicyService.refuseInsurancePolicy(Long.parseLong(insurancePolicyId)));
        return map;
    }

    @RequestMapping("/listProcessedInsurancePolicy")
    public String listProcessedInsurancePolicy(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long insurerId = (long) session.getAttribute("userId");
        modelMap.addAttribute("insurancePolicies", insurancePolicyService.listProcessedInsurancePolicyByInsurer(insurerId));
        return "insurer/listProcessedInsurancePolicy";
    }

    @RequestMapping("/lookProcessedInsurancePolicy")
    public String lookProcessedInsurancePolicy(String insurancePolicyId, ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("insurancePolicy", insurancePolicyService.lookInsurancePolicyByInsurer(Long.parseLong(insurancePolicyId)));
        return "insurer/lookProcessedInsurancePolicy";
    }

    @RequestMapping("/listUnhandledClaimRecord")
    public String listUnhandledClaimRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long insurerId = (long) session.getAttribute("userId");
        modelMap.addAttribute("insurancePolicies", insurancePolicyService.listUnhandledClaimRecord(insurerId));
        return "insurer/listUnhandledClaimRecord";
    }


    @RequestMapping("/artificialClaim")
    public String automaticClaim(String insurancePolicyId, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("record", insurancePolicyService.lookRecord(Long.parseLong(insurancePolicyId)));
        modelMap.addAttribute("insurancePolicy", insurancePolicyService.lookInsurancePolicyById(Long.parseLong(insurancePolicyId)));
        return "insurer/artificialClaim";
    }


    @RequestMapping("/automaticClaim")
    public String artificialClaim(String insurancePolicyId, ModelMap modelMap) throws Exception {
        RecordCipherInfo recordCipherInfo = insurancePolicyService.lookRecord(Long.parseLong(insurancePolicyId));
        InsurancePolicy insurancePolicy = insurancePolicyService.lookInsurancePolicyById(Long.parseLong(insurancePolicyId));
        modelMap.addAttribute("result", insurancePolicyService.judge(recordCipherInfo, insurancePolicy));
        modelMap.addAttribute("record", recordCipherInfo);
        modelMap.addAttribute("insurancePolicy", insurancePolicy);
        return "insurer/automaticClaim";
    }

    @ResponseBody
    @RequestMapping(value = "/agreeClaim", method = RequestMethod.POST)
    public Object agreeClaim(String insurancePolicyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", insurancePolicyService.agreeClaim(Long.parseLong(insurancePolicyId)));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/refuseClaim", method = RequestMethod.POST)
    public Object refuseClaim(String insurancePolicyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", insurancePolicyService.refuseClaim(Long.parseLong(insurancePolicyId)));
        return map;
    }

    @RequestMapping("/listHandledClaimRecord")
    public String listHandledClaimRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long insurerId = (long) session.getAttribute("userId");
        modelMap.addAttribute("insurancePolicies", insurancePolicyService.listHandledClaimRecord(insurerId));
        return "insurer/listHandledClaimRecord";
    }


}
