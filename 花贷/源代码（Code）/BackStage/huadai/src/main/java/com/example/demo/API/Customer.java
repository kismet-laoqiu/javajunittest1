package com.example.demo.API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Customer {
    public static String getCustomerJSONInformation(String accessToken) throws IOException {
        String client_id = "f89aa408-e3f9-4a35-bdf1-c63754871af4";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/customers/profiles")
                .get()
                .addHeader("authorization", "Bearer "+accessToken)
                .addHeader("uuid", UUID.randomUUID().toString())
                .addHeader("client_id", client_id)
                .addHeader("accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        //String phoneInformation = Authorize.getResponseBody(request);
        ResponseBody RB = response.body();
        return RB.string();
    }

    // 获取客户经济情况
    public static Double getCustomerInformation(String information){
        JSONObject jsonObject = JSONObject.parseObject(information);
        JSONArray jsonArray = (JSONArray)((JSONObject)jsonObject.get("financialInformation")).get("incomeDetails");
        double ms = 0;
        for (int i = 0;i < jsonArray.size(); i++){
            Double fixedAmount = Double.parseDouble(jsonArray.getJSONObject(i).get("fixedAmount").toString());
            Double variableAmount = Double.parseDouble(jsonArray.getJSONObject(i).get("variableAmount").toString());
            System.out.println(fixedAmount);
            System.out.println(variableAmount);
            ms += (fixedAmount + variableAmount);
        }
        return ms;
    }

    // 获得使用年龄
    public static Integer getStartTime(String information){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Integer end_time = Integer.parseInt(sdf.format(d));
        JSONObject jsonObject = JSONObject.parseObject(information);
        String dateOfBirth = ((JSONObject)(jsonObject.get("demographics"))).get("dateOfBirth").toString();
        Integer start_time = Integer.parseInt(dateOfBirth.substring(0,4));
        return end_time-start_time;
    }
}
