package com.example.demo.Dao;

import com.example.demo.entity.Person_record;
import com.example.demo.entity.Person_information;
import com.example.demo.entity.Repay_person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexPersonDao {

    @Select("select * from person_information where identification = #{identification}")
    public List<Person_information> select_information(String identification);

    @Update("update person_information set loan_amount = loan_amount - #{loan_amount} where identification = #{identification} and servicenum=#{servicenum}")
    public boolean update_loan_amount(double loan_amount, String identification, Integer servicenum);

    @Select("select * from person_record where identification = #{identification}")
    public List<Person_record> select_record(String identification);

    @Select("select * from repay_person where identification = #{identification}")
    public List<Repay_person> select_repay(String identification);
}
