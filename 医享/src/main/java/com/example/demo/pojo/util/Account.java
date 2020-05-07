package com.example.demo.pojo.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: Account
 * @Description: 链上账户
 * @Author: 刘敬
 * @Date: 2019/8/6 10:42
 **/
@Data
@AllArgsConstructor
public class Account {
    private String id;
    private String type;
    private String name;
    private long rest;
}
