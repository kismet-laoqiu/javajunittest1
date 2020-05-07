package com.example.demo.dao;

import com.example.demo.pojo.table.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName: OauthDao
 * @Description: Oauth表的dao层
 * @Author: 刘敬
 * @Date: 2019/6/5 19:42
 **/
public interface OauthDao extends JpaRepository<Oauth, Long> {
    Oauth findByRoleAndOauthTypeAndOauthId(String role,String type,String id);
}
