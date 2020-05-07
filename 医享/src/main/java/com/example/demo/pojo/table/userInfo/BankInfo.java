package com.example.demo.pojo.table.userInfo;

import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Field;

/**
 * @ClassName: BankInfo
 * @Description: 银行信息表
 * @Author: 许建波
 * @Date：2019/7/24 16:19
 */
@Entity
@Data
public class BankInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //组织机构代码
    @Column
    private String organization;

    //登记机关
    @Column
    private String registrar;

    //成立时间
    @Column
    private String createTime;

    //业务范围
    @Column
    private String business;

    //机构详细介绍
    @Lob
    private String details;

    public void updateInfo(BankInfo origin) {
        Field[] fields = origin.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(origin);
                if (null != value && !value.equals("") && !value.equals("null") && !field.getName().equals("id")) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }
}
