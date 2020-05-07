package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: InsuranceProject
 * @Description: 保险项目
 * @Author: 刘敬
 * @Date: 2019/7/27 16:34
 **/
@Entity
@Data
public class InsuranceProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //发布公司
    @OneToOne
    @JoinColumn(name = "insurer_id", referencedColumnName = "id")
    private User insurer;

    //发布时间
    @Column
    private Date createTime;

    //保险期限
    @Column
    private String insurancePeriod;

    //销售范围
    @Column
    private String salesScope;

    //险种名称
    @Column
    private String insuranceName;

    //投保年龄
    @Column
    private String issueAge;

    //理赔疾病
    @Column
    private String claimDisease;

    //一般医疗保险金
    @Column
    private String generalMedicalInsurance;

    //重大疾病医疗保险金
    @Column
    private String criticalIllnessMedicalInsurance;

    //年免赔额
    @Column
    private String annualDeductible;

    //保障区域
    @Column
    private String guaranteeArea;

    //医院范围
    @Column
    private String hospitalScope;

    //赔付比例
    @Column
    private String compensationRatio;

    //交费年限
    @Column
    private String paymentYears;

    //年交保费
    @Column
    private String annualPremium;

    //就医绿色通道
    @Column
    private String greenChannelMedicalTreatment;

    //保险责任
    @Lob
    private String insuranceLiability;

    //责任免除
    @Lob
    private String exclusions;

}
