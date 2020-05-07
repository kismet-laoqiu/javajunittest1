package com.example.demo.service.Impl;

import com.example.demo.chaincode.AccountChainCode;
import com.example.demo.config.Config;
import com.example.demo.dao.DesensitizationMedicalRecordDao;
import com.example.demo.dao.OrderRecordDao;
import com.example.demo.dao.PurchasedDataDao;
import com.example.demo.dao.UserDao;
import com.example.demo.pojo.display.BuyData;
import com.example.demo.pojo.table.DesensitizationMedicalRecord;
import com.example.demo.pojo.table.OrderRecord;
import com.example.demo.pojo.table.PurchasedData;
import com.example.demo.pojo.table.User;
import com.example.demo.service.ResearchService;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ResearchServiceImpl implements ResearchService {

    @Autowired
    private DesensitizationMedicalRecordDao desensitizationMedicalRecordDao;

    //已购数据Dao
    @Autowired
    private PurchasedDataDao purchasedDataDao;

    //订单Dao
    @Autowired
    private OrderRecordDao orderRecordDao;

    @Autowired
    UserDao userDao;

    //查询订单记录
    @Override
    public List<OrderRecord> listOrderRecords(long researchId) {
        User user = userDao.findById(researchId).get();
        return orderRecordDao.findByUser(user);
    }

    //添加充值记录
    @Override
    public int addRechargeRecord(long money, long researchId) {
        User research = userDao.findById(researchId).get();
        AccountChainCode.transfer("-1", research.getId().toString(), Config.rechargeRate * money + "");
        research.setAccountBalance(research.getAccountBalance() + Config.rechargeRate * money);
        userDao.save(research);
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setDescription("医元充值：" + money + "元");
        orderRecord.setMoney(money);
        orderRecord.setType("医元充值");
        orderRecord.setUser(research);
        orderRecord.setStatus("已处理");
        orderRecord.setCreateTime(new Date());
        orderRecordDao.save(orderRecord);
        return 200;
    }

    //列出可购买的病例数据
    @Override
    public List<BuyData> listBuyData() {
        return desensitizationMedicalRecordDao.getBuyDatas();
    }

    //已购数据列表
    @Override
    public List<PurchasedData> listPurchasedData(long researchId) {
        return purchasedDataDao.findByResearch(userDao.findById(researchId).get());
    }

    //购买操作
    @Override
    public int buyData(String type, long researchId) {
        Set<DesensitizationMedicalRecord> desensitizationMedicalRecords = desensitizationMedicalRecordDao.findByDiseaseType(type);
        long count = desensitizationMedicalRecords.size();
        User research = userDao.findById(researchId).get();
        if (research.getAccountBalance() <= count * Config.buyDataPrice) {
            return 201;
        } else {
            PurchasedData purchasedData = new PurchasedData();
            purchasedData.setCost(count * Config.buyDataPrice);
            purchasedData.setCreateTime(new Date());
            purchasedData.setResearch(userDao.findById(researchId).get());
            purchasedData.setTotal(count);
            purchasedData.setTypeKey("疾病类型");
            purchasedData.setTypeValue(type);
            purchasedData.setDesensitizationMedicalRecords(desensitizationMedicalRecords);
            AccountChainCode.transfer(research.getId().toString(), "0", count * Config.buyDataPrice + "");
            research.setAccountBalance(research.getAccountBalance() - count * Config.buyDataPrice);
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setDescription("购买医疗数据花费医元：" + count * Config.buyDataPrice + "元");
            orderRecord.setMoney(count * Config.buyDataPrice);
            orderRecord.setType("医元花费");
            orderRecord.setUser(userDao.findById(researchId).get());
            orderRecord.setStatus("已处理");
            orderRecord.setCreateTime(new Date());
            userDao.save(research);
            orderRecordDao.save(orderRecord);
            purchasedDataDao.save(purchasedData);
            return 200;
        }
    }

    //下载操作
    @Override
    public void createDataFile(String filePath, long purchasedDataId) throws Exception {
        PurchasedData purchasedData = purchasedDataDao.findById(purchasedDataId).get();
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet(purchasedData.getTypeKey() + "-" + purchasedData.getTypeValue(), 0);
        //创建要显示的内容，创建一个单元格，参数：列坐标，行坐标，内容
        int i = 0;
        sheet.addCell(new Label(i++, 0, "性别"));
        sheet.addCell(new Label(i++, 0, "年龄"));
        sheet.addCell(new Label(i++, 0, "民族"));
        sheet.addCell(new Label(i++, 0, "血型"));
        sheet.addCell(new Label(i++, 0, "主诉"));
        sheet.addCell(new Label(i++, 0, "现病史"));
        sheet.addCell(new Label(i++, 0, "疾病类型"));
        sheet.addCell(new Label(i++, 0, "体格检查"));
        sheet.addCell(new Label(i++, 0, "门诊诊断"));
        sheet.addCell(new Label(i, 0, "创建时间"));
        Set<DesensitizationMedicalRecord> desensitizationMedicalRecords = purchasedData.getDesensitizationMedicalRecords();
        i = 0;
        for (DesensitizationMedicalRecord desensitizationMedicalRecord : desensitizationMedicalRecords) {
            int j = 0;
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getGender()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getAge()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getNation()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getBloodType()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getChiefComplaint()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getPresentIllnessHistory()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getDiseaseType()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getPhysicalExamination()));
            sheet.addCell(new Label(j++, i + 1, desensitizationMedicalRecord.getOutpatientDiagnosis()));
            sheet.addCell(new Label(j, i + 1, desensitizationMedicalRecord.getCreateTime().toString()));
            i++;
        }
        workbook.write();
        workbook.close();
    }
}
