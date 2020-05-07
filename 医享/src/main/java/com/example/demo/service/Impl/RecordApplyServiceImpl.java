package com.example.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.chaincode.MedicalRecordChainCode;
import com.example.demo.dao.MedicalRecordDao;
import com.example.demo.dao.PolicyDao;
import com.example.demo.dao.RecordApplyDao;
import com.example.demo.dao.UserDao;
import com.example.demo.pojo.table.MedicalRecord;
import com.example.demo.pojo.table.Policy;
import com.example.demo.pojo.table.RecordApply;
import com.example.demo.pojo.table.User;
import com.example.demo.pojo.util.RecordCipherInfo;
import com.example.demo.service.RecordApplyService;
import com.example.demo.util.IOUtil;
import com.example.demo.util.IPFSUtil;
import com.example.demo.util.attributeEncryption.AttributeEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RecordApplyServiceImpl
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/6/14 9:26
 **/
@Service
public class RecordApplyServiceImpl implements RecordApplyService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RecordApplyDao recordApplyDao;

    @Autowired
    private PolicyDao policyDao;

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    @Override
    public List<RecordApply> listApplyByDoctor(long doctorId) {
        return recordApplyDao.findApplyByDoctor(doctorId);
    }

    @Override
    public List<RecordApply> listUnApplyByPatient(Long patientId) {
        return recordApplyDao.findUnApplyByPatient(patientId);
    }

    @Override
    public List<RecordApply> listApplyByPatient(Long patientId) {
        return recordApplyDao.findApplyByPatient(patientId);
    }

    @Override
    public void addApply(long doctorId, long recordId) {
        User doctor = userDao.findById(doctorId).get();
        MedicalRecord record = medicalRecordDao.findById(recordId).get();
        RecordApply apply = new RecordApply();
        apply.setApplyTime(new Date());
        apply.setState("等待授权");
        apply.setDoctor(doctor);
        apply.setMedicalRecord(record);
        recordApplyDao.save(apply);
    }

    @Override
    public void reSendApply(long applyId) {
        RecordApply apply = recordApplyDao.findById(applyId).get();
        apply.setApplyTime(new Date());
        apply.setState("等待授权");
        recordApplyDao.save(apply);
    }


    @Override
    public void confirmApply(long applyId) throws Exception {
        RecordApply recordApply = recordApplyDao.findById(applyId).get();
        MedicalRecord medicalRecord = recordApply.getMedicalRecord();
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
            p.setType("doctorId");
            p.setValue(recordApply.getDoctor().getId().toString());
            policyDao.save(p);
            List<Policy> policies = policyDao.findByMedicalRecord(medicalRecord);
            policy = Policy.makeNewPolicy(policies);
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
            recordApply.setState("已授权");
            recordApplyDao.save(recordApply);
        }

    }

    @Override
    public void revokeApply(long applyId) throws Exception {
        RecordApply recordApply = recordApplyDao.findById(applyId).get();
        MedicalRecord medicalRecord = recordApply.getMedicalRecord();
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
            Policy p = policyDao.findByMedicalRecordAndTypeAndValue(medicalRecord, "doctorId", recordApply.getDoctor().getId().toString());
            policyDao.delete(p);
            List<Policy> policies = policyDao.findByMedicalRecord(medicalRecord);
            policy = Policy.makeNewPolicy(policies);
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
            recordApply.setState("已撤销");
            recordApplyDao.save(recordApply);
        }
    }

    @Override
    public RecordCipherInfo lookRecord(long applyId) throws Exception {
        RecordApply recordApply = recordApplyDao.findById(applyId).get();
        MedicalRecord medicalRecord = recordApply.getMedicalRecord();
        Map<String, String> result = MedicalRecordChainCode.queryMedicalRecord(medicalRecord.getId().toString());
        RecordCipherInfo record = new RecordCipherInfo();
        if (result.get("state").equals("success")) {
            AttributeEncryption.setPath(medicalRecord.getId());
            String hash = result.get("data");
            byte[] text = IPFSUtil.cat(hash);
            AttributeEncryption.setPath(medicalRecord.getId());
            String attrs = "doctorId:" + recordApply.getDoctor().getId();
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

}
