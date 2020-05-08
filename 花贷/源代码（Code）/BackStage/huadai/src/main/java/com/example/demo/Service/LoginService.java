package com.example.demo.Service;

import com.example.demo.Dao.LoginDao;
import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class LoginService {

    /*
    *   检验用户登录信息
    * */

    @Autowired
    private LoginDao loginDao;

    public boolean login_enterprise(HttpServletRequest request){
        Enterprise enterprise = new Enterprise();
        enterprise.setBusinesslicence(request.getParameter("businesslicence"));
        enterprise.setPassword(request.getParameter("password"));
        List<Enterprise> list =  loginDao.select_enterprise(enterprise);
        return list.size() != 0;
    }

    public boolean login_person(HttpServletRequest request){
        Person person = new Person();
        person.setIdentification(request.getParameter("identification"));
        person.setPassword(request.getParameter("password"));
        List<Person> list = loginDao.select_person(person);
        return list.size() != 0;
    }

    public boolean test_person(String id, String password){
        Person person = new Person();
        person.setIdentification(id);
        person.setPassword(password);
        List<Person> list = loginDao.select_person(person);
        System.out.println(list.size());
        return list.size() != 0;
    }

    public String select_username(String id){
        return loginDao.select_username(id);
    }

    public String select_refreshtoken(String id){
        return loginDao.get_refreshtoken(id);
    }
}
