package com.example.demo.dao.userInfo;

import com.example.demo.pojo.table.userInfo.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorInfoDao extends JpaRepository<DoctorInfo,Long> {

}
