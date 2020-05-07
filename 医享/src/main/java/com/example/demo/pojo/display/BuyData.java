package com.example.demo.pojo.display;

import com.example.demo.config.Config;
import lombok.Data;

@Data
public class BuyData {

    private String type;

    private long total;

    private long money;

    public BuyData() {
    }

    public BuyData(String type, long total) {
        this.type = type;
        this.total = total;
        this.money = total* Config.buyDataPrice;
    }

}
