package com.example.demo.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.Service.CommodityService;
import com.example.demo.Service.MessageService;
import com.example.demo.Service.PersonalCenterService;
import com.example.demo.Service.ReimburseService;
import com.example.demo.client.ReimburseClient;
import com.example.demo.entity.*;
import jnr.ffi.annotations.In;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReimburseController {

    /*
    *
    *   报销相关系列操作：
    *          标签对应函数，映射标签对应类别
    *
    *          进入企业报销中心，查看企业收到的报销业务
    *          确认报销
    *          拒绝报销
    *          进入个人报销中心
    * */

    @Autowired
    private ReimburseService reimburseService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private PersonalCenterService personalCenterService;
    @Autowired
    private MessageService messageService;


    public String Label_mapping(Integer label){
        switch (label){
            case 1:
                return "酒店、交通";
            case 2:
                return "服装、箱包";
            case 3:
                return "美妆、个人健康";
            case 4:
                return "厨具、家具";
            case 5:
                return "书、电子书";
            case 6:
                return "玩具、母婴、家庭会员";
            case 7:
                return "电脑、办公、文具";
            case 8:
                return "户外运动";
            case 9:
                return "手机、摄影、数码";
            case 10:
                return "电子配件、智能生活";
            case 11:
                return "食品、营养品";
            case 12:
                return "家电、家装";
            case 13:
                return "游戏、娱乐、乐器";
            case 14:
                return "钟表、首饰";
            case 15:
                return "金融服务产品";
        }
        return "";
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/reimburse")
    public JSONArray reimburse(HttpServletRequest request){
        String businesslicence = request.getParameter("businesslicence");
        Integer state = new Integer("0");
        if (request.getParameter("state") != null)
            state = Integer.valueOf(request.getParameter("state"));
        List<Reimburse> reimburseList = new ArrayList<>();
        if(state == null){
            System.out.println(1);
            reimburseList = reimburseService.select_reimburse(businesslicence);
        } else{
            System.out.println(2);
            reimburseList = reimburseService.select_state_reimburse(businesslicence,state);
        }
        for(int i=0; i< reimburseList.size(); i++){
            Commodity commodity = commodityService.select_bussinesslicence(reimburseList.get(i).getService_bussinesslicence());
            reimburseList.get(i).setServiceName(commodity.getName());
            reimburseList.get(i).setLabel(Label_mapping(commodity.getLabel()));
            Enterprise enterprise = personalCenterService.select_enterprise(commodity.getBussinesslicence());
            reimburseList.get(i).setServiceProvider(enterprise.getEnterprisename());
            Person person = personalCenterService.select_person(reimburseList.get(i).getIdenfication());
            reimburseList.get(i).setApplicant(person.getUsername());
        }
        return JSONArray.parseArray(JSON.toJSONString(reimburseList));
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/confirm_reimburse")
    public JSONObject confirm_reimburse(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try{
            Integer order_num = Integer.valueOf(request.getParameter("order_num"));
            System.out.println(order_num);
            if(reimburseService.confirm_state(order_num)){
                Reimburse reimburse = reimburseService.select_reimburse_order_num(order_num);
                Enterprise enterprise = reimburseService.get_enterprise(reimburse.getBussinesslicence());
                Enterprise_information enterprise_information = reimburseService.get_enterprise_information(reimburse.getBussinesslicence());
                reimburseService.insert_repay(enterprise, reimburse.getValue(), enterprise_information.getLoan_number());
                reimburseService.update_information(reimburse.getValue(), reimburse.getBussinesslicence());
                messageService.insert_message(reimburse.getIdenfication(), 2, null);
                ReimburseClient reimburseClient = new ReimburseClient();
                reimburseClient.updateReiState(String.valueOf(order_num), new BigInteger("1"), "null");
                map.put("state", "true");
                map.put("msg","报销成功");
            }else {
                map.put("state", "flase");
                map.put("msg","报销失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("state", "flase");
            map.put("msg","报销失败");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/refuse_reimburse")
    public JSONObject refuse_reimburse(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            ReimburseClient reimburseClient = new ReimburseClient();
            reimburseClient.initialize();
            reimburseClient.deployReiAssetAndRecordAddr();

            Integer order_num = Integer.valueOf(request.getParameter("order_num"));
            String description = request.getParameter("description");
            if (description == null)
                description = "null";
            if(reimburseService.refuse_state(order_num, description)){
                Reimburse reimburse = reimburseService.select_reimburse_order_num(order_num);
                messageService.insert_message(reimburse.getIdenfication(), 3, null);
                reimburseClient.updateReiState(request.getParameter("order_num"), new BigInteger("3"), description);
                map.put("state", "true");
                map.put("msg","报销已拒绝");
            }else{
                map.put("state", "flase");
                map.put("msg","系统异常");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("state", "flase");
            map.put("msg","系统异常");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/reimburse_person")
    public JSONArray reimburse_person(HttpServletRequest request){
        String identification = request.getParameter("identification");
        List<Reimburse> reimburseList = reimburseService.select_reimburse_identification(identification);
        for (int i = 0 ; i<reimburseList.size(); i++){
            Commodity commodity = commodityService.select_bussinesslicence(reimburseList.get(i).getService_bussinesslicence());
            reimburseList.get(i).setServiceName(commodity.getName());
            reimburseList.get(i).setLabel(Label_mapping(commodity.getLabel()));
        }
        return JSONArray.parseArray(JSON.toJSONString(reimburseList));
    }
}
