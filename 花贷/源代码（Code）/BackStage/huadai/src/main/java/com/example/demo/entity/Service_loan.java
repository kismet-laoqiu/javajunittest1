package com.example.demo.entity;

public class Service_loan {
    private Integer servicenum;
    private String  servicename;
    private float service_rate;
    private Integer service_type;

    @Override
    public String toString() {
        return "Service_loan{" +
                "servicenum=" + servicenum +
                ", servicename='" + servicename + '\'' +
                ", service_rate=" + service_rate +
                ", service_type=" + service_type +
                '}';
    }

    public Integer getServicenum() {
        return servicenum;
    }

    public void setServicenum(Integer servicenum) {
        this.servicenum = servicenum;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public float getService_rate() {
        return service_rate;
    }

    public void setService_rate(float service_rate) {
        this.service_rate = service_rate;
    }

    public Integer getService_type() {
        return service_type;
    }

    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }
}
