package com.example.demo.Utils;

import com.example.demo.entity.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalculationUtil {

    /*
    *
    *   计算工具类
    *   计算相关系列操作：
    *       计算各种资料比重
    *       计算可用余额
    *       计算本月最低还款
    *       计算分期
    *       …………
    *
    * */

    // 计算比例
    public static List<Double> proportion(List<Enterprise_information> list){
        List<Double> doublelist = new ArrayList<>();
        double loan_sum = 0;
        double total_sum = 0;
        for (int i = 0 ; i < list.size(); i++){
            loan_sum += list.get(i).getLoan_amount();
            total_sum += list.get(i).getTotal_amount();
            doublelist.add(list.get(i).getLoan_amount() / list.get(i).getTotal_amount());
        }
        doublelist.add(loan_sum / total_sum);
        return doublelist;
    }

    public static double get_proportion(List<Person_information> list){
        double loan_sum = 0;
        double total_sum = 0;
        for(int i=0; i < list.size(); i++){
            loan_sum += list.get(i).getLoan_amount();
            total_sum += list.get(i).getTotal_amount();
        }
        return loan_sum / total_sum;
    }

    // 计算剩余可用余额
    public static List<Double> remain(List<Enterprise_information> list){
        List<Double> doublelist = new ArrayList<>();
        double loan_sum = 0;
        double total_sum = 0;
        for (int i = 0 ; i < list.size(); i++){
            loan_sum += list.get(i).getLoan_amount();
            total_sum += list.get(i).getTotal_amount();
            doublelist.add( list.get(i).getTotal_amount() - list.get(i).getLoan_amount());
        }
        doublelist.add( total_sum - loan_sum);
        return doublelist;
    }

    public static List<Double> get_remain(List<Person_information> list){
        List<Double> doublelist = new ArrayList<>();
        double loan_sum = 0;
        double total_sum = 0;
        for(int i=0; i < list.size(); i++){
            loan_sum += list.get(i).getLoan_amount();
            total_sum += list.get(i).getTotal_amount();
            doublelist.add(list.get(i).getTotal_amount() - list.get(i).getLoan_amount());
        }
        doublelist.add(total_sum - loan_sum);
        return doublelist;
    }

    // 计算本月还款以及本月贷款量
    public static List<Double> month_operation(List<Repay_enterprise> list){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String now_time = sdf.format(d);
        double positive = 0;
        double negative = 0;
        for(int i = 0 ; i < list.size() ; i++){
            String time = sdf.format(list.get(i).getTime());
            if (time.contains(now_time)){
                if (list.get(i).getMoney_amount() > 0){
                    positive += list.get(i).getMoney_amount();
                }else{
                    negative += Math.abs(list.get(i).getMoney_amount());
                }
            }
        }
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(positive);
        doubleList.add(negative);
        return doubleList;
    }

    public static List<Double> get_month_operation(List<Repay_person> list){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String now_time = sdf.format(d);
        double positive = 0;
        double negative = 0;
        for(int i = 0 ; i < list.size() ; i++){
            String time = sdf.format(list.get(i).getTime());
            if (time.contains(now_time)){
                if (list.get(i).getMoney_amount() > 0){
                    positive += list.get(i).getMoney_amount();
                }else{
                    negative += Math.abs(list.get(i).getMoney_amount());
                }
            }
        }
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(positive);
        doubleList.add(negative);
        return doubleList;
    }

    // 计算最小还款额度
    public static double mini_repay(List<Person_information> list_information, List<Service_loan> list_service, List<Separate> list_separate){
        // 计算现金贷的额度
        double x1 = list_information.get(1).getLoan_amount() / 6;
        x1 = x1 * (0.85*0.01 + 1);
        // 计算商品贷的额度
        double x2 = 0;
        for(int i = 0; i < list_separate.size(); i++){
            x2 += list_separate.get(i).getLast_num() / list_separate.get(i).getLast_stage();
        }
        x2 = x2 * (0.085*0.01 + 1);
        return x1 + x2;
    }

    // 计算本息
    public static double get_principal(List<Person_information> list_informatio, List<Service_loan> list_service){
        double sum = 0;
        for(int i = 0 ; i < list_informatio.size() ; i++){
            double temp = list_informatio.get(i).getLoan_amount();
            temp = temp * (1 + list_service.get(list_informatio.get(i).getServicenum()+1).getService_rate());
            sum += temp;
        }
        return sum;
    }

    // 分期付款各个方案所需要的费用
    public static List<String> every_stage(double value){
        List<String > doubleList = new ArrayList<>();
        DecimalFormat df   = new DecimalFormat("######0.00");
        double rates[] = {1.0085, 0.339, 0.171625, 0.11584, 0.088, 0.0461};
        doubleList.add(df.format(value));
        for (int i = 0 ; i < 6; i++ ){
            doubleList.add(df.format(value*rates[i]));
        }
        return doubleList;
    }
}
