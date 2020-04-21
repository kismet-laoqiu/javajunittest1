package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName:  Mortgage
 * @Description: 抵押贷款信息表
 * @Author: 刘敬
 * @Date：2019/7/30 8:43
 */
@Entity
@Data
public class Mortgage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //申请贷款用户
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //处理贷款银行
    @OneToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private User bank;

    @Column
    private Date createTime;

    @Column
    private String name;

    @Column
    private String age;

    @Column
    private String gender;

    @Column
    private String card;

    //民族
    @Column
    private String nation;

    //职业
    @Column
    private String occupation;

    @Column
    private String diseaseHistory;


    @Column
    private String phone;

    //贷款金额
    @Column
    private String amount;

    //家庭收入
    @Column
    private String income;

    //抵押资产
    @Column
    private String asset;

    //上传凭证
    @Column
    private String certificate;

    //贷款状态 分为贷款申请中、贷款成功
    @Column
    private String state;

}
