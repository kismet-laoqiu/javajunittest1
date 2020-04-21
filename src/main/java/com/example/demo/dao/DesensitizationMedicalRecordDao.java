package com.example.demo.dao;

import com.example.demo.pojo.display.BuyData;
import com.example.demo.pojo.table.DesensitizationMedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: DesensitizationMedicalRecordDao
 * @Description: 脱敏共享数据Dao
 * @Author: 刘敬
 * @Date：2019/8/4 16:41
 */
public interface DesensitizationMedicalRecordDao extends JpaRepository<DesensitizationMedicalRecord, Long> {
    @Query(value = "select new com.example.demo.pojo.display.BuyData(d.diseaseType,count(d)) from com.example.demo.pojo.table.DesensitizationMedicalRecord d group by d.diseaseType")
    List<BuyData> getBuyDatas();

    Set<DesensitizationMedicalRecord> findByDiseaseType(String diseaseType);
}
