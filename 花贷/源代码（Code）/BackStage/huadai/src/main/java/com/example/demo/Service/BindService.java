package com.example.demo.Service;

import com.example.demo.API.Authorize;
import com.example.demo.Dao.InformationDao;
import com.example.demo.Dao.RegisterDao;
import com.example.demo.entity.Person_information;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BindService {

    /*
    *   绑定花旗银行 API 接口背后操作
    * */

    @Autowired
    private RegisterDao registerDao;
    @Autowired
    private InformationDao informationDao;

    private static Double basesCores = 589.0;
    private static Double e = 2.71828;

    /*
    *   提取token
    * */
    public String getToBind(String code, String id){
        String accessInformation = Authorize.getAccessTokenWithGrantType(code,"http://duanbanyu.picp.net:25345/bindCard");
        String refreshtoken = Authorize.getRefreshToken(accessInformation);
        String accessToken = Authorize.getToken(accessInformation);
        registerDao.savetoken(refreshtoken, id);
        return accessToken;
    }

    /*
    *  计算对应年龄所属得分
    *
    * */
    public Integer AgeMap(Integer age){
        if (age <= 33)
            return -13;
        else if (age<=40)
            return -8;
        else if (age<=45)
            return -6;
        else if (age<=50)
            return -5;
        else if (age<=54)
            return -2;
        else if (age<=59)
            return 5;
        else if (age<=64)
            return 11;
        else
            return 20;
    }

    public Double sigmod(Double x){
        Double a = 4.0;
        Double b = x - basesCores;
        return a*(1/(1+ Math.pow(e, b)));
    }

    /*
    *   计算授信额度，并植入区块链、数据库
    * */
    public boolean insert_loan_amount(String id ,Integer age, Double amount){
        Double integration = basesCores + AgeMap(age);
        Double loan_amount = sigmod(integration);
        int temp = loan_amount.intValue();
        String money = String.valueOf(temp);
        String s = money.substring(0,2);
        s += (money.length() - 2) * '0';
        Double last_amount = Double.valueOf(s);
        Person_information person_information = new Person_information();
        person_information.setIdentification(id);
        person_information.setTotal_amount(last_amount * 0.8);
        person_information.setLoan_amount(0);
        person_information.setServicenum(4);
        boolean flag1 = informationDao.insert_loan_amount(person_information);
        person_information.setTotal_amount(last_amount * 0.2);
        person_information.setServicenum(3);
        boolean flag2 = informationDao.insert_loan_amount(person_information);
        return flag1 && flag2;
    }
}
