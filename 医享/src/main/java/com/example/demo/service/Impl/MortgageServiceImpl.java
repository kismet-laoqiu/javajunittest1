package com.example.demo.service.Impl;

import com.example.demo.dao.MortgageDao;
import com.example.demo.dao.UserDao;
import com.example.demo.pojo.table.Mortgage;
import com.example.demo.pojo.table.User;
import com.example.demo.service.MortgageService;
import com.example.demo.util.cloudStorage.CloudStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: MortgageServiceImpl
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/8/6 22:54
 **/
@Service
public class MortgageServiceImpl implements MortgageService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MortgageDao mortgageDao;

    //获得抵押贷款信息
    @Override
    public Mortgage findMortgageById(long mortgageId) {
        return mortgageDao.findById(mortgageId).get();
    }

    //抵押贷款申请
    @Override
    public int addMortgage(Mortgage mortgage, long patientId, MultipartFile file) {
        User user = userDao.findById(patientId).get();
        mortgage.setUser(user);
        mortgage.setCreateTime(new Date());
        mortgage.setState("贷款申请中");
        mortgageDao.save(mortgage);
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            mortgage.setCertificate(CloudStorage.uploadMultipartFile(file, "certificate/" + mortgage.getId() + suffixName));
        }
        mortgageDao.save(mortgage);
        return 200;
    }

    //贷款记录
    @Override
    public List<Mortgage> listMortgageByPatient(long patientId) {
        return mortgageDao.findByUser(userDao.findById(patientId).get());
    }

    //列出审核过的
    @Override
    public List<Mortgage> listExaminedMortgageByBank(long bankId) {
        List<Mortgage> list = mortgageDao.findByBankAndState(userDao.findById(bankId).get(), "申请通过");
        list.addAll(mortgageDao.findByBankAndState(userDao.findById(bankId).get(), "申请未通过"));
        return list;
    }

    //列出未审核的
    @Override
    public List<Mortgage> listUnExaminedMortgageByBank(long bankId) {
        return mortgageDao.findByBankAndState(userDao.findById(bankId).get(), "贷款申请中");
    }

    @Override
    public int agreeMortgage(long mortgageId) {
        Mortgage mortgage = mortgageDao.findById(mortgageId).get();
        mortgage.setState("申请通过");
        mortgageDao.save(mortgage);
        return 200;
    }

    @Override
    public int refuseMortgage(long mortgageId) {
        Mortgage mortgage = mortgageDao.findById(mortgageId).get();
        mortgage.setState("申请未通过");
        mortgageDao.save(mortgage);
        return 200;
    }
}
