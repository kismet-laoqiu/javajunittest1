package com.example.demo.dao;

import com.example.demo.pojo.table.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @InterfaceName: MedicalRecordDao
 * @Description: MedicalRecord的dao层
 * @Author: 刘敬
 * @Date: 2019/6/12 9:32
 **/
public interface
MedicalRecordDao extends JpaRepository<MedicalRecord, Long> {
    @Query(value = "SELECT m FROM com.example.demo.pojo.table.MedicalRecord m WHERE m.doctor.id=?1")
    List<MedicalRecord> findRecordInfoByDoctor(long id);

    @Query(value = "SELECT m FROM com.example.demo.pojo.table.MedicalRecord m WHERE m.state='等待患者确认'and m.patient.id=?1")
    List<MedicalRecord> findUnConfirmRecordInfoByPatient(long id);

    @Query(value = "SELECT m FROM com.example.demo.pojo.table.MedicalRecord m WHERE m.state='已确认' and m.patient.id=?1")
    List<MedicalRecord> findConfirmedRecordInfoByPatient(long id);

    @Query(value = "SELECT m FROM com.example.demo.pojo.table.MedicalRecord m WHERE m.state='已确认' and m.doctor.id=?1")
    List<MedicalRecord> findConfirmedRecordInfoByDoctor(long id);

    @Query(value = "SELECT m FROM com.example.demo.pojo.table.MedicalRecord m WHERE m.state='已确认' and m.payState='等待患者支付' and m.installmentState='等待患者分期付款' and m.patient.id=?1")
    List<MedicalRecord> findUnPaymentByPatient(long id);

    @Query(value = "SELECT m FROM com.example.demo.pojo.table.MedicalRecord m WHERE m.state='已确认' and m.payState='已支付'  and m.patient.id=?1")
    List<MedicalRecord> findPaymentByPatient(long id);

    @Query(value = "SELECT m FROM com.example.demo.pojo.table.MedicalRecord m WHERE m.state='已确认'  and m.installmentState='已分期' and m.patient.id=?1")
    List<MedicalRecord> findStagingByPatient(long id);

    @Query(value = "SELECT a.medicalRecord FROM com.example.demo.pojo.table.RecordApply a WHERE (a.state='已授权' or a.state='等待授权') and a.doctor.id=?1 and a.medicalRecord.patient.id=?2")
    List<MedicalRecord> findUnApplyRecordInfo(long doctorId, long patientId);

}
