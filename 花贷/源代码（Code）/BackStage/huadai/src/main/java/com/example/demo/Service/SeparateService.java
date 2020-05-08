package com.example.demo.Service;

import com.example.demo.Dao.SeparateDao;
import com.example.demo.entity.Separate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SeparateService {

    @Autowired
    private SeparateDao separateDao;

    public List<Separate> select_separate(String identity){
        return separateDao.select_separate(identity);
    }

    public boolean update_separate(double last_num, Integer separate_num){
        return separateDao.update_separate(last_num, separate_num);
    }

    // 插入分期中心
    public boolean insert_separate(Integer stage, double num, String identity, String name){
        Separate separate = new Separate();
        separate.setTotal_num(stage);
        separate.setLast_stage(stage);
        separate.setTotal_num(num);
        separate.setLast_num(num);
        separate.setIdentity(identity);
        Date date = new Date();
        separate.setTime(date);
        separate.setName(name);
        return separateDao.instert_separation(separate);
    }
}
