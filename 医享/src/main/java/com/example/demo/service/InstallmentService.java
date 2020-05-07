package com.example.demo.service;

import com.example.demo.pojo.table.Installment;

import java.util.List;

/**
 * @ClassName: InstallmentService
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/8/6 22:58
 **/
public interface InstallmentService {
    Installment findInstallmentById(long installmentId);

    List<Installment> listExaminedInstallmentByBank(long bankId);

    List<Installment> listUnExaminedInstallmentByBank(long bankId);

    int agreeInstallment(long installmentId);

    int refuseInstallment(long installmentId);

    int installmentPaySubmit(long recordId, long cycle);

    int directPaySubmit(long recordId);
}
