package com.example.demo.dao;

import com.example.demo.pojo.table.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @InterfaceName: UserDao
 * @Description: user表的dao层
 * @Author: 刘敬
 * @Date: 2019/6/5 19:40
 **/
public interface UserDao extends JpaRepository<User, Long> {
    List<User> findByRole(String role);
}
