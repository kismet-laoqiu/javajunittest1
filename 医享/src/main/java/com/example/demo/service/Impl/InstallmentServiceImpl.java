package com.example.demo.service.Impl;

import com.example.demo.dao.InstallmentDao;
import com.example.demo.dao.MedicalRecordDao;
import com.example.demo.dao.UserDao;
import com.example.demo.pojo.table.Installment;
import com.example.demo.pojo.table.MedicalRecord;
import com.example.demo.pojo.util.RecordCipherInfo;
import com.example.demo.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: InstallmentServiceImpl
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/8/6 22:58
 **/
@Service
public class InstallmentServiceImpl implements InstallmentService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private InstallmentDao installmentDao;

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    //获得分期信息
    @Override
    public Installment findInstallmentById(long installmentId) {
        return installmentDao.findById(installmentId).get();
    }

    //列出审核过的
    @Override
    public List<Installment> listExaminedInstallmentByBank(long bankId) {
        List<Installment> list = installmentDao.findAll();
        list.removeAll(installmentDao.findByBankAndState(userDao.findById(bankId).get(), "等待确认"));
        return list;
    }

    //列出未审核的
    @Override
    public List<Installment> listUnExaminedInstallmentByBank(long bankId) {
        return installmentDao.findByBankAndState(userDao.findById(bankId).get(), "等待确认");
    }

    @Override
    public int agreeInstallment(long installmentId) {
        Installment installment = installmentDao.findById(installmentId).get();
        installment.setState("申请通过");
        installmentDao.save(installment);
        return 200;
    }

    @Override
    public int refuseInstallment(long installmentId) {
        Installment installment = installmentDao.findById(installmentId).get();
        installment.setState("申请未通过");
        installmentDao.save(installment);
        return 200;
    }

    @Override
    public int installmentPaySubmit(long recordId, long cycle) {
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        Installment installment = installmentDao.findByMedicalRecord(medicalRecord);
        if (installment.getPaidCycle() + cycle >= installment.getCycle()) {
            installment.setPaidCycle(installment.getCycle());
            installment.setState("已支付完成");
        } else {
            installment.setPaidCycle(installment.getPaidCycle() + cycle);
        }
        medicalRecordDao.save(medicalRecord);
        installmentDao.save(installment);
        return 200;
    }

    @Override
    public int directPaySubmit(long recordId) {
        MedicalRecord medicalRecord = medicalRecordDao.findById(recordId).get();
        Installment installment = installmentDao.findByMedicalRecord(medicalRecord);
        installment.setState("已直接支付");
        medicalRecord.setPayState("已支付");
        medicalRecord.setInstallmentState("已直接支付");
        medicalRecordDao.save(medicalRecord);
        installmentDao.save(installment);
        return 200;
    }


}
