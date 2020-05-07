package com.example.demo.service;

import com.example.demo.pojo.form.MedicalRecordFormInfo;
import com.example.demo.pojo.table.Installment;
import com.example.demo.pojo.table.MedicalRecord;
import com.example.demo.pojo.table.User;
import com.example.demo.pojo.util.RecordCipherInfo;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: MedicalRecordService
 * @Description: MedicalRecord的service层
 * @Author: 刘敬
 * @Date: 2019/6/12 10:57
 **/
public interface MedicalRecordService {
    Map<String, Object> shareRecord(long recordId);

    MedicalRecord findMedicalRecordById(long recordId);

    int addRecord(MedicalRecordFormInfo medicalRecordFormInfo, long doctorId);

    List<MedicalRecord> listSubmitRecordByDoctor(long doctorId);

    List<MedicalRecord> listUnConfirmRecordByPatient(long patientId);

    List<MedicalRecord> listConfirmedRecordByPatient(long patientId);

    List<MedicalRecord> listConfirmedRecordByDoctor(long doctorId);

    RecordCipherInfo lookRecordByPatient(long recordId);

    Map<String, Object> confirmRecordSubmit(long recordId);

    List<MedicalRecord> listUnApplyRecord(long doctorId, long patientId);

    List<MedicalRecord> listUnPaymentByPatient(long patientId);

    List<MedicalRecord> listPaymentByPatient(long patientId);

    List<MedicalRecord> listStagingByPatient(long patientId);

    int paySubmit(long recordId, Boolean flag,long money);

    int installmentSubmit(long installmentCycle, long recordId, long bankId);

    Installment getInstallment(long recordId);

    User getDoctorByMedicalRecord(long recordId);
}
