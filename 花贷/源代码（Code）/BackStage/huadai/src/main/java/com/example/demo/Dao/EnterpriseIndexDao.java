package com.example.demo.Dao;

import com.example.demo.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EnterpriseIndexDao {

    @Select("select * from enterprise_information where businesslicence = #{businesslicence}")
    public List<Enterprise_information> select_information(String businesslicence);

    @Update("update person_information set loan_amount = loan_amount+#{loan_amount} where identification = #{identification} and servicenum = #{servicenum}")
    public boolean update_loan_amount(double loan_amount, String identification, Integer servicenum);

    @Update("update person_information set total_amount = loan_amount+#{total_amount} where identification = #{identification} and servicenum = #{servicenum}")
    public boolean update_total_amount(double total_amount, String identification, Integer servicenum);

    @Select("select * from enterprise_record where businesslicence = #{businesslicence}")
    public List<Enterprise_record> select_record(String businesslicence);

    @Select("select * from Service_loan where servicenum = #{servicenum}")
    public Service_loan select_service(Integer servicenum);

    @Select("select * from repay_enterprise where businesslicence = #{businesslicence}")
    public List<Repay_enterprise> select_repay(String businesslicence);
}
