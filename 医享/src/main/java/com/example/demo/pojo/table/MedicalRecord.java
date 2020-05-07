package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: MedicalRecord
 * @Description: 电子病历
 * @Author: 刘敬
 * @Date: 2019/6/10 15:27
 **/
@Entity
@Data
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private User doctor;

    @Column
    private Date createTime;

    @Lob
    private String cipherText;

    @Lob
    @Column
    private String secretKey;

    @Column
    private String state;

    //脱敏共享状态
    @Column
    private String shareState;

    //支付状态
    @Column
    private String payState;

    //分期付款状态
    @Column
    private String installmentState;

    //属性加密策略
    @Column
    private String policy;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return this.id.equals(((MedicalRecord) obj).getId());
    }
}
