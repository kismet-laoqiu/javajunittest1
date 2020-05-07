package com.example.demo.pojo.table;

import com.example.demo.pojo.util.RecordCipherInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: MedicalRecordFormInfo
 * @Description: 脱敏后病历信息
 * @Author: 刘敬
 * @Date: 2019/6/10 19:34
 **/
@Entity
@Data
public class DesensitizationMedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private User doctor;

    @Column
    private Date createTime;

    // 姓名
    @Column
    private String name;

    // 性别
    @Column
    private String gender;

    // 年龄
    @Column
    private String age;

    // 民族
    @Column
    private String nation;

    // 血型
    @Column
    private String bloodType;

    // 主诉
    @Column
    private String chiefComplaint;

    // 现病史
    @Column
    private String presentIllnessHistory;

    // 既往病史
    @Column
    private String pastIllnessHistory;

    // 药物过敏史
    @Column
    private String drugAllergyHistory;

    // 个人史
    @Column
    private String personalHistory;

    // 家族史
    @Column
    private String familyHistory;

    // 体格检查
    @Column
    private String physicalExamination;

    // 门诊体检
    @Column
    private String outpatientPhysicalExamination;

    // 辅助检查
    @Column
    private String supplementaryExamination;

    // 门诊诊断
    @Column
    private String outpatientDiagnosis;

    // 治疗意见
    @Column
    private String treatmentAdvice;

    // 门诊处理
    @Column
    private String outpatientHandle;

    // 门诊医嘱
    @Column
    private String outpatientDoctorAdvice;

    // 疾病类型
    private String diseaseType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "desensitizationMedicalRecords")
    private Set<PurchasedData> purchasedDatas = new HashSet<>();

    public DesensitizationMedicalRecord() {
    }

    public DesensitizationMedicalRecord(User patient, User doctor, RecordCipherInfo recordCipherInfo) {
        this.patient = patient;
        this.doctor = doctor;
        BeanUtils.copyProperties(recordCipherInfo, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DesensitizationMedicalRecord that = (DesensitizationMedicalRecord) o;
        return id.equals(that.id);
    }

}
