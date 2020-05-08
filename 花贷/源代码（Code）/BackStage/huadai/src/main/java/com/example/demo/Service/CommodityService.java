package com.example.demo.Service;

import com.example.demo.Dao.CommodityDao;
import com.example.demo.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityService {

    @Autowired
    private CommodityDao commodityDao;

    public List<Commodity> select_commodity(){
        return commodityDao.select_commodity();
    }

    public Commodity select_one_commodity(String commodity_id){
        return commodityDao.select_one_commodity(commodity_id);
    }

    public Commodity select_bussinesslicence(String bussinesslicence){
        return commodityDao.select_bussinesslicence(bussinesslicence);
    }
}
