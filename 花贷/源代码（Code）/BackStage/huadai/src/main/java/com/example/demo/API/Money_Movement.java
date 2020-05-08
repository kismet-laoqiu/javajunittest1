package com.example.demo.API;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.UUID;

public class Money_Movement {

    private static String client_id = "f89aa408-e3f9-4a35-bdf1-c63754871af4";

    /*
    *       创建交易
    * */
    public static String createTransfer(String accessToken, String sourceAccountId, String transactionAmount, String destinationAccountId){
        // OkHttpClient from http://square.github.io/okhttp/

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n" +
                "  \"sourceAccountId\":\"" +  sourceAccountId + "\",\n" +
                "  \"paymentMethod\": \"GIRO\",\n" +
                "  \"transactionAmount\":" + transactionAmount +",\n" +
                "  \"transferCurrencyIndicator\": \"SOURCE_ACCOUNT_CURRENCY\",\n" +
                "  \"destinationAccountId\":\"" + destinationAccountId +"\",\n" +
                "  \"chargeBearer\": \"BENEFICIARY\",\n" +
                "  \"fxDealReferenceNumber\": 123456,\n" +
                "  \"remarks\": \"1\"\n" +
                "}");
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/personalDomesticTransfers/preprocess")
                .post(body)
                .addHeader("authorization", "Bearer " + accessToken)
                .addHeader("uuid", UUID.randomUUID().toString())
                .addHeader("client_id", client_id)
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build();

        String transferInformation = Authorize.getResponseBody(request);
        return transferInformation;
    }


    /*
    *       提取 controlFlowId
    * */
    public static String getControlFlowId(String information){
        JSONObject jsonObject = JSONObject.parseObject(information);
        //JSONObject jsonObject = new JSONObject().parseObject(information);
        return jsonObject.get("controlFlowId").toString();
    }


    /*
    *  确认交易
    *
    * */
    public static boolean confirmTransfer(String accessToken, String controlFlowId){

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"controlFlowId\":\"" + controlFlowId +"\"}");
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/personalDomesticTransfers")
                .post(body)
                .addHeader("authorization", "Bearer " + accessToken)
                .addHeader("uuid", UUID.randomUUID().toString())
                .addHeader("client_id", client_id)
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build();

        String transferInformation = Authorize.getResponseBody(request);
        //JSONObject jsonObject = new JSONObject().parseObject(transferInformation);
        JSONObject jsonObject = JSONObject.parseObject(transferInformation);
        if(jsonObject.get("transactionReferenceId") != null)
            return true;
        else
            return false;
    }
}
