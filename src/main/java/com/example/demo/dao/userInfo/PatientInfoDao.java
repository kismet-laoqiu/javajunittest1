package com.example.demo.dao.userInfo;

import com.example.demo.pojo.table.userInfo.PatientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientInfoDao extends JpaRepository<PatientInfo, Long> {

}
