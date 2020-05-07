package com.example.demo.service;

import com.example.demo.pojo.table.InsurancePolicy;
import com.example.demo.pojo.util.RecordCipherInfo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: InsurancePolicyService
 * @Description: InsurancePolicy的service层
 * @Author: 刘敬
 * @Date: 2019/6/14 18:07
 **/
public interface InsurancePolicyService {
    int signInsuranceProjectSubmit(long insuranceProjectId, long patientId);

    List<InsurancePolicy> listInsurancePolicyByPatient(long patientId);

    InsurancePolicy lookInsurancePolicyById(long insurancePolicyId);

    List<InsurancePolicy> listInsurancePolicyByInsurer(long insurerId);

    InsurancePolicy lookInsurancePolicyByInsurer(long insurancePolicyId);

    int agreeInsurancePolicy(long insurancePolicyId);

    int refuseInsurancePolicy(long insurancePolicyId);

    List<InsurancePolicy> listProcessedInsurancePolicyByInsurer(long insurerId);

    int claimInsurancePolicy(long insurancePolicyId, long recordId) throws Exception;

    List<InsurancePolicy> listClaimRecordByPatient(long patientId);

    List<InsurancePolicy> listUnhandledClaimRecord(long insurerId);

    RecordCipherInfo lookRecord(long insurancePolicyId) throws Exception;

    List<InsurancePolicy> listHandledClaimRecord(long insurerId);

    Map<String,Object> judge(RecordCipherInfo recordCipherInfo, InsurancePolicy insurancePolicy);

    int agreeClaim(long insurancePolicyId);

    int refuseClaim(long insurancePolicyId);
}
