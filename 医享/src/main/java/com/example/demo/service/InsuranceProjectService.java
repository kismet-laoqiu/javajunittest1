package com.example.demo.service;

import com.example.demo.pojo.table.InsuranceProject;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: InsurancePolicyService
 * @Description: InsurancePolicy的service层
 * @Author: 刘敬
 * @Date: 2019/6/14 18:07
 **/
public interface InsuranceProjectService {
    Map<String, Object> addInsuranceProject(InsuranceProject medicalRecordFormInfo, long insurerId);

    List<InsuranceProject> listInsuranceProject(long insurerId);

    List<InsuranceProject> listAllInsuranceProject();

    InsuranceProject lookInsuranceProject(long insuranceProjectId);

    int deleteInsuranceProject(long insuranceProjectId);
}
