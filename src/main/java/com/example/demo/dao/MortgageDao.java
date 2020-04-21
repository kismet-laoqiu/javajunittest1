package com.example.demo.dao;

import com.example.demo.pojo.table.Mortgage;
import com.example.demo.pojo.table.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: MortgageDao
 * @Description: 抵押贷款信息访问接口
 * @Author: 许建波
 * @Date：2019/7/30 9:05
 */
public interface MortgageDao extends JpaRepository<Mortgage, Long> {
    List<Mortgage> findByUser(User user);
    List<Mortgage> findByBankAndState(User user,String state);
}
