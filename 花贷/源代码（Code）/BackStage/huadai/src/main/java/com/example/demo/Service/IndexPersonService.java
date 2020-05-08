package com.example.demo.Service;

import com.example.demo.Dao.EnterpriseIndexDao;
import com.example.demo.Dao.IndexPersonDao;
import com.example.demo.Dao.RepayDao;
import com.example.demo.Dao.SeparateDao;
import com.example.demo.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IndexPersonService {
    /*
    *   准备首页、付还款信息
    *
    * */

    @Autowired
    private IndexPersonDao indexPersonDao;
    @Autowired
    private EnterpriseIndexDao enterpriseIndexDao;
    @Autowired
    private RepayDao repayDao;
    @Autowired
    private SeparateDao separateDao;

    public List<Person_information> get_information(String identification){
        return indexPersonDao.select_information(identification);
    }


    public List<Person_record> get_record(String identification){
        return indexPersonDao.select_record(identification);
    }


    public List<Repay_person> get_repay(String identification){
        return indexPersonDao.select_repay(identification);
    }

    /*
    *   查询服务套餐
    * */
    public List<Service_loan> get_service(List<Person_information> list_information){

        List<Service_loan> list_service = new ArrayList<>();

        for(int i = 0; i < list_information.size(); i++){
            Integer servicenum = list_information.get(i).getServicenum();
            list_service.add(enterpriseIndexDao.select_service(servicenum));
        }
        return list_service;
    }


    /*
    *   还款操作， 按照 分期还款策略进行还款， 详情请参照 商业计划书第二章节
    * */
    public boolean repay(HttpServletRequest request, Person person, List<Person_information> list_information, List<Separate> list_separate,double mini_repay){
        double rate = 1+0.0085;
        // 还清最低额度
        if(back(list_separate, list_information)){
            double money_amount = Double.parseDouble(request.getParameter("value"));
            money_amount -= mini_repay;
            if(money_amount > list_information.get(1).getLoan_amount()* 5/6 *rate){
                indexPersonDao.update_loan_amount(list_information.get(1).getLoan_amount()/6 *5, list_information.get(0).getIdentification(), 3);
                money_amount -= list_information.get(1).getLoan_amount()* 5/6 *rate;
                double money_amount_copy = money_amount;
                for(int i =0; i < list_separate.size(); i++){
                    if (money_amount == 0)
                        break;
                    double temp = list_separate.get(i).getLast_num()*(list_separate.get(i).getLast_stage()-1)/list_separate.get(i).getLast_stage()*rate;
                    if(money_amount >= temp){
                        money_amount -= temp;
                        separateDao.update_separation(0, list_separate.get(i).getSeparate_num());
                    } else{
                        money_amount = 0;
                        separateDao.update_separate(money_amount, list_separate.get(i).getSeparate_num());
                    }
                }
                indexPersonDao.update_loan_amount(money_amount_copy/rate, list_information.get(0).getIdentification(), 4);
                return true;
            }else {
                indexPersonDao.update_loan_amount(money_amount/rate, list_information.get(0).getIdentification(), 3);
                return true;
            }
        }

        return false;
    }

    /*
    *   还款单元函数
    * */
    public boolean back( List<Separate> list_separate, List<Person_information> list_information){
        double sum = 0;
        for (int i = 0; i < list_separate.size(); i++){
            Integer separate_num = list_separate.get(i).getSeparate_num();
            double last_num = list_separate.get(i).getLast_num() / list_separate.get(i).getLast_stage();
            sum += last_num;
            separateDao.update_separate(last_num, separate_num);
        }
        indexPersonDao.update_loan_amount(sum, list_information.get(0).getIdentification(), 4);
        indexPersonDao.update_loan_amount(list_information.get(1).getLoan_amount()/6, list_information.get(0).getIdentification(),3);
        return true;
    }

    public boolean test(String id){
        return true;
    }
}
