package com.example.demo.Dao;

import com.example.demo.entity.Separate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SeparateDao {

    @Select("select * from Separate where identity = #{identity} and last_stage<>0")
    public List<Separate> select_separate(String identity);

    @Update("update Separate set last_stage=last_stage-1 , last_num=last_num-#{last_num} where separate_num=#{separate_num}")
    public boolean update_separate(double last_num, Integer separate_num);

    @Update("update Separate set last_stage=last_stage-1 , last_num=#{last_num} where separate_num=#{separate_num}")
    public boolean update_separation(double last_num, Integer separate_num);

    @Insert("insert into Separate( total_stage, last_stage, total_num, last_num, identity, time, name) VALUE ( #{total_stage}, #{last_stage}, #{total_num}, #{last_num}, #{identity}, #{time}, #{name})")
    public boolean instert_separation(Separate separate);
}
