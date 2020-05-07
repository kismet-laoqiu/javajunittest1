package com.example.demo.pojo.table.userInfo;

import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Field;

/**
 * @ClassName: PatientInfo
 * @Description: 患者信息表
 * @Author: 刘敬
 * @Date：2019/7/25 19:34
 */
@Entity
@Data
public class PatientInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //民族
    @Column
    private String nation;

    //国籍
    @Column
    private String nationality;

    //职业
    @Column
    private String occupation;

    //工作地点
    @Column
    private String workPlace;

    @Column
    private String blood;

    @Column
    private String diseaseHistory;

    public void updateInfo(PatientInfo origin) {
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
