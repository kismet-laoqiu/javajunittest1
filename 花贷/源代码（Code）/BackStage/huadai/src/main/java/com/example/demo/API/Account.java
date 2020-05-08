package com.example.demo.API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Card;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {


    /*
    * 提取接口 Account 信息
     */
    public static String getAccountJSONInformation(String access_token) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String information = null;
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/accounts?nextStartIndex=1")
                .get()
                .addHeader("authorization", "Bearer "+access_token)
                .addHeader("uuid", UUID.randomUUID().toString())
                .addHeader("accept", "application/json")
                .addHeader("client_id", "f89aa408-e3f9-4a35-bdf1-c63754871af4")
                .build();

        Response response = client.newCall(request).execute();
        //String phoneInformation = Authorize.getResponseBody(request);
        ResponseBody RB = response.body();
        return RB.string();
    }


    /*
    *      提取 Account 信息中的 displayPolicyNumber （卡号） accountId  currentBalance（余额）
    */
    public static List<Card> getAccountInformation(String information, String state){
        List<Card> cardList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(information);
        JSONArray jsonArray = (JSONArray) ((JSONObject)(((JSONArray)(jsonObject.get("accountGroupSummary"))).get(0))).get("accounts");
        for (int i = 0; i< jsonArray.size() ; i++){
            JSONObject obj = null;
            obj = (JSONObject) jsonArray.getJSONObject(i).get("savingsAccountSummary");
            if (obj == null)
                obj = (JSONObject) jsonArray.getJSONObject(i).get("callDepositAccountSummary");
            if (obj == null)
                obj = (JSONObject) jsonArray.getJSONObject(i).get("timeDepositAccountSummary");
            if (obj == null)
                obj = (JSONObject) jsonArray.getJSONObject(i).get("premiumDepositAccountSummary");
            if (obj == null)
                obj = (JSONObject) jsonArray.getJSONObject(i).get("mutualFundAccountSummary");
            if (obj == null)
                obj = (JSONObject) jsonArray.getJSONObject(i).get("securitiesBrokerageAccountSummary");
            Card card = new Card();
            card.setIdentification(state);
            card.setCard(obj.get("displayAccountNumber").toString());
            card.setAccountId(obj.get("accountId").toString());
            card.setCurrentBalance(Double.parseDouble(obj.get("currentBalance").toString()));
            cardList.add(card);
        }
        return cardList;
    }

}
