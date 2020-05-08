package com.example.demo.Service;

import com.example.demo.Dao.EnterpriseIndexDao;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnterpriseIndexService {

    @Autowired
    private EnterpriseIndexDao enterpriseIndexDao;

    // 企业总体概况
    public List<Enterprise_information> get_overview(String businesslicence){
        return enterpriseIndexDao.select_information(businesslicence);
    }

    // 企业历史记录
    public List<Enterprise_record> get_records(String businesslicence){
        return enterpriseIndexDao.select_record(businesslicence);
    }

    // 贷款利率等信息
    public List<Service_loan> get_service(List<Enterprise_information> list_information){

        List<Service_loan> list_service = new ArrayList<>();

        for(int i = 0; i < list_information.size(); i++){
            Integer servicenum = list_information.get(i).getServicenum();
            list_service.add(enterpriseIndexDao.select_service(servicenum));
        }
        return list_service;
    }

    public List<Repay_enterprise> get_repay(String bussinesslicense){
        return enterpriseIndexDao.select_repay(bussinesslicense);
    }
}
