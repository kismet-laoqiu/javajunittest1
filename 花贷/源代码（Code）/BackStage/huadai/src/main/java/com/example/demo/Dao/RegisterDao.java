package com.example.demo.Dao;

import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RegisterDao {

    @Select("select enterprisename from Enterprise where businesslicence = #{businesslicence}")
    public String select_licence(String businesslicence);

    @Insert("insert into Enterprise(enterprisename, businesslicence, publicaccounts, depositbank, accountname, accountowner, owneridentification, ownernumber, Certification, password) VALUES (#{enterprisename}, #{businesslicence}, #{publicaccounts}, #{depositbank}, #{accountname}, #{accountowner}, #{owneridentification}, #{ownernumber}, #{certification}, #{password})")
    public boolean insert_enterprise(Enterprise enterprise);

    @Select("select * from Enterprise where enterprisename=#{enterprisename}")
    public Enterprise select_enterprisename(String enterprisename);

    @Insert("insert into Person(username, password, identification, phonenum, enterprisename, businesslicence) VALUE (#{username}, #{password}, #{identification}, #{phonenum}, #{enterprisename}, #{businesslicence})")
    public boolean insert_person(Person person);

    @Update("update Enterprise set Certification=#{Certification}, password=#{password} where businesslicence=#{businesslicence}")
    public boolean update_enterprise(String businesslicence, String Certification, String password);

    @Update("update Person set refreshtoken = #{refreshtoken} where identification = #{identification}")
    public boolean savetoken(String refreshtoken, String identification);
 }
