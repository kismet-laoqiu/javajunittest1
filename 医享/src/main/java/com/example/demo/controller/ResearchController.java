package com.example.demo.controller;

import com.example.demo.pojo.table.User;
import com.example.demo.service.ResearchService;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PatientController
 * @Description: 患者端
 *
 **/
@Controller
@RequestMapping("/research")
public class ResearchController {
    @Autowired
    private ResearchService researchService;

    @Autowired
    private UserService userService;

    //主页
    @RequestMapping("/home")
    public String home() {
        return "research/home";
    }

    //访问机构信息
    @RequestMapping("/userInfo")
    public String researchInfo(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long researchId = (long) session.getAttribute("userId");
        modelMap.addAttribute("user", userService.findUserById(researchId));
        return "research/userInfo";
    }

    //修改机构信息后提交操作
    @ResponseBody
    @RequestMapping(value = "/researchInfoSubmit", method = RequestMethod.POST)
    public Object researchInfoSubmit(User user, @RequestParam("picture") MultipartFile picture) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", userService.researchInfoSubmit(user, picture));
        return map;
    }

    //访问充值医圆
    @RequestMapping("/recharge")
    public String recharge(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long researchId = (long) session.getAttribute("userId");
        User research = userService.findUserById(researchId);
        modelMap.addAttribute("name", research.getName());
        modelMap.addAttribute("organization", research.getResearchInfo().getOrganization());
        return "research/recharge";
    }

    //充值医元
    @ResponseBody
    @RequestMapping("/rechargeSubmit")
    public Object rechargeSubmit(String money, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        long researchId = (long) session.getAttribute("userId");
        map.put("state", researchService.addRechargeRecord(Long.parseLong(money), researchId));
        return map;
    }

    //订单记录
    @RequestMapping("/listOrders")
    public String listOrders(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long researchId = (long) session.getAttribute("userId");
        modelMap.addAttribute("orderRecords", researchService.listOrderRecords(researchId));
        return "research/listOrders";
    }

    //列出医疗数据
    @RequestMapping("/listBuyData")
    public String buyData(ModelMap modelMap) {
        modelMap.addAttribute("buyDatas", researchService.listBuyData());
        return "research/listBuyData";
    }

    //处理购买数据请求
    @ResponseBody
    @RequestMapping("/buyDataSubmit")
    public Object buyDataSubmit(String type, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        long researchId = (long) session.getAttribute("userId");
        map.put("state", researchService.buyData(type, researchId));
        return map;
    }

    //列出下载医疗数据
    @RequestMapping("/listPurchasedData")
    public String dataDownload(ModelMap modelMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long researchId = (long) session.getAttribute("userId");
        modelMap.addAttribute("purchasedDatas", researchService.listPurchasedData(researchId));
        return "research/listPurchasedData";
    }

    //处理下载数据请求
    @RequestMapping("/downloadDataSubmit")
    public void downloadDataSubmit(String purchasedDataId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = request.getSession().getAttribute("userId") + "_" + purchasedDataId + ".xls";
        String filePath = System.getProperty("user.dir") + "\\cache\\" + request.getSession().getAttribute("userId") + "\\" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            researchService.createDataFile(filePath, Long.parseLong(purchasedDataId));
        }
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //上传模型
    @RequestMapping("/updateModel")
    public String updateModel() {
        return "research/updateModel";
    }

    //显示结果
    @RequestMapping("/show")
    public String show() {
        return "research/show";
    }
}
