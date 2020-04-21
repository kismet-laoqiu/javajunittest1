package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: RecordApplyDao
 * @Description: 记录申请记录
 * @Author: 刘敬
 * @Date: 2019/6/14 8:53
 **/
@Entity
@Data
public class RecordApply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private User doctor;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private MedicalRecord medicalRecord;

    @Column
    private Date applyTime;

    @Column
    private String state;
}
