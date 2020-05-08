package com.example.demo.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.Service.*;
import com.example.demo.Utils.CalculationUtil;
import com.example.demo.Utils.QrCodeUtils;
import com.example.demo.client.AssetClient;
import com.example.demo.client.CodeClient;
import com.example.demo.client.ReimburseClient;
import com.example.demo.contract.Asset;
import com.example.demo.entity.*;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/mall")
public class MallController {

    /*
    *      主要演示商城这块：
    *           搜集商城信息并进行传输
    *           计算 商品分期策略
    *           进行支付
    *           进行报销
    *
    * */

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CardService cardService;
    @Autowired
    private SeparateService separateService;
    @Autowired
    private MallService mallService;
    @Autowired
    private ReimburseService reimburseService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MessageService messageService;

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/index")
    public JSONArray index(){
        List<Commodity> commodityList = commodityService.select_commodity();
        return JSONArray.parseArray(JSON.toJSONString(commodityList));
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @GetMapping(value = "/buy_num")
    public JSONObject buy_num(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");
        String temp_num = request.getParameter("num");
        String temp_value = request.getParameter("value");
        Integer num = new Integer("0");
        Double value = new Double("0");
        if (temp_num != null)
            num = Integer.valueOf(temp_num);
        if (temp_value != null)
            value = Double.valueOf(request.getParameter("value"));
        // 计算各种分期的价格
        List<String> doubleList = CalculationUtil.every_stage(value*num);
        List<Card> cardList = cardService.select_card(identification);
        map.put("doubleList", doubleList);
        map.put("cardList", cardList);
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/pay")
    public JSONObject choice_card(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            String identification = request.getParameter("identification");
            String card = request.getParameter("card");
            Integer num = Integer.valueOf(request.getParameter("num"));
            Integer stage = Integer.valueOf(request.getParameter("stage"));
            Integer  order_num = new Integer("0");
            if (request.getParameter("order_num")!=null)
                order_num = Integer.valueOf(request.getParameter("order_num"));
            if(loginService.login_person(request)){
                Commodity commodity = commodityService.select_one_commodity(request.getParameter("commodity_id"));
                if(stage != 0){
                    // 加入分期
                    separateService.insert_separate(stage, num* commodity.getValue(), identification, commodity.getName());
                }
                // 对information进行调整
                mallService.update_loan_amount(identification, num* commodity.getValue());
                // 记录在repay上
                mallService.insert_repay(identification, card, num* commodity.getValue(), 4);
                // 返回消费二维码凭借
                TD_code td_code = mallService.insert_code(identification, num, commodity);
                AssetClient assetClient = new AssetClient();
                assetClient.initialize();
                assetClient.initialize();
                if(td_code != null && reimburseService.update_state(order_num) && mallService.insert_record(commodity.getCommodity_id(),
                        commodity.getValue(), identification, order_num) && assetClient.commodity_pay(identification,commodity.getValue())){
                    messageService.insert_message(identification, 4, commodity.getName());
                    map.put("state","true");
                    map.put("msg","付款成功");
                    map.put("td_code", td_code);
                    JSONObject jsonObject = JSONObject.fromObject(td_code);
                    byte[] date = QrCodeUtils.encodes(jsonObject.toString(), "/static/images/logo.jpg", true);
                    BASE64Encoder encoder = new BASE64Encoder();
                    String s = encoder.encode(date);
                    CodeClient codeClient = new CodeClient();
                    codeClient.initialize();
                    codeClient.deployCodeAssetAndRecordAddr();
                    codeClient.registerCodeAssetAmount(String.valueOf(td_code.getCode()), s);
                }

            }else{
                map.put("state","false");
                map.put("msg","付款失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("state","false");
            map.put("msg","付款失败");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping("/reimburse")
    public JSONObject reimburse( HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            String identification = request.getParameter("identification");
            String description = request.getParameter("description");
            Person person = reimburseService.select_person(identification);
            Commodity commodity = commodityService.select_one_commodity(request.getParameter("commodity_id"));
            Integer num = Integer.valueOf(request.getParameter("num"));
            Reimburse reimburse = new Reimburse();
            reimburse.setIdenfication(identification);
            reimburse.setBussinesslicence(person.getBusinesslicence());
            reimburse.setValue(num* commodity.getValue());
            reimburse.setDescription(description);
            reimburse.setNum(num);
            reimburse.setService_bussinesslicence(commodity.getBussinesslicence());
            TD_code td_code = mallService.insert_code(identification, num, commodity);
            if(reimburseService.inster_reimburse(reimburse) && td_code!= null){
                ReimburseClient reimburseClient = new ReimburseClient();
                reimburseClient.initialize();
                reimburseClient.deployReiAssetAndRecordAddr();
                reimburseClient.registerReiAssetAmount(reimburse);
                map.put("state","true");
                map.put("msg","成功发送申请，请等待审核");
                map.put("td_code", td_code);
            }else {
                map.put("state","false");
                map.put("msg","发送请求失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("state","false");
            map.put("msg","系统异常");
        }
        return JSONObject.fromObject(map);
    }

}
