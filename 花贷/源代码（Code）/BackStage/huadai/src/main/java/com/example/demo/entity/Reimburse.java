package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reimburse {
    private String idenfication;
    private String bussinesslicence;
    private Integer order_num;
    private double value;
    private Integer state;
    private String description;
    private Date time;
    private String times;
    private String service_bussinesslicence;
    private Integer num;
    private String commodity_id;
    // 数据库中没有的整合内容
    private String ServiceName;
    private String ServiceProvider;
    private String label;
    private String applicant;

    @Override
    public String toString() {
        return "Reimburse{" +
                "idenfication='" + idenfication + '\'' +
                ", bussinesslicence='" + bussinesslicence + '\'' +
                ", order_num=" + order_num +
                ", value=" + value +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", times='" + times + '\'' +
                ", service_bussinesslicence='" + service_bussinesslicence + '\'' +
                ", num=" + num +
                ", ServiceName='" + ServiceName + '\'' +
                ", ServiceProvider='" + ServiceProvider + '\'' +
                ", label='" + label + '\'' +
                ", applicant='" + applicant + '\'' +
                '}';
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getIdenfication() {
        return idenfication;
    }

    public void setIdenfication(String idenfication) {
        this.idenfication = idenfication;
    }

    public String getBussinesslicence() {
        return bussinesslicence;
    }

    public void setBussinesslicence(String bussinesslicence) {
        this.bussinesslicence = bussinesslicence;
    }

    public Integer getOrder_num() {
        return order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        setTimes(time);
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getServiceProvider() {
        return ServiceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        ServiceProvider = serviceProvider;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getService_bussinesslicence() {
        return service_bussinesslicence;
    }

    public void setService_bussinesslicence(String service_bussinesslicence) {
        this.service_bussinesslicence = service_bussinesslicence;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.times = sdf.format(time);
    }
}
