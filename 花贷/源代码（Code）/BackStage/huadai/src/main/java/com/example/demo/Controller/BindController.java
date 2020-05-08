package com.example.demo.Controller;

import com.example.demo.API.Account;
import com.example.demo.API.Cards;
import com.example.demo.API.Customer;
import com.example.demo.Service.BindService;
import com.example.demo.Service.CardService;
import com.example.demo.client.AssetClient;
import com.example.demo.contract.Asset;
import com.example.demo.entity.Card;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BindController {

    /*
    *   申请花旗 API 授权后跳转至此, 进行信息的收集与处理
    *   并跳转回到首页
    * */

    @Autowired
    private BindService bindService;
    @Autowired
    private CardService cardService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/bindCard")
    public String bind(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            String code = request.getParameter("code");
            String state = request.getParameter("state");
            String accessToken = bindService.getToBind(code, state);
            // Account
            String information = Account.getAccountJSONInformation(accessToken);
            List<Card> cardList = Account.getAccountInformation(information, state);
            boolean flag1 = cardService.add_cardList(cardList);
            // Cards
            information = Cards.getCardsJSONInformation(accessToken);
            cardList = Cards.getCardsInformation(information);
            boolean flag2 = cardService.add_cardList(cardList);
            // Customer
            information = Customer.getCustomerJSONInformation(accessToken);
            Double amount = Customer.getCustomerInformation(information);
            Integer age = Customer.getStartTime(information);
            // 注入授信额度
            bindService.insert_loan_amount(state, age, amount);
            AssetClient assetClient = new AssetClient();
            assetClient.initialize();
            assetClient.deployAssetAndRecordAddr();
            assetClient.registerAssetAmount(state, amount*0.8, amount * 0.2);
            return "login";
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("state", "false");
            map.put("msg","花旗账户认证失败");
        }
        return "error";
    }
}
