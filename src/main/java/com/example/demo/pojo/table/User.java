package com.example.demo.pojo.table;

import com.example.demo.pojo.table.userInfo.*;
import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Field;

/**
 * @ClassName: User
 * @Description: 用户
 * @Author: 刘敬
 * @Date: 2019/6/5 16:24
 **/
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String avatarUrl;

    @Column
    private String phone;

    @Column
    private String gender;

    @Column
    private String age;

    @Column
    private String province;

    @Column
    private String city;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String signature;

    @Column
    private String resume;

    @Column
    private String role;


    @Column
    private String card;

    @Column
    private long accountBalance;

    @Lob
    @Column
    private String publicKey;

    @Lob
    @Column
    private String privateKey;

    @OneToOne
    @JoinColumn
    private BankInfo bankInfo;

    @OneToOne
    @JoinColumn
    private DoctorInfo doctorInfo;

    @OneToOne
    @JoinColumn
    private InsurerInfo InsurerInfo;

    @OneToOne
    @JoinColumn
    private ResearchInfo researchInfo;

    @OneToOne
    @JoinColumn
    private PatientInfo patientInfo;

    public void updateInfo(User origin) {
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
