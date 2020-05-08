package com.example.yhy.justfortest.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InternetService {
    @POST("/regist/SMS")
    Call<ResponseBody>send_check(
        @Query("phonenum")String phonenum
    );
    @POST("/regist/person/")
    Call<ResponseBody>resign(
            @Query("identification") String identification,
            @Query("username") String  username,
            @Query("password") String password,
            @Query("phonenum") String phonenum,
            @Query("vercode")String vercode,
            @Query("enterprisename") String enterprisename,
            @Query("card") String card ,
            @Query("cardpassword") String cardpassword


    );
    @GET("/personalcenter/message")Call<ResponseBody>message_center(
            @Query("identification") String identification
    );
    @POST("/login/person")
    Call<ResponseBody>login(
            @Query("identification") String identification,
            @Query("password")       String password
    );
    @GET("/index/person")
    Call<ResponseBody>first_page(
            @Query("identification")String identification
    );
    @GET("/personalcenter/person")
    Call<ResponseBody>person(
            @Query("identification")String identification
    );
    @POST("/personalcenter/update_information")
    Call<ResponseBody>update_information(
            @Query("identification")String identification,
            @Query("old_password")String old_password,
            @Query("username")String username,
            @Query("new_password")String new_password,
            @Query("phonenum")      String phonenum
    );

    @GET("/mall/index")
    Call<ResponseBody>shangcheng_page();

    @GET("/mall/buy_num")
    Call<ResponseBody>buy(
            @Query("identification")String identification,
            @Query("num")           String num, //购买数量
            @Query("value")         String value);
    @POST("/mall/pay")
    Call<ResponseBody>pay_card_order(
            @Query("identification")String identification,
            @Query("card")          String card,
            @Query("password")      String password,
            @Query("commodity_id")  String commodity_id,
            @Query("num")           String num,
            @Query("stage")         String stage,
            @Query("order_num")     String order_num
    );
    @POST("/mall/pay")
    Call<ResponseBody>pay_order(
            @Query("identification")String identification,
            @Query("password")      String password,
            @Query("commodity_id")  String commodity_id,
            @Query("num")           String num,
            @Query("stage")         String stage,
            @Query("order_num")     String order_num
    );
    @POST("/mall/pay")
    Call<ResponseBody>pay_card(
            @Query("identification")String identification,
            @Query("card")          String card,
            @Query("password")      String password,
            @Query("commodity_id")  String commodity_id,
            @Query("num")           String num,
            @Query("stage")         String stage
    );
    @POST("/mall/pay")
    Call<ResponseBody>pay(
            @Query("identification")String identification,
            @Query("password")      String password,
            @Query("commodity_id")  String commodity_id,
            @Query("num")           String num,
            @Query("stage")         String stage
    );
    @POST("/mall/reimburse")
    Call<ResponseBody>reim(//报销
            @Query("identification")String identification,
            @Query("password")String password,
                           @Query("description")   String description,
            @Query("commodity_id")  String commodity_id,
            @Query("num")           String num
    );
    @POST("/index/repay")
    Call<ResponseBody>repay(//还款
            @Query("identification")String identification,
            @Query("value")         String value
    );
    @POST("/index/delay")
    Call<ResponseBody>delay(
            @Query("identification")String identification
    );
    @GET("/reimburse_person")
    Call<ResponseBody>reim_person(
            @Query("identification")String identification
    );
    @GET ("/qrcode")
    Call<ResponseBody>qr_code(
            @Query("identification")String identification,
            @Query("td_code")       String td_code
    );
    @POST("/codecenter")
    Call<ResponseBody>codecenter(
            @Query("identification")String identification,
            @Query("password")      String password
    );
    @POST("/card/bindcard")
    Call<ResponseBody>bindCard(
            @Query("identification")String identification,
            @Query("card")          String card,
            @Query("password")      String password
    );
    @POST("/card/revokecard")
    Call<ResponseBody>revokeCard(
            @Query("identification")String identification,
            @Query("card")      String card,
            @Query("password")  String login_password
    );
    @POST("/transfer")
    Call<ResponseBody>transfer(
        @Query("card")          String card,
        @Query("destination")   String destination_name,
        @Query("payee")         String payee,
        @Query("receive_bank")  String receive_bank,
        @Query("value")         String value,
        @Query("password")      String password
    );
}
