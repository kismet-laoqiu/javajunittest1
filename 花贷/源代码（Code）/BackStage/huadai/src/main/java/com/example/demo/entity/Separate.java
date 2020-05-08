package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Separate {
    private Integer separate_num;
    private String name;
    private Integer total_stage;
    private Integer last_stage;
    private double total_num;
    private double last_num;
    private String identity;
    private Date time;
    private String times;

    public Integer getSeparate_num() {
        return separate_num;
    }

    public void setSeparate_num(Integer separate_num) {
        this.separate_num = separate_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal_stage() {
        return total_stage;
    }

    public void setTotal_stage(Integer total_stage) {
        this.total_stage = total_stage;
    }

    public Integer getLast_stage() {
        return last_stage;
    }

    public void setLast_stage(Integer last_stage) {
        this.last_stage = last_stage;
    }

    public double getTotal_num() {
        return total_num;
    }

    public void setTotal_num(double total_num) {
        this.total_num = total_num;
    }

    public double getLast_num() {
        return last_num;
    }

    public void setLast_num(double last_num) {
        this.last_num = last_num;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        setTimes(time);
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.times = sdf.format(time);
    }
}
