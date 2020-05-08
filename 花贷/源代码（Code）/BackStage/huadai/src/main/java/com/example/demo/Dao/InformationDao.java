package com.example.demo.Dao;

import com.example.demo.entity.Person_information;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InformationDao {


//    @Select("select * from person_information where identification = #{identification} and servicenum =  #{servicenum}")
//    public Person_information select_information(String identification, Integer servicenum);

    @Update("update enterprise_information set loan_amount = loan_amount+#{loan_amount} where businesslicence=#{businesslicence} and servicenum = 2")
    public boolean update_loan_amount(double loan_amount, String businesslicence);

    @Insert("insert into person_information(identification, total_amount, loan_amount, servicenum) value (#{identification}, #{total_amount}, #{loan_amount}, #{servicenum})")
    public boolean insert_loan_amount(Person_information person_information);
}
