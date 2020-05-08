package com.example.demo.Dao;

import com.example.demo.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommodityDao {

    @Select("select * from commodity")
    public List<Commodity> select_commodity();

    @Select("select * from commodity where commodity_id=#{commodity_id}")
    public Commodity select_one_commodity(String commodity_id);

    @Select("select * from commodity where bussinesslicence = #{bussinesslicence}")
    public Commodity select_bussinesslicence(String bussinesslicence);
}
