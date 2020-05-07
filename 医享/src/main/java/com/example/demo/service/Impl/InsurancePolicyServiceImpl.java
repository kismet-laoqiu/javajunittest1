package com.example.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.chaincode.MedicalRecordChainCode;
import com.example.demo.dao.*;
import com.example.demo.pojo.table.*;
import com.example.demo.pojo.util.RecordCipherInfo;
import com.example.demo.service.InsurancePolicyService;
import com.example.demo.util.IOUtil;
import com.example.demo.util.IPFSUtil;
import com.example.demo.util.attributeEncryption.AttributeEncryption;
import com.example.demo.util.filoop.Filoop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.*;

/**
 * @ClassName: InsurancePolicyServiceImpl
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/6/14 18:07
 **/
@Service
public class InsurancePolicyServiceImpl implements InsurancePolicyService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private InsurancePolicyDao insurancePolicyDao;

    @Autowired
    private InsuranceProjectDao insuranceProjectDao;

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    @Autowired
    private PolicyDao policyDao;

    @Override
    public int signInsuranceProjectSubmit(long insuranceProjectId, long patientId) {
        InsuranceProject insuranceProject = insuranceProjectDao.findById(insuranceProjectId).get();
        User user = userDao.findById(patientId).get();
        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setState("等待确认");
        insurancePolicy.setPatient(user);
        insurancePolicy.setCreateTime(new Date());
        insurancePolicy.setInsuranceProject(insuranceProject);
        insurancePolicyDao.save(insurancePolicy);
        return 200;
    }

    @Override
    public List<InsurancePolicy> listInsurancePolicyByPatient(long patientId) {
        return insurancePolicyDao.listInsurancePolicyByPatient(patientId);
    }

    @Override
    public InsurancePolicy lookInsurancePolicyById(long insurancePolicyId) {
        return insurancePolicyDao.findById(insurancePolicyId).get();
    }

    @Override
    public List<InsurancePolicy> listInsurancePolicyByInsurer(long insurerId) {
        return insurancePolicyDao.listInsurancePolicyByInsurer(insurerId);
    }

    @Override
    public InsurancePolicy lookInsurancePolicyByInsurer(long insurancePolicyId) {
        return insurancePolicyDao.findInsurancePolicy(insurancePolicyId);
    }


    @Override
    public int agreeInsurancePolicy(long insurancePolicyId) {
        InsurancePolicy insurancePolicy = insurancePolicyDao.findById(insurancePolicyId).get();
        insurancePolicy.setState("在保");
        insurancePolicyDao.save(insurancePolicy);
        return 200;
    }

    @Override
    public int refuseInsurancePolicy(long insurancePolicyId) {
        InsurancePolicy insurancePolicy = insurancePolicyDao.findById(insurancePolicyId).get();
        insurancePolicy.setState("已拒绝");
        insurancePolicyDao.save(insurancePolicy);
        return 200;
    }

    @Override
    public List<InsurancePolicy> listProcessedInsurancePolicyByInsurer(long insurerId) {
        return insurancePolicyDao.listProcessedInsurancePolicyByInsurer(insurerId);
    }

    @Override
    public int claimInsurancePolicy(long insurancePolicyId, long recordId) throws Exception {
        InsurancePolicy insurancePolicy = insurancePolicyDao.findById(insurancePolicyId).get();
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        insurancePolicy.setMedicalRecord(medicalRecord);
        insurancePolicy.setState("等待理赔处理");
        insurancePolicyDao.save(insurancePolicy);
        Map<String, String> result = MedicalRecordChainCode.queryMedicalRecord(medicalRecord.getId().toString());
        if (result.get("state").equals("success")) {
            AttributeEncryption.setPath(medicalRecord.getId());
            String hash = result.get("data");
            byte[] text = IPFSUtil.cat(hash);
            String policy = "patientId:" + medicalRecord.getPatient().getId();
            FileOutputStream outputStream = new FileOutputStream(AttributeEncryption.getEncfile());
            outputStream.write(text);
            outputStream.close();
            AttributeEncryption.setAttrs(policy);
            AttributeEncryption.keygen();
            AttributeEncryption.dcode();
            String context = IOUtil.readFile(AttributeEncryption.getDecfile());
            Policy p = new Policy();
            p.setMedicalRecord(medicalRecord);
            p.setType("insurerId");
            p.setValue(insurancePolicy.getInsuranceProject().getInsurer().getId().toString());
            policyDao.save(p);
            List<Policy> policies = policyDao.findByMedicalRecord(medicalRecord);
            policy = makeNewPolicy(policies);
            AttributeEncryption.setPolicy(policy);
            AttributeEncryption.setup();
            outputStream = new FileOutputStream(AttributeEncryption.getInputfile());
            outputStream.write(context.getBytes());
            outputStream.close();
            AttributeEncryption.ecode();
            String path = System.getProperty("user.dir") + "\\" + AttributeEncryption.getEncfile();
            hash = IPFSUtil.add(path);
            MedicalRecordChainCode.updateMedicalRecord(medicalRecord.getId().toString(), hash);
            medicalRecord.setPolicy(policy);
            File file = new File(System.getProperty("user.dir") + "\\" + AttributeEncryption.getEncfile());
            file.delete();
            file = new File(System.getProperty("user.dir") + "\\" + AttributeEncryption.getInputfile());
            file.delete();
            file = new File(System.getProperty("user.dir") + "\\" + AttributeEncryption.getDecfile());
            file.delete();
            medicalRecordDao.save(medicalRecord);
            return 200;
        }
        return 201;
    }

    @Override
    public List<InsurancePolicy> listClaimRecordByPatient(long patientId) {
        return insurancePolicyDao.listClaimRecordByPatient(patientId);
    }

    @Override
    public List<InsurancePolicy> listUnhandledClaimRecord(long insurerId) {
        return insurancePolicyDao.listUnhandledClaimRecord(insurerId);
    }

    @Override
    public RecordCipherInfo lookRecord(long insurancePolicyId) throws Exception {
        InsurancePolicy insurancePolicy = insurancePolicyDao.findById(insurancePolicyId).get();
        MedicalRecord medicalRecord = insurancePolicy.getMedicalRecord();
        Map<String, String> result = MedicalRecordChainCode.queryMedicalRecord(medicalRecord.getId().toString());
        RecordCipherInfo record = new RecordCipherInfo();
        if (result.get("state").equals("success")) {
            AttributeEncryption.setPath(medicalRecord.getId());
            String hash = result.get("data");
            byte[] text = IPFSUtil.cat(hash);
            AttributeEncryption.setPath(medicalRecord.getId());
            String attrs = "insurerId:" + insurancePolicy.getInsuranceProject().getInsurer().getId().toString();
            FileOutputStream outputStream = new FileOutputStream(AttributeEncryption.getEncfile());
            outputStream.write(text);
            outputStream.close();
            AttributeEncryption.setAttrs(attrs);
            AttributeEncryption.keygen();
            AttributeEncryption.dcode();
            String context = IOUtil.readFile(AttributeEncryption.getDecfile());
            record = JSON.parseObject(context, RecordCipherInfo.class);
        }
        return record;
    }

    @Override
    public List<InsurancePolicy> listHandledClaimRecord(long insurerId) {
        return insurancePolicyDao.listHandledClaimRecord(insurerId);
    }

    @Override
    public Map<String, Object> judge(RecordCipherInfo recordCipherInfo, InsurancePolicy insurancePolicy) {
        Map<String, Object> map = new HashMap<>();
        map.put("claimDisease", recordCipherInfo.getDiseaseType());
        map.put("diseaseGrade", "二级");
        String[] costs = recordCipherInfo.getCost().split("\\+");
        int cost = 0;
        for (String s : costs) {
            cost += Long.parseLong(s);
        }
        Date date=new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(insurancePolicy.getCreateTime());
        long time1 = cal.getTimeInMillis();
        cal.setTime(date);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        Map<String, Object> result= Filoop.judge("InsuranceClaim"+insurancePolicy.getInsuranceProject().getId(),Integer.parseInt(String.valueOf(between_days)),cost, recordCipherInfo.getDiseaseType());
        Map<String, Object> data= (Map<String, Object>) result.get("data");
        Boolean flag= (Boolean) data.get("flag");
        BigInteger claimAmount=(BigInteger)data.get("claimAmount");
        map.put("claimAmount", claimAmount.toString());
        map.put("basis", "智能合约自动判断");
        if (flag) {
            map.put("flag", "需理赔");
        } else {
            map.put("flag", "无需理赔");
        }
        return map;
    }

    @Override
    public int agreeClaim(long insurancePolicyId) {
        InsurancePolicy insurancePolicy = insurancePolicyDao.findById(insurancePolicyId).get();
        insurancePolicy.setState("需理赔");
        insurancePolicyDao.save(insurancePolicy);
        return 200;
    }

    @Override
    public int refuseClaim(long insurancePolicyId) {
        InsurancePolicy insurancePolicy = insurancePolicyDao.findById(insurancePolicyId).get();
        insurancePolicy.setState("无需理赔");
        insurancePolicyDao.save(insurancePolicy);
        return 200;
    }

    private String makeNewPolicy(List<Policy> policies) {
        StringBuilder p = new StringBuilder();
        for (Policy policy :
                policies) {
            p.append(policy.getType()).append(":").append(policy.getValue()).append(" ");
        }
        if (policies.size() > 1) {
            p.append("1of").append(policies.size());
        }
        return p.toString();
    }


}
