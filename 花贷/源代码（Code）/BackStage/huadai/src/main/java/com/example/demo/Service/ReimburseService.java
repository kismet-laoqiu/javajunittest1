package com.example.demo.Service;

import com.example.demo.Dao.InformationDao;
import com.example.demo.Dao.MessageDao;
import com.example.demo.Dao.PersonalCenterDao;
import com.example.demo.Dao.ReimburseDao;
import com.example.demo.client.ReimburseClient;
import com.example.demo.entity.*;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class ReimburseService {

    @Autowired
    private PersonalCenterDao personalCenterDao;
    @Autowired
    private ReimburseDao reimburseDao;
    @Autowired
    private InformationDao informationDao;
    @Autowired
    private MessageDao messageDao;

    //  插入报销中心
    public boolean inster_reimburse(Reimburse reimburse){
        if (reimburse.getDescription() == null)
            reimburse.setDescription("null");
        Date time = new Date();
        reimburse.setState(0);
        reimburse.setTime(time);
        return reimburseDao.inster_reimburse(reimburse);
    }

    public Person select_person(String identification){
        return personalCenterDao.select_person(identification);
    }

    public List<Reimburse> select_reimburse(String bussinesslicence){
        return reimburseDao.select_reimburse(bussinesslicence);
    }

    public List<Reimburse> select_state_reimburse(String bussinesslicence, Integer state){
        return reimburseDao.select_state_reimburse(bussinesslicence, state);
    }

    public Enterprise select_enterprise(String bussinesslicence)
    {
        return personalCenterDao.select_enterprise(bussinesslicence);
    }

    public boolean confirm_state(Integer order_num){
        return reimburseDao.update_state_1(1, order_num);
    }

    public Reimburse select_reimburse_order_num(Integer order_num){
        return reimburseDao.select_reimburse_order_num(order_num);
    }

    public boolean refuse_state(Integer order_num,String description){
        return reimburseDao.update_state_2(3, order_num, description);
    }

    public boolean update_information(double loan_amount, String businesslicence){
        return informationDao.update_loan_amount(loan_amount, businesslicence);
    }

    public Enterprise_information get_enterprise_information(String businesslicence){
        return reimburseDao.get_enterprise_information(businesslicence);
    }

    public Enterprise get_enterprise(String businesslicence){
        return reimburseDao.get_enterprise(businesslicence);
    }

    public boolean insert_repay(Enterprise enterprise, double value, Integer loan_num){

        Repay_enterprise repay_enterprise = new Repay_enterprise();
        repay_enterprise.setName(enterprise.getEnterprisename());
        repay_enterprise.setBusinesslicence(enterprise.getBusinesslicence());
        repay_enterprise.setPublicaccounts(enterprise.getPublicaccounts());
        repay_enterprise.setMoney_amount(value);
        repay_enterprise.setServicenum(2);
        Date time = new Date();
        repay_enterprise.setTime(time);
        repay_enterprise.setLoan_num(loan_num);
        System.out.println(repay_enterprise);
        return true;
    }

    public List<Reimburse> select_reimburse_identification(String identification){
        return reimburseDao.selct_reimburse_idenfication(identification,3);

    }

    public boolean update_state(Integer order_num) throws Exception {
        if (order_num.compareTo(new Integer("0")) == 0){
            return true;
        }
        ReimburseClient reimburseClient = new ReimburseClient();
        reimburseClient.initialize();
        reimburseClient.deployReiAssetAndRecordAddr();
        reimburseClient.updateReiState(String.valueOf(order_num), new BigInteger("2"), "null");
        return reimburseDao.update_state_3(order_num);
    }
}
