package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: InsurancePolicy
 * @Description: 保险单
 * @Author: 刘敬
 * @Date: 2019/6/14 17:34
 **/
@Entity
@Data
public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    @OneToOne
    @JoinColumn(name = "insurance_project_id", referencedColumnName = "id")
    private InsuranceProject insuranceProject;

    @OneToOne
    @JoinColumn(name = "medical_record_id", referencedColumnName = "id")
    private MedicalRecord medicalRecord;

    //理赔疾病
    @Column
    private String claimDisease;

    //疾病等级
    @Column
    private String diseaseGrade;

    //理赔金额
    @Column
    private String claimAmount;

    //依据
    @Lob
    private String basis;

    //创建时间
    @Column
    private Date createTime;

    //状态
    @Column
    private String state;
}
