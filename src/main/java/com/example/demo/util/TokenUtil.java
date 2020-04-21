package com.example.demo.util;

/**
 * @ClassName: TokenUtil
 * @Description: token生成和校验
 * @Author: 刘敬
 * @Date: 2019/6/9 19:46
 **/
public class TokenUtil {
    private static final int VALID_TIME = 60*60*24*5; // token有效期(秒)
    public static final String TOKEN_ERROR = "F"; // 非法
    public static final String TOKEN_OVERDUE = "G"; // 过期
    public static final String TOKEN_FAILURE = "S"; // 失效

    /**
     * 生成token,该token长度不一致,如需一致,可自行MD5或者其它方式加密一下
     * 该方式的token只存在磁盘上,如果项目是分布式,最好用redis存储
     * @param str: 该字符串可自定义,在校验token时要保持一致
     * @return
     */
    public static String generateToken(String str) {
        String token = TokenEncryptUtil.encoded(getCurrentTime()+","+str);
        return token;
    }

    /**
     * 校验token的有效性
     * @param token
     * @return
     */
    public static String validateToken(String token) {
        if (token == null) {
            return TOKEN_ERROR;
        }
        try{
            String[] tArr = TokenEncryptUtil.decoded(token).split(",");
            if (tArr.length != 2) {
                return TOKEN_ERROR;
            }
            // token生成时间戳
            int tokenTime = Integer.parseInt(tArr[0]);
            // 当前时间戳
            int currentTime = getCurrentTime();
            if (currentTime-tokenTime < VALID_TIME) {
                return tArr[1];
            } else {
                return TOKEN_OVERDUE;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return TOKEN_ERROR;
    }

    /**获取当前时间戳（10位整数）*/
    private static int getCurrentTime() {
        return (int)(System.currentTimeMillis()/1000);
    }

}
