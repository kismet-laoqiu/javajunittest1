package com.example.demo.dao;

import com.example.demo.pojo.table.InsuranceProject;
import com.example.demo.pojo.table.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @InterfaceName: InsuranceProject
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/7/27 20:47
 **/
public interface InsuranceProjectDao extends JpaRepository<InsuranceProject, Long> {
    List<InsuranceProject> findByInsurer(User user);
}
