package com.example.demo.Dao;

import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Message;
import com.example.demo.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonalCenterDao {

    @Select("select * from Enterprise where businesslicence = #{businesslicence}")
    public Enterprise select_enterprise(String businesslicence);

    @Update("update Enterprise set avatar = #{avatar} where businesslicence = #{businesslicence}")
    public boolean update_avatar(String businesslicence, String avatar);

    @Update("update Enterprise set password = #{password} where businesslicence = #{businesslicence}")
    public boolean update_password(String businesslicence, String password);

    @Select("select * from Enterprise where password = #{password} and businesslicence =#{businesslicence}")
    public Enterprise inspect_password(String password, String businesslicence);

    @Select("select * from Person where identification = #{identification}")
    public Person select_person(String identification);

    @Select("select * from Person where identification = #{identification} and password=#{password}")
    public Person inspect_password_person(String identification, String password);

    @Update("update Person set username = #{username} , password = #{password} , phonenum = #{phonenum} where identification = #{identification}")
    public boolean update_person(Person person);

    @Select("select * from message where identification = #{id}")
    public List<Message> select_message(String id );

    @Update("update message set state = 1 where identification = #{identification}")
    public boolean update_message(String identification);
}
