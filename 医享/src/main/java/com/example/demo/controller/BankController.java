package com.example.demo.controller;

import com.example.demo.pojo.table.User;
import com.example.demo.service.InstallmentService;
import com.example.demo.service.MortgageService;
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
 * @ClassName: BankController
 * @Description: 银行端
 * @Author:
 * @Date
 */
@Controller
@RequestMapping("/bank")
public class BankController {
    @Autowired
    private UserService userService;

    @Autowired
    private MortgageService mortgageService;

    @Autowired
    private InstallmentService installmentService;

    @RequestMapping("/home")
    public String home() {
        return "bank/home";
    }

    //显示银行信息
    @RequestMapping("/userInfo")
    public String showBankInfo(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long bankId = (long) session.getAttribute("userId");
        modelMap.addAttribute("user", userService.findUserById(bankId));
        return "bank/userInfo";
    }

    //修改银行信息后提交操作
    @ResponseBody
    @RequestMapping(value = "/bankInfoSubmit", method = RequestMethod.POST)
    public Object bankInfoSubmit(User user, @RequestParam("picture") MultipartFile picture) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", userService.bankInfoSubmit(user,picture));
        return map;
    }

    //贷款审核
    @RequestMapping("/listUnExaminedMortgage")
    public String listUnExaminedMortgage(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long bankId = (long) session.getAttribute("userId");
        modelMap.addAttribute("mortgages", mortgageService.listUnExaminedMortgageByBank(bankId));
        return "bank/listUnExaminedMortgage";
    }

    //贷款记录
    @RequestMapping("/listExaminedMortgage")
    public String listExaminedMortgage(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long bankId = (long) session.getAttribute("userId");
        modelMap.addAttribute("mortgages", mortgageService.listExaminedMortgageByBank(bankId));
        return "bank/listExaminedMortgage";
    }

    //查看贷款记录
    @RequestMapping("/lookExaminedMortgage")
    public String lookExaminedMortgage(String mortgageId, ModelMap modelMap) {
        modelMap.addAttribute("mortgage", mortgageService.findMortgageById(Long.parseLong(mortgageId)));
        return "bank/lookExaminedMortgage";
    }

    //确认贷款记录
    @RequestMapping("/lookUnExaminedMortgage")
    public String lookUnExaminedMortgage(String mortgageId, ModelMap modelMap) {
        modelMap.addAttribute("mortgage", mortgageService.findMortgageById(Long.parseLong(mortgageId)));
        return "bank/lookUnExaminedMortgage";
    }

    //同意贷款
    @ResponseBody
    @RequestMapping(value = "/agreeMortgage", method = RequestMethod.POST)
    public Object agreeMortgage(String mortgageId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", mortgageService.agreeMortgage(Long.parseLong(mortgageId)));
        return map;
    }

    //拒绝贷款
    @ResponseBody
    @RequestMapping(value = "/refuseMortgage", method = RequestMethod.POST)
    public Object refuseMortgage(String mortgageId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", mortgageService.refuseMortgage(Long.parseLong(mortgageId)));
        return map;
    }

    //分期审核
    @RequestMapping("/listUnExaminedInstallment")
    public String listUnExaminedInstallment(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long bankId = (long) session.getAttribute("userId");
        modelMap.addAttribute("installments", installmentService.listUnExaminedInstallmentByBank(bankId));
        return "bank/listUnExaminedInstallment";
    }

    //分期记录
    @RequestMapping("/listExaminedInstallment")
    public String listExaminedInstallment(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long bankId = (long) session.getAttribute("userId");
        modelMap.addAttribute("installments", installmentService.listExaminedInstallmentByBank(bankId));
        return "bank/listExaminedInstallment";
    }

    //查看分期记录
    @RequestMapping("/lookExaminedInstallment")
    public String lookExaminedInstallment(String installmentId, ModelMap modelMap) {
        modelMap.addAttribute("installment", installmentService.findInstallmentById(Long.parseLong(installmentId)));
        return "bank/lookExaminedInstallment";
    }

    //确认分期记录
    @RequestMapping("/lookUnExaminedInstallment")
    public String lookUnExaminedInstallment(String installmentId, ModelMap modelMap) {
        modelMap.addAttribute("installment", installmentService.findInstallmentById(Long.parseLong(installmentId)));
        return "bank/lookUnExaminedInstallment";
    }

    //同意分期
    @ResponseBody
    @RequestMapping(value = "/agreeInstallment", method = RequestMethod.POST)
    public Object agreeInstallment(String installmentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", installmentService.agreeInstallment(Long.parseLong(installmentId)));
        return map;
    }

    //拒绝分期
    @ResponseBody
    @RequestMapping(value = "/refuseInstallment", method = RequestMethod.POST)
    public Object refuseInstallment(String installmentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", installmentService.refuseInstallment(Long.parseLong(installmentId)));
        return map;
    }

    //访问发布医圆
    @RequestMapping("/issue")
    public String recharge(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long bankId = (long) session.getAttribute("userId");
        User bank = userService.findUserById(bankId);
        modelMap.addAttribute("user", bank);
        return "bank/issue";
    }

    //发布医元
    @ResponseBody
    @RequestMapping("/issueSubmit")
    public Object rechargeSubmit(String money, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        long bankId = (long) session.getAttribute("userId");
        map.put("state", userService.issue(Long.parseLong(money), bankId));
        return map;
    }

}