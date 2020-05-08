package com.example.demo.Controller;

import com.example.demo.Service.CardService;
import com.example.demo.Service.LoginService;
import com.example.demo.entity.Card;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/card")
public class CardController {

    /*
    *   绑定与解绑银行卡
    * */
    @Autowired
    private CardService cardService;
    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping(value = "/bindcard")
    public JSONObject bindcard(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");
        if (cardService.add_card(request)){
            List<Card> cardList = cardService.select_card(identification);
            map.put("state","true");
            map.put("msg","绑定成功");
            map.put("cardList", cardList);
        } else {
            map.put("state","false");
            map.put("msg","绑定失败");
        }
        return JSONObject.fromObject(map);
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @RequestMapping(value = "/revokecard")
    public JSONObject revokecard(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String identification = request.getParameter("identification");
        if (loginService.login_person(request) && cardService.delete_card(request)){
            List<Card> cardList = cardService.select_card(identification);
            map.put("stete","true");
            map.put("msg","解绑成功");
            map.put("cardList", cardList);
        }else  {
            map.put("state","false");
            map.put("msg","解绑失败");
        }
        return JSONObject.fromObject(map);
    }
}
