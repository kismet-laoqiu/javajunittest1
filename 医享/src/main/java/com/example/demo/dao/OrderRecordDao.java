package com.example.demo.dao;

import com.example.demo.pojo.table.OrderRecord;
import com.example.demo.pojo.table.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRecordDao extends JpaRepository<OrderRecord, Long> {
    List<OrderRecord> findByUser(User user);
}
