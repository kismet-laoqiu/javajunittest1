package com.example.yhy.justfortest.API;

import com.example.yhy.justfortest.Interface.InternetService;
import com.example.yhy.justfortest.classs.Internet;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class InternetAPI {
    public static InternetAPI api;
    private InternetService service;
    private InternetAPI(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Internet.httpUrl)
                .build();
        service=retrofit.create(InternetService.class);
    }

    public static InternetAPI getApi() {
        if (null==api)
            api=new InternetAPI();
        return api;
    }
    public Call<ResponseBody>sendCheck(
            String phoneNum
    ){
        return
                service.send_check(phoneNum);
    }
    public Call<ResponseBody>resign(
            String identification,
            String username,
            String password,
            String phonenum,
            String vercode,
            String enterprisename,
            String card ,
            String cardpassword
    ){
        return
                service.resign(identification,
                username,
                password,
                phonenum,
                vercode,
                enterprisename,
                card,
                cardpassword
                );
    }
    public Call<ResponseBody>login(
            String identification,
            String password
    ){
        return service.login(identification,password);
    }

    public Call<ResponseBody>first_page(
            String identification
    ){
        return service.first_page(identification);
    }

    public Call<ResponseBody>person_info(
            String identification
    ){
        return service.person(identification);
    }
    public Call<ResponseBody>updateInfo(
            String id,
            String  old_password,
            String username,
            String new_password,
            String phonenum
    ){
        return service.update_information(id,old_password, username, new_password, phonenum);
    }
    public Call<ResponseBody>shangcheng_page(){
        return service.shangcheng_page();
    }
    public Call<ResponseBody>buy(String identification,
                                 String num, //购买数量
                                 String value){
        return service.buy(identification, num, value);
    }
    public Call<ResponseBody>pay_card_oreder(String id,String card,
                                               String password,
                                               String commodity_id,
                                               String num,
                                               String stage,
                                               String order_num
    ){
        return service.pay_card_order(id,card, password, commodity_id, num, stage, order_num);
    }
    public Call<ResponseBody>pay_card(String id,String card,
                                             String password,
                                             String commodity_id,
                                             String num,
                                             String stage
    ){
        return service.pay_card(id,card, password, commodity_id, num, stage);
    }
    public Call<ResponseBody>pay_oreder(    String id,
                                             String password,
                                             String commodity_id,
                                             String num,
                                             String stage,
                                             String order_num
    ){
        return service.pay_order(id, password, commodity_id, num, stage, order_num);
    }
    public Call<ResponseBody>pay(           String id,
                                             String password,
                                             String commodity_id,
                                             String num,
                                             String stage
    ){
        return service.pay(id, password, commodity_id, num, stage);
    }
    public Call<ResponseBody>reim(
            String identification,
            String password,
            String description,

            String commodity_id,
            String num
    ){
        return service.reim(identification, password,description, commodity_id, num);
    }
    public Call<ResponseBody>repay(String identification,
                                   String value
    ){
        return service.repay(identification, value);
    }
    public Call<ResponseBody>delay(String identification){
        return service.delay(identification);
    }
    public Call<ResponseBody>reim_person(String identification){
        return service.reim_person(identification);
    }
    public Call<ResponseBody>qr_code(String identification,String td_code){
        return service.qr_code(identification,td_code);
    }
    public Call<ResponseBody>message_center(String identification){
        return service.message_center(identification);
    }
    public Call<ResponseBody>codecenter(String identification,String password){
        return service.codecenter(identification,password);
    }
    public Call<ResponseBody>bindCard(String identification,
                                      String card,
                                      String password){
        return service.bindCard(identification, card, password);
    }
    public Call<ResponseBody>revokeCard(String identification,
                                      String card,
                                      String password){
        return service.revokeCard(identification, card, password);
    }
    public Call<ResponseBody>transfer(String card,
                                      String destination_name,
                                      String payee,
                                      String receive_bank,
                                      String value,
                                      String password){
        return service.transfer(card, destination_name, payee, receive_bank, value, password);
    }


}
