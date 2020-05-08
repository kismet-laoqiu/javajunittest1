package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TD_code {
    private Integer codenum;
    private String identification;
    private String commodity_id;
    private String name;
    private Integer num;
    private double value;
    private Integer state;
    private Date time;
    private String times;

    @Override
    public String toString() {
        return "TD_code{" +
                "codenum=" + codenum +
                ", identification='" + identification + '\'' +
                ", commodity_id='" + commodity_id + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", value=" + value +
                ", state=" + state +
                ", time=" + time +
                ", times='" + times + '\'' +
                '}';
    }

    public Integer getCode() {
        return codenum;
    }

    public void setCode(Integer code) {
        this.codenum = code;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
