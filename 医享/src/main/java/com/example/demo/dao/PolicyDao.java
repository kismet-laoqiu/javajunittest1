package com.example.demo.dao;

import com.example.demo.pojo.table.MedicalRecord;
import com.example.demo.pojo.table.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @InterfaceName: ClaimApplyDao
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/6/26 19:21
 **/
public interface PolicyDao extends JpaRepository<Policy, Long> {
    List<Policy> findByMedicalRecord(MedicalRecord MedicalRecord);

    Policy findByMedicalRecordAndTypeAndValue(MedicalRecord MedicalRecord,String type,String value);

    List<Policy> findByMedicalRecordAndType(MedicalRecord MedicalRecord, String type);

}
