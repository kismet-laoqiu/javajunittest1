package com.example.demo.dao.userInfo;

import com.example.demo.pojo.table.userInfo.InsurerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: InsurerInfoDao
 * @Description: 保险公司信息访问接口
 * @Author: 许建波
 * @Date：2019/7/25 19:38
 */
public interface InsurerInfoDao extends JpaRepository<InsurerInfo, Long> {

}
