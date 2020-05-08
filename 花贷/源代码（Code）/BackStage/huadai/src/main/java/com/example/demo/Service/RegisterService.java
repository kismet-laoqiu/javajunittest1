package com.example.demo.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Dao.CardDao;
import com.example.demo.Dao.RegisterDao;
import com.example.demo.Utils.SendMessageUtil;
import com.example.demo.entity.Card;
import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Service
public class RegisterService {

    @Autowired
    private RegisterDao registerDao;
    @Autowired
    private CardDao cardDao;

    // 随机生成密码函数
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    // 网页端， 注册
    public boolean enterpriseRegister(HttpServletRequest request){
        Enterprise enterprise = new Enterprise();
        enterprise.setEnterprisename(request.getParameter("enterprisename"));
        enterprise.setBusinesslicence(request.getParameter("businesslicence"));
        enterprise.setPublicaccounts(request.getParameter("publicaccounts"));
        enterprise.setDepositbank(request.getParameter("depositbank"));
        enterprise.setAccountname(request.getParameter("accountname"));
        enterprise.setAccountowner(request.getParameter("accountowner"));
        enterprise.setOwneridentification(request.getParameter("owneridentification"));
        enterprise.setOwnernumber(request.getParameter("ownernumber"));
        if (registerDao.select_licence(enterprise.getBusinesslicence()) == null)
            return registerDao.insert_enterprise(enterprise);
        else
            return false;
    }

    /*   企业认证材料上传   */
    public boolean enterpriseUpload(String businesslicence, String phone_number, String path){
        String password = getRandomString(15);
        Integer number = SendMessageUtil.send("hexianbo","d41d8cd98f00b204e980",phone_number,
                "【花贷】您好，您在花贷平台登记的信息已审核通过,您申请的账号密码为:"+ password +",请勿告诉他人。" );
        return registerDao.update_enterprise(businesslicence, path, password);
    }

    /*  员工注册    */
    public boolean personRegister(HttpServletRequest request){
        Person person = new Person();
        person.setUsername(request.getParameter("username"));
        person.setPassword(request.getParameter("password"));
        person.setIdentification(request.getParameter("identification"));
        person.setPhonenum(request.getParameter("phonenum"));
        person.setEnterprisename(request.getParameter("enterprisename"));

        Enterprise enterprise = registerDao.select_enterprisename(person.getEnterprisename());
        if (enterprise != null){
            person.setBusinesslicence(enterprise.getBusinesslicence());
        }
        else
            return false;
        Integer number = SendMessageUtil.send("hexianbo","d41d8cd98f00b204e980",request.getParameter("phonenum"),
                "【花贷】您好，您在花贷平台登记的信息已审核通过,感谢您对本产品的支持！" );
        return registerDao.insert_person(person);
    }
}
