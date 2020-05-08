package com.example.demo.Dao;

import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MessageDao {

    @Insert("insert into message(identification, label, content, time, state) value (#{identification}, #{label}, #{content}, #{time}, #{state})")
    public boolean insert_message(Message message);

    @Select("select count(*) from message where identification = #{id} and state = 0")
    public Integer select_num(String id);

    @Update("update message set state = 1 where identification = #{id}")
    public boolean update_state(String id);
}
