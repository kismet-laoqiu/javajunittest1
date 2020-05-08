package com.example.demo.Dao;

import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginDao {

    @Select("select * from Enterprise where businesslicence = #{businesslicence} and password=#{password}")
    public List<Enterprise> select_enterprise(Enterprise enterprise);

    @Select("select * from Person where identification = #{identification} and password=#{password}")
    public List<Person> select_person(Person person);

    @Select("select username from Person where identification = #{identification}")
    public String select_username(String identification);

    @Select("select refreshtoken from Person where identification = #{identification}")
    public String get_refreshtoken(String identification);
}
