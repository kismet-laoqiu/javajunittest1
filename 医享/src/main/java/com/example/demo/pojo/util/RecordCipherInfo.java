package com.example.demo.pojo.util;

import com.example.demo.pojo.form.MedicalRecordFormInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @ClassName: MedicalRecordFormInfo
 * @Description: 病历详细信息，用于加密
 * @Author: 刘敬
 * @Date: 2019/6/10 19:34
 **/
@Data
public class RecordCipherInfo {

    private Long id;

    private Long patient_id;

    private Long doctor_id;

    private String hospital;

    private String department;

    private Date createTime;

    // 花费
    private String cost;

    // 签名
    private String signature;

    // 姓名
    private String name;

    // 性别
    private String gender;

    // 年龄
    private String age;

    // 民族
    private String nation;

    // 血型
    private String bloodType;

    // 主诉
    private String chiefComplaint;

    // 现病史
    private String presentIllnessHistory;

    // 既往病史
    private String pastIllnessHistory;

    // 药物过敏史
    private String drugAllergyHistory;

    // 个人史
    private String personalHistory;

    // 家族史
    private String familyHistory;

    // 体格检查
    private String physicalExamination;

    // 门诊体检
    private String outpatientPhysicalExamination;

    // 辅助检查
    private String supplementaryExamination;

    // 门诊诊断
    private String outpatientDiagnosis;

    // 治疗意见
    private String treatmentAdvice;

    // 门诊处理
    private String outpatientHandle;

    // 门诊医嘱
    private String outpatientDoctorAdvice;

    // 疾病类型
    private String diseaseType;

    public RecordCipherInfo() {
    }

    public RecordCipherInfo(MedicalRecordFormInfo medicalRecordFormInfo) {
        BeanUtils.copyProperties(medicalRecordFormInfo, this);
    }


}
