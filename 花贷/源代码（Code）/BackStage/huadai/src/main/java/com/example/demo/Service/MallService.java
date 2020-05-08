package com.example.demo.Service;

import com.example.demo.Dao.*;
import com.example.demo.entity.*;
import org.fisco.bcos.web3j.abi.datatypes.Int;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MallService {

    /*
    *   市场底层相关操作
    * */

    @Autowired
    private EnterpriseIndexDao enterpriseIndexDao;
    @Autowired
    private PersonalCenterDao personalCenterDao;
    @Autowired
    private RepayDao repayDao;
    @Autowired
    private TD_Dao td_dao;
    @Autowired
    private Mall_consumption_recordDao mall_consumption_recordDao;

    /* 更新授信额度 */
    public boolean update_loan_amount(String identification, double loan_amount){
        return enterpriseIndexDao.update_loan_amount(loan_amount, identification, 4);
    }

    /* 插入交易记录 */
    public boolean insert_repay(String identification, String card, double money_amount, Integer servicenum){
        Repay_person repay_person = new Repay_person();
        Person person = personalCenterDao.select_person(identification);
        repay_person.setUsername(person.getUsername());
        repay_person.setIdentification(identification);
        repay_person.setCard(card);
        repay_person.setMoney_amount(-money_amount);
        repay_person.setServicenum(servicenum);
        Date time = new Date();
        repay_person.setTime(time);
        return repayDao.insert_repay(repay_person);
    }

    /*  二维码信息注入   */
    public TD_code insert_code(String identification, Integer num, Commodity commodity){
        TD_code td_code = new TD_code();
        td_code.setIdentification(identification);
        td_code.setCommodity_id(commodity.getCommodity_id());
        td_code.setNum(num);
        td_code.setValue(num* commodity.getValue());
        td_code.setState(0);
        td_code.setName(commodity.getName());
        Date time = new Date();
        td_code.setTime(time);
        if(td_dao.insert_code(td_code)){
            List<TD_code> td_codes = td_dao.select_codes(identification, commodity.getCommodity_id());
            Integer id = td_codes.get(td_codes.size()-1).getCode();
            td_code.setCode(id);
            return td_code;
        }
        else
            return null;
    }

    /*  插入商品记录  */
    public boolean insert_record(String commodity_id, Double value, String id, Integer order_num){
        Mall_consumption_record record = new Mall_consumption_record();
        record.setCommodity_id(Integer.valueOf(commodity_id));
        record.setValue(value);
        record.setIdentification(id);
        record.setOrder_num(order_num);
        return mall_consumption_recordDao.insert_record(record);
    }
}
