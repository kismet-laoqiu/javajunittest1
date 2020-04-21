package com.example.demo.service;

import com.example.demo.pojo.display.BuyData;
import com.example.demo.pojo.table.OrderRecord;
import com.example.demo.pojo.table.PurchasedData;

import java.util.List;

public interface ResearchService {
    List<BuyData> listBuyData();

    List<PurchasedData> listPurchasedData(long researchId);

    List<OrderRecord> listOrderRecords(long researchId);

    int addRechargeRecord(long money, long researchId);

    int buyData(String type,long researchId);

    void createDataFile(String filePath,long purchasedDataId)throws Exception;
}
