package com.example.demo.service;

import com.example.demo.pojo.table.RecordApply;
import com.example.demo.pojo.util.RecordCipherInfo;

import java.util.List;

/**
 * @InterfaceName: RecordApplyService
 * @Description: RecordApply的service层
 * @Author: 刘敬
 * @Date: 2019/6/14 9:25
 **/
public interface RecordApplyService {
    List<RecordApply> listApplyByDoctor(long doctorId);
    List<RecordApply> listUnApplyByPatient(Long patientId);
    List<RecordApply> listApplyByPatient(Long patientId);
    void addApply(long doctorId,long recordId);
    void reSendApply(long applyId);
    void confirmApply(long recordId) throws Exception;
    void revokeApply(long applyId) throws Exception;
    RecordCipherInfo lookRecord(long applyId) throws Exception;
}
