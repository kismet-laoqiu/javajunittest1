package com.example.demo.dao;

import com.example.demo.pojo.table.Installment;
import com.example.demo.pojo.table.MedicalRecord;
import com.example.demo.pojo.table.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: InstallmentDao
 * @Description: 分期付款信息接口
 * @Author: 许建波
 * @Date：2019/7/31 14:32
 */
public interface InstallmentDao extends JpaRepository<Installment, Long> {
    Installment findByMedicalRecord(MedicalRecord medicalRecord);
    List<Installment> findByBankAndState(User user,String state);
}
