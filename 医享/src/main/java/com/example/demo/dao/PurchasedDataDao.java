package com.example.demo.dao;

import com.example.demo.pojo.table.PurchasedData;
import com.example.demo.pojo.table.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: PurchasedDataDao
 * @Description: 已购买数据Dao
 * @Author: 刘敬
 * @Date：2019/8/4 16:41
 */
public interface PurchasedDataDao extends JpaRepository<PurchasedData, Long> {
    List<PurchasedData> findByResearch(User user);
}
