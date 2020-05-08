package com.example.demo.Dao;

import com.example.demo.entity.TD_code;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface TD_Dao {

    @Select("select * from `2D_code` where identification = #{identification}")
    public List<TD_code> select_code(String identification);

    @Insert("insert into `2D_code` ( identification, commodity_id, num, value, state, time, name) value (#{identification}, #{commodity_id}, #{num}, #{value}, #{state}, #{time}, #{name})")
    public boolean insert_code(TD_code td_code);

    @Select("select * from `2D_code` where identification = #{identification} and commodity_id = #{commodity_id}")
    public List<TD_code> select_codes(String identification, String commodity_id);

}
