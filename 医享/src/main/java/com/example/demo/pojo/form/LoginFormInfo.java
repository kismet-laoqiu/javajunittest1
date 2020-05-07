package com.example.demo.pojo.form;

import lombok.Data;

/**
 * @ClassName: LoginFormInfo
 * @Description: 登录表单实体类
 * @Author: 刘敬
 * @Date: 2019/6/9 11:10
 **/
@Data
public class LoginFormInfo {
    private String type;
    private String email;
    private String password;
}
