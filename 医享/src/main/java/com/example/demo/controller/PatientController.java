package com.example.demo.controller;

import com.example.demo.pojo.table.Mortgage;
import com.example.demo.pojo.table.User;
import com.example.demo.pojo.util.RecordCipherInfo;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PatientController
 * @Description: 患者端
 *
 **/
@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private RecordApplyService recordApplyService;

    @Autowired
    private InsurancePolicyService insurancePolicyService;

    @Autowired
    private InsuranceProjectService insuranceProjectService;

    @Autowired
    private MortgageService mortgageService;

    @Autowired
    private InstallmentService installmentService;

    @Autowired
    private UserService userService;

    //显示患者的个人信息
    @RequestMapping("/userInfo")
    public String showUserInfo(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("user", userService.findUserById(patientId));
        return "patient/userInfo";
    }

    //患者修改个人信息后提交操作
    @ResponseBody
    @RequestMapping(value = "/patientInfoSubmit", method = RequestMethod.POST)
    public Object patientInfoSubmit(User user, @RequestParam("picture") MultipartFile picture) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", userService.patientInfoSubmit(user,picture));
        return map;
    }

    @RequestMapping("/home")
    public String home() {
        return "patient/home";
    }

    //显示抵押贷款信息
    @RequestMapping("/mortgage")
    public String showMortgage(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("user", userService.findUserById(patientId));
        modelMap.addAttribute("banks", userService.findAllBank());
        return "patient/mortgage";
    }

    //申请贷款
    @ResponseBody
    @RequestMapping(value = "/addMortgageSubmit", method = RequestMethod.POST)
    public Object addMortgageSubmit(Mortgage mortgage, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        Map<String, Object> map = new HashMap<>();
        mortgage.setBank(userService.findUserById(38));
        map.put("state", mortgageService.addMortgage(mortgage, patientId,file));
        return map;
    }

    //贷款记录列表
    @RequestMapping("/listMortgageRecord")
    public String listMortgageRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("mortgages", mortgageService.listMortgageByPatient(patientId));
        return "patient/listMortgageRecord";
    }

    //贷款记录查看
    @RequestMapping("/lookMortgage")
    public String lookMortgage(String mortgageId, ModelMap modelMap) {
        modelMap.addAttribute("mortgage", mortgageService.findMortgageById(Long.parseLong(mortgageId)));
        return "patient/lookMortgage";
    }

    //缴纳费用
    @RequestMapping("/payment")
    public String payment(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("records", medicalRecordService.listUnPaymentByPatient(patientId));
        return "patient/payment";
    }

    //显示支付信息
    @RequestMapping("/pay")
    public String pay(ModelMap modelMap, String recordId) {
        RecordCipherInfo recordCipherInfo = medicalRecordService.lookRecordByPatient(Long.parseLong(recordId));
        modelMap.addAttribute("user", medicalRecordService.getDoctorByMedicalRecord(Long.parseLong(recordId)));
        modelMap.addAttribute("record", recordCipherInfo);
        String[] costs = recordCipherInfo.getCost().split("\\+");
        long cost = 0;
        for (String s : costs) {
            cost += Long.parseLong(s);
        }
        modelMap.addAttribute("realCost", cost);
        modelMap.addAttribute("accountBalance", medicalRecordService.findMedicalRecordById(Long.parseLong(recordId)).getPatient().getAccountBalance() / 10);
        return "patient/pay";
    }

    //显示支付成功
    @ResponseBody
    @RequestMapping(value = "/paySubmit", method = RequestMethod.POST)
    public Object paySubmit(@RequestBody Map<String, Object> map) {
        String recordId = (String) map.get("recordId");
        String money = (String) map.get("money");
        Map<String, Object> result = new HashMap<>();
        result.put("state", medicalRecordService.paySubmit(Long.parseLong(recordId), (Boolean) map.get("flag"), Long.parseLong(money)));
        return result;
    }

    //显示支付记录列表
    @RequestMapping("/listPayRecord")
    public String listPayRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("records", medicalRecordService.listPaymentByPatient(patientId));
        return "patient/listPayRecord";
    }

    //显示详细的支付信息
    @RequestMapping("/lookPayRecord")
    public String lookPayRecord(ModelMap modelMap, String recordId) {
        modelMap.addAttribute("user", medicalRecordService.getDoctorByMedicalRecord(Long.parseLong(recordId)));
        modelMap.addAttribute("record", medicalRecordService.lookRecordByPatient(Long.parseLong(recordId)));
        return "patient/lookPayRecord";
    }

    //显示分期付款信息
    @RequestMapping("/installment")
    public String installment(ModelMap modelMap, String recordId) {
        modelMap.addAttribute("user", medicalRecordService.getDoctorByMedicalRecord(Long.parseLong(recordId)));
        modelMap.addAttribute("record", medicalRecordService.lookRecordByPatient(Long.parseLong(recordId)));
        return "patient/installment";
    }

    //确定分期付款信息
    @ResponseBody
    @RequestMapping(value = "/installmentSubmit", method = RequestMethod.POST)
    public Object InstallmentSubmit(String recordId, String cycle) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", medicalRecordService.installmentSubmit(Long.parseLong(cycle), Long.parseLong(recordId), 14L));
        return map;
    }

    //显示分期付款记录列表
    @RequestMapping("/listStagingRecord")
    public String listStagingRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("records", medicalRecordService.listStagingByPatient(patientId));
        return "patient/listStagingRecord";
    }

    //显示详细的分期信息
    @RequestMapping("/lookStagingRecord")
    public String lookStagingRecord(ModelMap modelMap, String recordId) {
        modelMap.addAttribute("installment", medicalRecordService.getInstallment(Long.parseLong(recordId)));
        return "patient/lookStagingRecord";
    }

    //分期支付
    @ResponseBody
    @RequestMapping(value = "/installmentPaySubmit", method = RequestMethod.POST)
    public Object installmentPaySubmit(String recordId, String cycle) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", installmentService.installmentPaySubmit(Long.parseLong(recordId), Long.parseLong(cycle)));
        return map;
    }

    //直接支付
    @ResponseBody
    @RequestMapping(value = "/directPaySubmit", method = RequestMethod.POST)
    public Object directPaySubmit(String recordId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", installmentService.directPaySubmit(Long.parseLong(recordId)));
        return map;
    }

    //list患者已确认的医疗记录
    @RequestMapping("/listPatientRecord")
    public String listPatientRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("records", medicalRecordService.listConfirmedRecordByPatient(patientId));
        return "patient/listPatientRecord";
    }

    //list患者未确认的医疗记录
    @RequestMapping("/listUnConfirmRecord")
    public String listUnConfirmRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("records", medicalRecordService.listUnConfirmRecordByPatient(patientId));
        return "patient/listUnConfirmRecord";
    }

    @RequestMapping("/confirmRecord")
    public String confirmRecord(ModelMap modelMap, String recordId) {
        modelMap.addAttribute("record", medicalRecordService.lookRecordByPatient(Long.parseLong(recordId)));
        return "patient/confirmRecord";
    }

    @ResponseBody
    @RequestMapping(value = "/confirmRecordSubmit", method = RequestMethod.POST)
    public Object confirmRecordSubmit(@RequestBody Map<String, Object> map) {
        String s = (String) map.get("recordId");
        return medicalRecordService.confirmRecordSubmit(Long.parseLong(s));
    }

    @RequestMapping("/lookRecord")
    public String lookRecord(ModelMap modelMap, String recordId) {
        modelMap.addAttribute("record", medicalRecordService.lookRecordByPatient(Long.parseLong(recordId)));
        modelMap.addAttribute("flag", medicalRecordService.findMedicalRecordById(Long.parseLong(recordId)).getShareState());
        return "patient/lookRecord";
    }

    @ResponseBody
    @RequestMapping(value = "/shareRecord", method = RequestMethod.POST)
    public Object shareRecord(@RequestBody Map<String, Object> map) {
        String s = (String) map.get("recordId");
        return medicalRecordService.shareRecord(Long.parseLong(s));
    }

    @RequestMapping(value = "/listUnApplyRecord")
    public String listUnApplyRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("applies", recordApplyService.listUnApplyByPatient(patientId));
        return "patient/listUnApplyRecord";
    }

    @RequestMapping(value = "/applyRecord")
    public String applyRecord(String applyId) throws Exception {
        recordApplyService.confirmApply(Long.parseLong(applyId));
        return "redirect:/patient/listApplyRecord";
    }

    @RequestMapping(value = "/listApplyRecord")
    public String listApplyRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("applies", recordApplyService.listApplyByPatient(patientId));
        return "patient/listApplyRecord";
    }

    @RequestMapping(value = "/revokeApply")
    public String revokeApply(String applyId) throws Exception {
        recordApplyService.revokeApply(Long.parseLong(applyId));
        return "redirect:/patient/listApplyRecord";
    }

    //保险
    @RequestMapping("/listInsuranceProject")
    public String listInsuranceProject(ModelMap modelMap) {
        modelMap.addAttribute("insuranceProjects", insuranceProjectService.listAllInsuranceProject());
        return "patient/listInsuranceProject";
    }

    @RequestMapping("/lookInsuranceProject")
    public String lookInsuranceProject(String insuranceProjectId, ModelMap modelMap) {
        modelMap.addAttribute("insuranceProject", insuranceProjectService.lookInsuranceProject(Long.parseLong(insuranceProjectId)));
        return "patient/lookInsuranceProject";
    }

    @RequestMapping("/signInsuranceProject")
    public String signInsuranceProject(String insuranceProjectId, ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("user", userService.findUserById(patientId));
        modelMap.addAttribute("insuranceProjectId", insuranceProjectId);
        return "patient/signInsuranceProject";
    }

    @ResponseBody
    @RequestMapping(value = "/signInsuranceProjectSubmit", method = RequestMethod.POST)
    public Object signInsuranceProjectSubmit(String insuranceProjectId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("state", insurancePolicyService.signInsuranceProjectSubmit(Long.parseLong(insuranceProjectId), patientId));
        return map;
    }

    @RequestMapping("/listInsurancePolicy")
    public String listInsurancePolicy(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("insurancePolicies", insurancePolicyService.listInsurancePolicyByPatient(patientId));
        return "patient/listInsurancePolicy";
    }

    @RequestMapping("/lookInsurancePolicy")
    public String lookInsurancePolicy(String insurancePolicyId, ModelMap modelMap) {
        modelMap.addAttribute("insurancePolicy", insurancePolicyService.lookInsurancePolicyById(Long.parseLong(insurancePolicyId)));
        return "patient/lookInsurancePolicy";
    }

    @RequestMapping("/selectMedicalRecord")
    public String claimInsurancePolicy(String insurancePolicyId, ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("records", medicalRecordService.listConfirmedRecordByPatient(patientId));
        modelMap.addAttribute("insurancePolicyId", insurancePolicyId);
        return "patient/selectMedicalRecord";
    }

    @ResponseBody
    @RequestMapping(value = "/claimInsurancePolicy", method = RequestMethod.POST)
    public Object claimInsurancePolicy(String recordId, String insurancePolicyId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("state", insurancePolicyService.claimInsurancePolicy(Long.parseLong(insurancePolicyId), Long.parseLong(recordId)));
        return map;
    }

    @RequestMapping("/listClaimRecord")
    public String listClaimRecord(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long patientId = (long) session.getAttribute("userId");
        modelMap.addAttribute("insurancePolicies", insurancePolicyService.listClaimRecordByPatient(patientId));
        return "patient/listClaimRecord";
    }
}
