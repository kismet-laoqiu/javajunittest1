package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: PurchasedData
 * @Description: 已购买数据
 * @Author: 刘敬
 * @Date: 2019/8/4 21:25
 **/
@Entity
@Data
public class PurchasedData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "research_id", referencedColumnName = "id")
    private User research;

    @Column
    private Date createTime;

    //分类Key，年龄，疾病类型
    @Column
    private String typeKey;

    //分类value，18岁，心脏病
    @Column
    private String typeValue;

    //数量
    @Column
    private long total;

    //花费
    @Column
    private long cost;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private Set<DesensitizationMedicalRecord> desensitizationMedicalRecords= new HashSet<>();

}
