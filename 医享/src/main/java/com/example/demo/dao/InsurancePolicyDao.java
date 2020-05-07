package com.example.demo.dao;

import com.example.demo.pojo.table.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @InterfaceName: InsurancePolicyDao
 * @Description: InsurancePolicy的dao层
 * @Author: 刘敬
 * @Date: 2019/6/14 18:06
 **/
public interface InsurancePolicyDao extends JpaRepository<InsurancePolicy, Long> {
    @Query(value = "SELECT p FROM com.example.demo.pojo.table.InsurancePolicy p WHERE p.patient.id = ?1 and (p.state='等待确认' or p.state='在保')")
    List<InsurancePolicy> listInsurancePolicyByPatient(long patientId);

    @Query(value = "SELECT p FROM com.example.demo.pojo.table.InsurancePolicy p WHERE p.insuranceProject.insurer.id = ?1 and p.state='等待确认' ")
    List<InsurancePolicy> listInsurancePolicyByInsurer(long insurerId);

    @Query(value = "SELECT p FROM com.example.demo.pojo.table.InsurancePolicy p WHERE p.id = ?1")
    InsurancePolicy findInsurancePolicy(long insurancePolicyId);

    @Query(value = "SELECT p FROM com.example.demo.pojo.table.InsurancePolicy p WHERE p.insuranceProject.insurer.id = ?1 and (p.state='在保' or p.state='已拒绝')")
    List<InsurancePolicy> listProcessedInsurancePolicyByInsurer(long insurerId);

    @Query(value = "SELECT p FROM com.example.demo.pojo.table.InsurancePolicy p WHERE p.patient.id = ?1 and (p.state='等待理赔处理' or p.state='需理赔' or p.state='无需理赔')")
    List<InsurancePolicy> listClaimRecordByPatient(long patientId);

    @Query(value = "SELECT p FROM com.example.demo.pojo.table.InsurancePolicy p WHERE p.insuranceProject.insurer.id = ?1 and p.state='等待理赔处理' ")
    List<InsurancePolicy> listUnhandledClaimRecord(long insurerId);

    @Query(value = "SELECT p FROM com.example.demo.pojo.table.InsurancePolicy p WHERE p.insuranceProject.insurer.id = ?1 and (p.state='需理赔' or p.state='无需理赔') ")
    List<InsurancePolicy> listHandledClaimRecord(long insurerId);
}
