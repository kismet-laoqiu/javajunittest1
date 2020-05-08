package com.example.demo.Dao;

import com.example.demo.entity.Mall_consumption_record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Mall_consumption_recordDao {

    @Insert("insert into mall_consumption_record(commodity_id, value, identification, order_num) value (#{commodity_id}, #{value}, #{identification}, #{order_num})")
    public boolean insert_record(Mall_consumption_record mall_consumption_record);
}
