package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: OrderRecord
 * @Description: 订单记录
 * @Author: 刘敬
 * @Date: 2019/8/4 21:57
 **/
@Entity
@Data
public class OrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String description;

    // 充值，支付
    @Column
    private String type;

    @Column
    private long money;

    @Column
    private Date createTime;

    @Column
    private String status;

}
