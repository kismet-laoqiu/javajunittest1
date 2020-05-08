package com.example.demo.Dao;

import com.example.demo.entity.Card;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface CardDao {

    @Insert("insert into Cards(identification, card, cardpassword) VALUE (#{identification}, #{card}, #{cardpassword})")
    public boolean insert_card(Card card);

    @Select("select * from Cards where identification = #{identification}")
    public List<Card> select_card(String identification);

    @Select("select * from Cards where card=#{card} and cardpassword=#{password}")
    public Card test_card(String card, String password);

    @Delete("delete from Cards where identification = #{identification} and card = #{card}")
    public boolean delete_card(String identification, String card);

    @Select("select identification from Cards where card = #{card}")
    public String select_id(String card);

    @Select("select * from Card where card = #{card}")
    public Card select_cardall(String card);

    @Update("update Card set currentBalance = currentBalance - #{amount} where card = #{card}")
    public boolean update_currentbalance_1(Double amount, String card );

    @Update("update Card set currentBalance = currentBalance + #{amount} where card = #{card}")
    public boolean update_currentbalance_2(Double amount, String card );
}
