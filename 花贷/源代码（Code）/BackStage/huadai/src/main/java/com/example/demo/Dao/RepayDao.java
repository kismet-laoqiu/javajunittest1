package com.example.demo.Dao;

import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Person_information;
import com.example.demo.entity.Repay_enterprise;
import com.example.demo.entity.Repay_person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RepayDao {

    @Insert("insert into repay_person(username, identification, card, money_amount, servicenum, time) VALUE (#{username}, #{identification}, #{card}, #{money_amount}, #{servicenum}, #{time} )")
    public boolean insert_repay(Repay_person repay_person);

    @Update("update person_information set loan_amount = loan_amount + #{loan_amount} where identification = #{identification} and servicenum = #{servicenum}")
    public boolean update_information(Person_information person_information);

    @Insert("insert into repay_enterprise(name, businesslicence, publicaccounts, money_amount, servicenum, time, loan_num) VALUE (#{name}, #{businesslicence}, #{publicaccounts}, #{money_amount}, #{servicenum}, #{time}, #{loan_num})")
    public boolean insert_repay_enterprise(Repay_enterprise repay_enterprise);

    @Select("select * from Enterprise where businesslicence = #{businesslicence}")
    public Enterprise select_enterprise(String businesslicence);

}
