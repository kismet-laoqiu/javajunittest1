package com.example.demo.entity;

import jnr.ffi.annotations.In;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String identification;
    private Integer label;
    private String content;
    private Date time;
    private String times;
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        setTimes(time);
    }

    public void setTimes(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.times = sdf.format(time);
    }

    public String getTimes(){
        return times;
    }
}
