package com.example.demo.Service;

import com.example.demo.Dao.CardDao;
import com.example.demo.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CardService {

    /*
    *       添加银行卡、 删除银行卡
    *       找寻银行卡持卡者信息
    *       转账交易
    * */

    @Autowired
    private CardDao cardDao;

    public boolean add_card(HttpServletRequest request){
        Card card = new Card();
        card.setIdentification(request.getParameter("identification"));
        card.setCard(request.getParameter("card"));
        card.setCardpassword(request.getParameter("password"));
        return cardDao.insert_card(card);
    }

    public boolean add_cardList(List<Card> cardList) {
        boolean flag = true;
        for (int i = 0 ; i < cardList.size(); i++){
            flag = cardDao.insert_card(cardList.get(i));
            if (!flag)
                break;;
        }
        return flag;
    }


    public List<Card> select_card(String identification){
        return cardDao.select_card(identification);
    }

    public boolean test_card(String card, String password){
        if(cardDao.test_card(card, password) != null) {
            return true;
        }else {
            return false;
        }
    }

    public String select_id(String card){
        return cardDao.select_id(card);
    }

    public boolean delete_card(HttpServletRequest request){
        String identification = request.getParameter("identification");
        String card = request.getParameter("card");
        return cardDao.delete_card(identification, card);
    }

    public Card selectaccountid(String card){
        return cardDao.select_cardall(card);
    }

    public boolean transfer(String card1, String card2, Double amount){
        boolean flag1 = cardDao.update_currentbalance_1( amount, card1);
        boolean flag2 = cardDao.update_currentbalance_2( amount, card2);
        if (flag1 && flag2)
            return true ;
        else
            return false;
    }
}
