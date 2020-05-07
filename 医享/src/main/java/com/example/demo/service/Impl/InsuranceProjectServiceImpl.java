package com.example.demo.service.Impl;

import com.example.demo.dao.InsuranceProjectDao;
import com.example.demo.dao.UserDao;
import com.example.demo.pojo.table.InsuranceProject;
import com.example.demo.pojo.table.User;
import com.example.demo.service.InsuranceProjectService;
import com.example.demo.util.GenerateSolidity;
import com.example.demo.util.filoop.Filoop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: InsurancePolicyServiceImpl
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/6/14 18:07
 **/
@Service
public class InsuranceProjectServiceImpl implements InsuranceProjectService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private InsuranceProjectDao insuranceProjectDao;

    @Override
    public Map<String, Object> addInsuranceProject(InsuranceProject insuranceProject, long insurerId) {
        Map<String, Object> result = new HashMap<>();
        User user = userDao.findById(insurerId).get();
        insuranceProject.setInsurer(user);
        insuranceProject.setCreateTime(new Date());
        insuranceProjectDao.save(insuranceProject);
        Map<String, String> map = new HashMap<>();
        String contractName = "InsuranceClaim" + insuranceProject.getId();
        map.put("claimDisease", insuranceProject.getClaimDisease());
        map.put("insurancePeriod", insuranceProject.getInsurancePeriod());
        map.put("compensationRatio", insuranceProject.getCompensationRatio());
        map.put("generalMedicalInsurance", insuranceProject.getGeneralMedicalInsurance());
        GenerateSolidity.Generate(contractName, map);
        result.put("state", 200);
        result.put("contractAddress", Filoop.deployContract(contractName));
        return result;
    }

    @Override
    public List<InsuranceProject> listInsuranceProject(long insurerId) {
        User user = userDao.findById(insurerId).get();
        return insuranceProjectDao.findByInsurer(user);
    }

    @Override
    public List<InsuranceProject> listAllInsuranceProject() {
        return insuranceProjectDao.findAll();
    }

    @Override
    public InsuranceProject lookInsuranceProject(long insuranceProjectId) {
        return insuranceProjectDao.findById(insuranceProjectId).get();
    }

    @Override
    public int deleteInsuranceProject(long insuranceProjectId) {
        insuranceProjectDao.deleteById(insuranceProjectId);
        return 200;
    }

}
