package com.example.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.chaincode.AccountChainCode;
import com.example.demo.chaincode.MedicalRecordChainCode;
import com.example.demo.config.Config;
import com.example.demo.dao.*;
import com.example.demo.pojo.form.MedicalRecordFormInfo;
import com.example.demo.pojo.table.*;
import com.example.demo.pojo.util.RecordCipherInfo;
import com.example.demo.service.MedicalRecordService;
import com.example.demo.util.AESUtil;
import com.example.demo.util.IPFSUtil;
import com.example.demo.util.RSAUtil;
import com.example.demo.util.attributeEncryption.AttributeEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @ClassName: MedicalRecordServiceImpl
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/6/12 10:47
 **/
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    @Autowired
    private OauthDao oauthDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PolicyDao policyDao;

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    @Autowired
    private InstallmentDao installmentDao;

    @Autowired
    private OrderRecordDao orderRecordDao;

    @Autowired
    private DesensitizationMedicalRecordDao desensitizationMedicalRecordDao;

    @Override
    public MedicalRecord findMedicalRecordById(long recordId) {
        return medicalRecordDao.findById(recordId).get();
    }

    @Override
    public int addRecord(MedicalRecordFormInfo medicalRecordFormInfo, long doctorId) {
        RecordCipherInfo recordCipherInfo = new RecordCipherInfo(medicalRecordFormInfo);
        recordCipherInfo.setDoctor_id(doctorId);
        Oauth patientOauth = oauthDao.findByRoleAndOauthTypeAndOauthId("patient", "email", medicalRecordFormInfo.getPatient());
        User patient = patientOauth.getUser();
        if (patientOauth != null) {
            recordCipherInfo.setPatient_id(patientOauth.getUser().getId());
            Date date = new Date();
            recordCipherInfo.setCreateTime(date);
            Optional<User> user = userDao.findById(doctorId);
            if (user.isPresent()) {
                User doctor = user.get();
                try {
                    recordCipherInfo.setSignature(RSAUtil.sign(recordCipherInfo.getName(), doctor.getPrivateKey()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recordCipherInfo.setHospital(doctor.getDoctorInfo().getHospital());
                recordCipherInfo.setDepartment(doctor.getDoctorInfo().getDepartment());
                String text = JSON.toJSONString(recordCipherInfo);
                String secretKey = AESUtil.getRandomCode(16);
                String ciphertext = AESUtil.ecode(text, secretKey);
                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setCreateTime(date);
                medicalRecord.setPatient(patientOauth.getUser());
                medicalRecord.setDoctor(doctor);
                medicalRecord.setState("等待患者确认");
                medicalRecord.setCipherText(ciphertext);
                try {
                    medicalRecord.setSecretKey(RSAUtil.encryptByPublicKey(secretKey, patient.getPublicKey()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                medicalRecordDao.save(medicalRecord);
                return 200;
            } else {
                return 201;
            }

        } else {
            return 201;
        }
    }

    @Override
    public List<MedicalRecord> listSubmitRecordByDoctor(long doctorId) {
        return medicalRecordDao.findRecordInfoByDoctor(doctorId);
    }

    @Override
    public List<MedicalRecord> listUnConfirmRecordByPatient(long patientId) {
        return medicalRecordDao.findUnConfirmRecordInfoByPatient(patientId);
    }

    @Override
    public List<MedicalRecord> listConfirmedRecordByPatient(long patientId) {
        return medicalRecordDao.findConfirmedRecordInfoByPatient(patientId);
    }

    @Override
    public List<MedicalRecord> listUnPaymentByPatient(long patientId) {
        return medicalRecordDao.findUnPaymentByPatient(patientId);
    }

    @Override
    public List<MedicalRecord> listStagingByPatient(long patientId) {
        return medicalRecordDao.findStagingByPatient(patientId);
    }

    @Override
    public List<MedicalRecord> listPaymentByPatient(long patientId) {
        return medicalRecordDao.findPaymentByPatient(patientId);
    }


    @Override
    public List<MedicalRecord> listConfirmedRecordByDoctor(long doctorId) {
        return medicalRecordDao.findConfirmedRecordInfoByDoctor(doctorId);
    }

    @Override
    public RecordCipherInfo lookRecordByPatient(long recordId) {
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        String secretKey = null;
        try {
            secretKey = RSAUtil.decryptByPrivateKey(medicalRecord.getSecretKey(), medicalRecord.getPatient().getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = AESUtil.dcode(medicalRecord.getCipherText(), secretKey);
        return JSON.parseObject(text, RecordCipherInfo.class);
    }

    @Override
    public Map<String, Object> shareRecord(long recordId) {
        Map<String, Object> map = new HashMap<>();
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        medicalRecord.setShareState("已共享");
        String secretKey = null;
        try {
            secretKey = RSAUtil.decryptByPrivateKey(medicalRecord.getSecretKey(), medicalRecord.getPatient().getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = AESUtil.dcode(medicalRecord.getCipherText(), secretKey);
        RecordCipherInfo recordCipherInfo = JSON.parseObject(text, RecordCipherInfo.class);
        DesensitizationMedicalRecord desensitizationMedicalRecord = new DesensitizationMedicalRecord(medicalRecord.getPatient(), medicalRecord.getDoctor(), recordCipherInfo);
        User patient = medicalRecord.getPatient();
        AccountChainCode.transfer("0", patient.getId().toString(), Config.shareDataPrice + "");
        patient.setAccountBalance(patient.getAccountBalance() + Config.shareDataPrice);
        medicalRecordDao.save(medicalRecord);
        userDao.save(patient);
        desensitizationMedicalRecordDao.save(desensitizationMedicalRecord);
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setDescription("脱敏共享获取医元：" + Config.shareDataPrice + "元");
        orderRecord.setMoney(Config.shareDataPrice);
        orderRecord.setType("医元获取");
        orderRecord.setUser(medicalRecord.getPatient());
        orderRecord.setStatus("已处理");
        orderRecord.setCreateTime(new Date());
        orderRecordDao.save(orderRecord);
        map.put("state", 200);
        map.put("money", Config.shareDataPrice);
        return map;
    }

    @Override
    public Map<String, Object> confirmRecordSubmit(long recordId) {
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        String secretKey = null;
        try {
            secretKey = RSAUtil.decryptByPrivateKey(medicalRecord.getSecretKey(), medicalRecord.getPatient().getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = AESUtil.dcode(medicalRecord.getCipherText(), secretKey);
        RecordCipherInfo recordCipherInfo = JSON.parseObject(text, RecordCipherInfo.class);
        Map<String, Object> map = new HashMap<>();
        if (RSAUtil.verify(recordCipherInfo.getName(), medicalRecord.getDoctor().getPublicKey(), recordCipherInfo.getSignature())) {
            AttributeEncryption.setPath(recordId);
            Policy p = new Policy();
            p.setMedicalRecord(medicalRecord);
            p.setType("patientId");
            p.setValue(medicalRecord.getPatient().getId().toString());
            policyDao.save(p);
            String policy = "patientId:" + medicalRecord.getPatient().getId().toString();
            AttributeEncryption.setPolicy(policy);
            String hash="";
            try {
                AttributeEncryption.setup();
                FileOutputStream outputStream = new FileOutputStream(AttributeEncryption.getInputfile());
                outputStream.write(text.getBytes());
                outputStream.close();
                AttributeEncryption.ecode();
                String path = System.getProperty("user.dir") + "\\" + AttributeEncryption.getEncfile();
                hash = IPFSUtil.add(path);
                System.out.println(recordId + "--------" + hash);
                MedicalRecordChainCode.addMedicalRecord(recordId + "", hash);
                medicalRecord.setState("已确认");
                medicalRecord.setShareState("未共享");
                medicalRecord.setPayState("等待患者支付");
                medicalRecord.setInstallmentState("等待患者分期付款");
                medicalRecord.setPolicy(policy);
            } catch (Exception e) {
                e.printStackTrace();
            }
            File file = new File(System.getProperty("user.dir") + "\\" + AttributeEncryption.getEncfile());
            file.delete();
            file = new File(System.getProperty("user.dir") + "\\" + AttributeEncryption.getInputfile());
            file.delete();
            medicalRecordDao.save(medicalRecord);
            map.put("state", 200);
            map.put("hash", hash);
        } else {
            map.put("state", 201);
        }
        return map;
    }

    @Override
    public List<MedicalRecord> listUnApplyRecord(long doctorId, long patientId) {
        List<MedicalRecord> list = medicalRecordDao.findConfirmedRecordInfoByPatient(patientId) ;
        List<MedicalRecord> list2 = medicalRecordDao.findUnApplyRecordInfo(doctorId, patientId);
        list.removeAll(list2);
        return list;
    }

    @Override
    public int paySubmit(long recordId, Boolean flag, long money) {
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        User patient = medicalRecord.getPatient();
        patient.setAccountBalance(patient.getAccountBalance() - money * 10);
        AccountChainCode.transfer(patient.getId().toString(), "0", money * 10 + "");
        medicalRecord.setPayState("已支付");
        medicalRecordDao.save(medicalRecord);
        userDao.save(patient);
        return 200;
    }


    @Override
    public User getDoctorByMedicalRecord(long recordId) {
        return medicalRecordDao.findById(recordId).get().getDoctor();
    }


    @Override
    public int installmentSubmit(long installmentCycle, long recordId, long bankId) {
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        medicalRecord.setInstallmentState("已分期");
        RecordCipherInfo recordCipherInfo = lookRecordByPatient(recordId);
        Installment installment = new Installment(recordCipherInfo, installmentCycle);
        installment.setMedicalRecord(medicalRecord);
        installment.setBank(userDao.findById(bankId).get());
        installment.setState("等待确认");
        medicalRecordDao.save(medicalRecord);
        installmentDao.save(installment);
        return 200;
    }

    @Override
    public Installment getInstallment(long recordId) {
        return installmentDao.findByMedicalRecord(medicalRecordDao.findById(recordId).get());
    }
}

