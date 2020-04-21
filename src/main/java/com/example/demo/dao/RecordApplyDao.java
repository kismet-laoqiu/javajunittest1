package com.example.demo.dao;

import com.example.demo.pojo.table.RecordApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @InterfaceName: RecordApplyDao
 * @Description: RecordApply的dao层
 * @Author: 刘敬
 * @Date: 2019/6/14 9:09
 **/
public interface RecordApplyDao extends JpaRepository<RecordApply, Long> {
    @Query(value = "SELECT a FROM com.example.demo.pojo.table.RecordApply a WHERE a.doctor.id = ?1")
    List<RecordApply> findApplyByDoctor(Long doctorId);

    @Query(value = "SELECT a FROM com.example.demo.pojo.table.RecordApply a WHERE  a.state='等待授权' and a.medicalRecord.patient.id=?1")
    List<RecordApply> findUnApplyByPatient(Long patientId);

    @Query(value = "SELECT a FROM com.example.demo.pojo.table.RecordApply a WHERE (a.state='已授权' or a.state='已撤销') and a.medicalRecord.patient.id=?1")
    List<RecordApply> findApplyByPatient(Long patientId);

}
