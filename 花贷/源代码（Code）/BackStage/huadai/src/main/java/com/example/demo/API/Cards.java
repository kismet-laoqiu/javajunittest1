package com.example.demo.API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Card;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cards {

    private static String client_id = "f89aa408-e3f9-4a35-bdf1-c63754871af4";

    public static String getCardsJSONInformation(String accessToken){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/cards?cardFunction=ALL")
                .get()
                .addHeader("authorization", "Bearer "+accessToken)
                .addHeader("client_id", client_id)
                .addHeader("uuid", UUID.randomUUID().toString())
                .addHeader("accept", "application/json")
                .build();

        String cardsInformation = Authorize.getResponseBody(request);
        return cardsInformation;
    }

    /*
    *   提取信用卡中的  cardId   currentCreditLimitAmount （总信用额度）  displayCardNumber（卡号）
    * */
    public static List<Card> getCardsInformation(String information){
        List<Card> cardList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(information);
        JSONArray jsonArray = (JSONArray)(jsonObject.get("cardDetails"));

        for (int i = 0; i<jsonArray.size(); i++){
            Card card = new Card();
            Object cardId = jsonArray.getJSONObject(i).get("cardId");
            Object displayCardNumber = jsonArray.getJSONObject(i).get("displayCardNumber");
            Object currentCreditLimitAmount = jsonArray.getJSONObject(i).get("currentCreditLimitAmount");
            if (cardId != null)
                card.setAccountId(cardId.toString());
            if (displayCardNumber != null)
                card.setCard(displayCardNumber.toString());
            if (currentCreditLimitAmount != null)
                card.setCurrentBalance(Double.parseDouble(currentCreditLimitAmount.toString()));
            cardList.add(card);
        }
        return cardList;
    }
}
