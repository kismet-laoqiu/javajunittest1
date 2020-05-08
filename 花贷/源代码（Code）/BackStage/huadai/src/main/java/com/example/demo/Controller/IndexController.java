package com.example.demo.Controller;

import com.example.demo.Service.*;
import com.example.demo.Utils.CalculationUtil;
import com.example.demo.entity.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(path = "/index")
public class IndexController {

    /*
    *   分别收集 企业端 首页 信息，并显示,
    *           员工端 首页 信息，并显示，
    *           提前还款处理
    *           延期还款处理
    * */

    @Autowired
    private EnterpriseIndexService enterpriseIndexService;
    @Autowired
    private IndexPersonService indexPersonService;
    @Autowired
    private PersonalCenterService personalCenterService;
    @Autowired
    private CardService cardService;
    @Autowired
    private SeparateService separateService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping("/enterprise")
    public JSONObject enterprise(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String businesslicence = request.getParameter("businesslicence");
        System.out.println(businesslicence);
        // 获得企业总体概况
        List<Enterprise_information> list_information = enterpriseIndexService.get_overview(businesslicence);
        // 获得历史记录
        List<Enterprise_record> list_record = enterpriseIndexService.get_records(businesslicence);
        // 获得交易记录
        List<Repay_enterprise> list_repay = enterpriseIndexService.get_repay(businesslicence);
        // 贷款套餐,利率说明等
        List<Service_loan> list_service = enterpriseIndexService.get_service(list_information);
        // 计算已贷 / 总额度的比例
        List<Double> list_proportion = CalculationUtil.proportion(list_information);
        // 计算剩余可用额度
        List<Double> list_remain = CalculationUtil.remain(list_information);
        // 计算本月还款、贷款
        List<Double> list_operation = CalculationUtil.month_operation(list_repay);

        map.put("information",list_information);
        map.put("record",list_record);
        map.put("repay", list_repay);
        map.put("service", list_service);
        map.put("proportion",list_proportion);
        map.put("remain", list_remain);
        map.put("operation", list_operation);
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping("/person")
    public JSONObject person(HttpSession session,HttpServletRequest request){
        Map<String, Object>map = new HashMap<>();
        String identification = request.getParameter("identification");
        // 获得用户总体概括
        List<Person_information> list_information = indexPersonService.get_information(identification);
        // 获得历史记录
        List<Person_record> list_record = indexPersonService.get_record(identification);
        // 获得交易记录
        List<Repay_person> list_repay = indexPersonService.get_repay(identification);
        // 贷款套餐说明
        List<Service_loan> list_service = indexPersonService.get_service(list_information);
        // 贷款比例
        double proportion = CalculationUtil.get_proportion(list_information);
        // 剩余额度
        List<Double> list_remain = CalculationUtil.get_remain(list_information);
        // 本月操作
        List<Double> list_operation = CalculationUtil.get_month_operation(list_repay);
        // 用户银行卡
        List<Card> list_card = cardService.select_card(identification);
        // 获得分期购物情况
        List<Separate> list_separate = separateService.select_separate(identification);
        // 最低还款额度
        double mini_repay = CalculationUtil.mini_repay(list_information, list_service, list_separate);
        session.setAttribute("MINI_repay", mini_repay);
        // 银行卡信息
        List<Card> cardList = cardService.select_card(identification);
        Integer sign = messageService.select_num(identification);
        map.put("list_information", list_information);
        map.put("list_record", list_record);
        map.put("list_repay", list_repay);
        map.put("list_service", list_service);
        map.put("proportion", proportion);
        map.put("list_remain", list_remain);
        map.put("list_operation", list_operation);
        map.put("list_card", list_card);
        map.put("list_separate", list_separate);
        map.put("mini_repay", mini_repay);
        map.put("card", cardList);
        map.put("sign",sign);
        return JSONObject.fromObject(map);
    }

    // 还款
    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping("/repay")
    public JSONObject repay(HttpSession session, HttpServletRequest request){
        String identification = request.getParameter("identification");
        Map<String, Object> map = new HashMap<>();
        // 获得用户总体概括
        List<Person_information> list_information = indexPersonService.get_information(identification);
        // 还款额度
        double money_amount = Double.parseDouble(request.getParameter("value"));
        // 贷款套餐说明
        List<Service_loan> list_service = indexPersonService.get_service(list_information);
        // 获得分期购物情况
        List<Separate> list_separate = separateService.select_separate(identification);
        // 最低还款额度
        double mini_repay = CalculationUtil.mini_repay(list_information, list_service, list_separate);

        if(list_information.get(0).getLoan_amount() + list_information.get(1).getLoan_amount() < money_amount || money_amount < mini_repay){
            map.put("state","false");
            map.put("msg","还款金额异常");
            return JSONObject.fromObject(map);
        }
        // 检查银行卡密码是否正确
        Person person = personalCenterService.select_person(identification);
        String card = request.getParameter("card");
        String password = request.getParameter("password");
        if (loginService.test_person(identification, password)){
            // 进行还款操作
            if(indexPersonService.repay(request, person, list_information ,list_separate, mini_repay)){
                map.put("state", "true");
                map.put("msg","还款成功");
            }
        }else {
            map.put("state","false");
            map.put("msg","还款失败");
        }
        return JSONObject.fromObject(map);
    }

    // 延期
    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping("/delay")
    public JSONObject delay(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");



//        // 获得用户总体概括
//        List<Person_information> list_information = indexPersonService.get_information(identification);
//        // 贷款套餐说明
//        List<Service_loan> list_service = indexPersonService.get_service(list_information);
//        // 计算本息
//        double principal = CalculationUtil.get_principal(list_information, list_service);
        indexPersonService.test(identification);
        map.put("state","true");
        map.put("msg","延期成功");
        return JSONObject.fromObject(map);
    }

}