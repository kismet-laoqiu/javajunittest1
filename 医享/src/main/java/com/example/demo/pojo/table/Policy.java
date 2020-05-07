package com.example.demo.pojo.table;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName: Policy
 * @Description: 授权策略
 * @Author: 刘敬
 * @Date: 2019/7/16 21:25
 **/
@Entity
@Data
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private MedicalRecord medicalRecord;

    @Column
    private String type;

    @Column
    private String value;

    public static String makeNewPolicy(List<Policy> policies) {
        StringBuilder p = new StringBuilder();
        for (Policy policy :
                policies) {
            p.append(policy.getType()).append(":").append(policy.getValue()).append(" ");
        }
        if (policies.size() > 1) {
            p.append("1of").append(policies.size());
        }
        return p.toString();
    }
}
