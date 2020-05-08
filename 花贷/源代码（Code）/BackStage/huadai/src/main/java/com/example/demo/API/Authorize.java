package com.example.demo.API;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authorize {



    /*
    *   发送请求, 提取请求信息
    * */
    public static String getResponseBody(Request request){
        OkHttpClient client = new OkHttpClient();
        String returnInformation = null;
        try{
            Response response = client.newCall(request).execute();

            ResponseBody RB = response.body();
            returnInformation = RB.string();
        }catch (IOException e)
        {
            System.out.println("error");
            System.out.println(e.getMessage());
        }
        return returnInformation;
    }

    /*
    *   获得accesstoken
    * */
    public static String getAccessToken(){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&scope=/api");
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/clientCredentials/oauth2/token/hk/gcb")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Basic Zjg5YWE0MDgtZTNmOS00YTM1LWJkZjEtYzYzNzU0ODcxYWY0OnhFNGdBMmpFNHdTNGVQNmlPM2RPN3dMN21BN21RMXVVNHVUMG9RM2hWOHVEOG9MNGpZ")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        String return_str="";
        try{
            Response response = client.newCall(request).execute();
            ResponseBody RB = response.body();
            //System.out.println(RB.string());
            return_str=RB.string();
        }
        catch (IOException e){
            System.out.println("request error");
        }
        return return_str;
    }

    /*
    *   拼接花旗 API 授权地址
    * */
    public static String getURL(String scope, String countryCode, String businessCode, String locale, String state, String redirect_url){
        String client_id = "f89aa408-e3f9-4a35-bdf1-c63754871af4";
        return "https://sandbox.apihub.citi.com/gcb/api/authCode/oauth2/authorize?response_type=code"+"&client_id="+client_id+"&scope="+scope+"&countryCode="+countryCode+"&businessCode="+businessCode+"&locale="+locale+"&state="+state+"&redirect_uri="+redirect_url;
    }


    public static String getTokenByRF(String url){
        Pattern pattern = Pattern.compile("(\")([^<]*)(\")");
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
			return matcher.group(2);
        }
        return null;
    }


    /*
    *  获取 refreshtoken 与 accesstoken
    * */
    public static String getAccessTokenWithGrantType(String code, String redirect_URL){
        String returnInformation=null;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=authorization_code&code="+code+"&redirect_uri="+redirect_URL);
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/authCode/oauth2/token/hk/gcb")
                .post(body)
                .addHeader("authorization", "Basic Zjg5YWE0MDgtZTNmOS00YTM1LWJkZjEtYzYzNzU0ODcxYWY0OnhFNGdBMmpFNHdTNGVQNmlPM2RPN3dMN21BN21RMXVVNHVUMG9RM2hWOHVEOG9MNGpZ")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept", "application/json")
                .build();
        returnInformation = Authorize.getResponseBody(request);
        return returnInformation;
    }


    /*
    *   从返回 JSON 中提取 accesstoken
    * */
    public static String getToken(String tokenInformation){
        JsonElement je = new JsonParser().parse(tokenInformation);
        String access_token=je.getAsJsonObject().get("access_token").toString();
        access_token = Authorize.getTokenByRF(access_token);
        System.out.println(access_token);
        return access_token;
    }

    /*
    *        提取 refresh_token
    * */
    public static String getRefreshToken(String tokenInformation){
        JsonElement je = new JsonParser().parse(tokenInformation);
        String refresh_access_token=je.getAsJsonObject().get("refresh_token").toString();
        System.out.println("refresh_token" + refresh_access_token);
        return refresh_access_token;
    }


    /*
    *  刷新refreshtoken
    * */
    public static String refreshToken( String formerRefreshToken){
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=refresh_token&refresh_token="+formerRefreshToken);
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/authCode/oauth2/refresh")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Basic Zjg5YWE0MDgtZTNmOS00YTM1LWJkZjEtYzYzNzU0ODcxYWY0OnhFNGdBMmpFNHdTNGVQNmlPM2RPN3dMN21BN21RMXVVNHVUMG9RM2hWOHVEOG9MNGpZ")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        String status = Authorize.getResponseBody(request);
        return status;
    }

    /*
    *  刷新 refreshtoken, 并提取 access_token 与 refresh_token
    * */
    public static String[] getTokenAndRefreshTokenByFormerRefreshToken( String formerRefreshToken){
        String accessInformation = refreshToken(formerRefreshToken);
        System.out.println("accessInformation" + accessInformation);
        JsonElement je = new JsonParser().parse(accessInformation);
        String token =je.getAsJsonObject().get("access_token").toString();
        String refresh_token = je.getAsJsonObject().get("refresh_token").toString();
        String[] tokens = {token,refresh_token};
        return tokens;
    }

    /*
    *       接触 token 授权
    * */
    public static String revokeToken(String token, String tokenType){
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "token="+token+"&token_type_hint="+tokenType);
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/authCode/oauth2/revoke")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Basic Zjg5YWE0MDgtZTNmOS00YTM1LWJkZjEtYzYzNzU0ODcxYWY0OnhFNGdBMmpFNHdTNGVQNmlPM2RPN3dMN21BN21RMXVVNHVUMG9RM2hWOHVEOG9MNGpZ")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        String status = Authorize.getResponseBody(request);

        return status;
    }
}
