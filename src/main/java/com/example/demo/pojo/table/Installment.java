package com.example.demo.pojo.table;

import com.example.demo.pojo.util.RecordCipherInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: Installment
 * @Description: 分期付款信息表
 * @Author: 刘敬
 * @Date：2019/7/31 14:04
 */
@Entity
@Data
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private MedicalRecord medicalRecord;

    @OneToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private User bank;

    @Column
    private String cost;

    @Column
    private Date createTime;

    @Column
    private long cycle;

    @Column
    private long paidCycle;

    @Column
    private String state;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private String age;

    // 主诉
    @Column
    private String chiefComplaint;

    // 疾病类型
    @Column
    private String diseaseType;

    public Installment() {
    }

    public Installment(RecordCipherInfo recordCipherInfo, long cycle) {
        this.cost = recordCipherInfo.getCost();
        this.createTime = new Date();
        this.name = recordCipherInfo.getName();
        this.gender = recordCipherInfo.getGender();
        this.age = recordCipherInfo.getAge();
        this.chiefComplaint = recordCipherInfo.getChiefComplaint();
        this.diseaseType = recordCipherInfo.getDiseaseType();
        this.cycle = cycle;
        this.paidCycle = 0;
    }

}
