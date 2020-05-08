package com.example.demo.Controller;

import com.example.demo.API.Authorize;
import com.example.demo.API.Money_Movement;
import com.example.demo.Service.CardService;
import com.example.demo.Service.LoginService;
import com.example.demo.entity.Card;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TransferController {

    @Autowired
    private CardService cardService;
    @Autowired
    private LoginService loginService;

    /*
    *
    *   转账操作
    *
    * */
    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/transfer")
    public JSONObject transfer(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            String card = request.getParameter("card");
            String destination = request.getParameter("destination");
            String payee = request.getParameter("payee");
            Double value = Double.valueOf(request.getParameter("value"));
            String password = request.getParameter("password");
            String id1 = cardService.select_id(card);
            String refreshtoken = loginService.select_refreshtoken(id1);
            String id2 = cardService.select_id(payee);
            String accestoken = Authorize.refreshToken(refreshtoken);
            Card card1 = cardService.selectaccountid(card);
            Card card2 = cardService.selectaccountid(payee);
            String information = Money_Movement.createTransfer(accestoken, card1.getAccountId(), card2.getAccountId(), String.valueOf(value));
            String controlFlowId = Money_Movement.getControlFlowId(information);
            boolean flag = Money_Movement.confirmTransfer(accestoken, controlFlowId);
            if (flag && loginService.test_person(id1, password) && cardService.transfer(card1.getCard(), card2.getCard(), value) && destination.equals(loginService.select_username(id2))){
                //可以转账
                map.put("state","true");
                map.put("msg","转账成功");
            }else {
                map.put("state","false");
                map.put("msg","转账失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("state","false");
            map.put("msg","系统异常");
        }
        return JSONObject.fromObject(map);
    }
}
