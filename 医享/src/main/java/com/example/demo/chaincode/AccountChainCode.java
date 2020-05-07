package com.example.demo.chaincode;

import com.example.demo.pojo.util.Account;
import com.example.demo.util.filoop.Filoop;

import java.util.Map;

/**
 * @ClassName: AccountChainCode
 * @Description: 医圆账户操作
 * @Author: 刘敬
 * @Date: 2019/8/6 12:33
 **/
public class AccountChainCode {
    public static Map<String, Object> queryAccount(String key) {
        return Filoop.queryAccount(key);
    }

    public static boolean createAccount(Account account) {
        return Filoop.createEntity(account);
    }

    /**
     * @Description: 发布医圆
     * @Author: 刘敬
     * @Date: 2019/8/6 13:11
     * @Param: [id, money]
     * @Return: void
     */
    public static boolean issue(String id, String money) {
        return Filoop.issue(id, money);
    }//  qunian de pingtai

    /**
     * @Description: 转账
     * @Author: 刘敬
     * @Date: 2019/8/6 13:03
     * @Param: [transfererId 转账人, transferreeId 收款人, money]
     * @Return: void
     */
    public static boolean transfer(String transfererId, String transferreeId, String money) {
        return Filoop.transfer(transfererId, transferreeId, money);
    }
}
