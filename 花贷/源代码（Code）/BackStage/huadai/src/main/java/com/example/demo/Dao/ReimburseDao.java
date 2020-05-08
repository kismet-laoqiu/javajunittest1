package com.example.demo.Dao;

import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Enterprise_information;
import com.example.demo.entity.Reimburse;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReimburseDao {

    @Insert("insert into reimburse(idenfication, bussinesslicence, value, state) VALUE (#{idenfication}, #{bussinesslicence}, #{value}, #{state})")
    public boolean inster_reimburse(Reimburse reimburse);

    @Select("select * from reimburse where bussinesslicence = #{bussinesslicence}")
    public List<Reimburse> select_reimburse(String bussinesslicence);

    @Select("select * from reimburse where bussinesslicence = #{bussinesslicence} and state = #{state}")
    public List<Reimburse> select_state_reimburse(String bussinesslicence, Integer state);

    @Update("update reimburse set state=#{state} where order_num = #{order_num}")
    public boolean update_state_1(Integer state, Integer order_num);

    @Update("update reimburse set state=#{state} and description = #{description} where order_num = #{order_num}")
    public boolean update_state_2(Integer state, Integer order_num, String description);

    @Update("update reimburse set state=2 where order_num = #{order_num}")
    public boolean update_state_3(Integer order_num);

    @Select("select * from reimburse where order_num = #{order_num}")
    public Reimburse select_reimburse_order_num(Integer order_num);

    @Select("select * from enterprise_information where businesslicence = #{businesslicence} and servicenum=2")
    public Enterprise_information get_enterprise_information(String businesslicence);

    @Select("select * from Enterprise where businesslicence= #{businesslicence}")
    public Enterprise get_enterprise(String businesslicence);

    @Select("select * from reimburse where idenfication = #{idenfication} and state = #{state}")
    public List<Reimburse> selct_reimburse_idenfication(String idenfication, Integer state);
}
