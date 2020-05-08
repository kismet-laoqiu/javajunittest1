package com.example.demo.Service;

import com.example.demo.Dao.PersonalCenterDao;
import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Message;
import com.example.demo.entity.Person;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalCenterService {

    @Autowired
    private PersonalCenterDao personalCenterDao;

    public Enterprise select_enterprise(String businesslicence){
        return personalCenterDao.select_enterprise(businesslicence);
    }

    public boolean update_avatar(String businesslicence, String path){
        return personalCenterDao.update_avatar(businesslicence, path);
    }

    public boolean update_password(String businesslicence, String password){
        return personalCenterDao.update_password(businesslicence, password);
    }

    public Enterprise inspect_password(String businesslicence, String password){
        return personalCenterDao.inspect_password(password, businesslicence);
    }

    public Person select_person(String identification){
        return personalCenterDao.select_person(identification);
    }

    public Person inspect_password_person(String identification, String password){
        return personalCenterDao.inspect_password_person(identification, password) ;
    }

    public boolean update_person(Person person){
        return personalCenterDao.update_person(person);
    }

    public List<Message> select_message(String identification){
        return personalCenterDao.select_message(identification);
    }

    public boolean update_message(String identification){
        return personalCenterDao.update_message(identification);
    }
}
