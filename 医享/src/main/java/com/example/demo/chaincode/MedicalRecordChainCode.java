package com.example.demo.chaincode;

import com.example.demo.util.filoop.Filoop;

import java.util.Map;

/**
 * @ClassName: MedicalRecordChainCode
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/8/6 12:22
 **/
public class MedicalRecordChainCode {
    public static boolean addMedicalRecord(String key, String value) {
        return Filoop.addMedicalRecord(key, value);
    }

    public static boolean updateMedicalRecord(String key, String value) {
        return Filoop.updateMedicalRecord(key, value);
    }

    public static Map<String, String> queryMedicalRecord(String key) {
        return Filoop.queryMedicalRecord(key);
    }

}
