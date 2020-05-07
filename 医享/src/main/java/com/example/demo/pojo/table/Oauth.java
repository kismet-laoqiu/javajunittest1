package com.example.demo.pojo.table;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: OauthDao
 * @Description: 用户认证信息
 * @Author: 刘敬
 * @Date: 2019/6/5 16:22
 **/
@Entity
@Data
public class Oauth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String role;

    @Column
    private String oauthType;

    @Column
    private String oauthId;

    @Column
    private String credential;

    @Column
    private Date createTime;

    @Column
    private Date lastLoginTime;
}
