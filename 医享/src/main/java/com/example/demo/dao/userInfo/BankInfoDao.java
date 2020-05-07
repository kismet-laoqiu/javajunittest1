package com.example.demo.dao.userInfo;

import com.example.demo.pojo.table.userInfo.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: BankInfoDao
 * @Description: 银行信息访问接口
 * @Author: 许建波
 * @Date：2019/7/24 16:41
 */

public interface BankInfoDao extends JpaRepository<BankInfo, Long> {

}
