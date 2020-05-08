package com.example.demo.entity;

public class Commodity {
    private String name;
    private String bussinesslicence;
    private Integer label;
    private String commodity_id;
    private double value;
    private String image;
    private String description;

    @Override
    public String toString() {
        return "Commodity{" +
                "name='" + name + '\'' +
                ", bussinesslicence='" + bussinesslicence + '\'' +
                ", label=" + label +
                ", commodity_id='" + commodity_id + '\'' +
                ", value=" + value +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBussinesslicence() {
        return bussinesslicence;
    }

    public void setBussinesslicence(String bussinesslicence) {
        this.bussinesslicence = bussinesslicence;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
