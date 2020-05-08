package com.example.demo.client;

import com.example.demo.contract.Asset;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

public class AssetClient {

    /**
     *
     *   区块链
     *        授信额度上链
     *
     *
     *    使用顺序：
     *      initialize                      连接区块链
     *      deployAssetAndRecordAddr        部署合约
     *
     *    函数：
     *          registerAssetAmount         上链
     *          queryReiAssetAmount         查找
     *          commodity_pay               付钱
     *          cash_pay
     *          cash_repay                  还钱
     *          commodity_repay
     *          transfer                    转账
     *
     * */

    private Web3j web3j;

    private Credentials credentials;

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("asset_address", address);
        final Resource contractResource = new ClassPathResource("Asset.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "Asset contract address");
    }

    public String loadAssetAddr() throws Exception {
        // load Asset contact address from com_contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("Asset.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("asset_address");
        System.out.println("contractAddress" + contractAddress);
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load Asset Asset contract address failed, please deploy it first. ");
        }
        System.out.printf(" load Asset Asset address from .properties, address is %s", contractAddress);
        return contractAddress;
    }

    public void initialize() throws Exception {

        // init the Service
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        Web3j web3j = Web3j.build(channelEthereumService, 1);

        // init Credentials
        Credentials credentials = Credentials.create(Keys.createEcKeyPair());

        setCredentials(credentials);
        setWeb3j(web3j);

        System.out.println(" web3j is " + web3j + " ,credentials is " + credentials);
    }

    private static BigInteger gasPrice = new BigInteger("30000000");
    private static BigInteger gasLimit = new BigInteger("30000000");

    public void deployAssetAndRecordAddr() {

        try {
            Asset asset = Asset.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
            System.out.println(" deploy Asset success, contract address is " + asset.getContractAddress());

            recordAssetAddr(asset.getContractAddress());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy Asset contract failed, error message is  " + e.getMessage());
        }
    }

    public boolean registerAssetAmount(String id, Double cash_amount, Double commodity_amount) {
        try {
            String contractAddress = loadAssetAddr();

            Asset reimburst = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = reimburst.regist(id, String.valueOf(cash_amount), String.valueOf(commodity_amount)).send();
            List<Asset.RegistEventEventResponse> response =  reimburst.getRegistEventEvents(result);
            if (!response.isEmpty()) {
                System.out.printf(" regist asset account success => asset: %s, value: %s, %s \n", id,
                        cash_amount, commodity_amount);
                return true;
            } else {
                System.out.printf(" regist asset account failed, code asset is %s \n", id);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" regist asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

    public Asset.QueryEventEventResponse queryReiAssetAmount(String id){
        try {
            String contractAddress = loadAssetAddr();

            Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = asset.query(id).send();
            List<Asset.QueryEventEventResponse> response = asset.getQueryEventEvents(result);
            if (!response.isEmpty()){
                System.out.printf(" query asset account success => asset: %s, value: %s , %s\n", response.get(0).ID,
                        response.get(0).cash_amount, response.get(0).commodity_amount);
                return response.get(0);
            }else {
                System.out.printf(" query asset account failed, code asset is %s \n", id);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" query Reimburst asset account failed, error message is %s\n", e.getMessage());
        }
        return null;
    }

    public boolean commodity_pay(String id, Double Money){
        try {
            String contractAddress = loadAssetAddr();

            Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = asset.query(id).send();
            List<Asset.QueryEventEventResponse> response = asset.getQueryEventEvents(result);
            if (response.isEmpty()){
                System.out.println(" asset is not exist!");
                return false;
            }else {
                Money = Double.valueOf(response.get(0).commodity_amount) - Money;
                asset.update_commodity(id, String.valueOf(Money));
                return true;
            }
        }catch (Exception e){
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" pay commodity asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

    public boolean cash_pay(String id, Double Money){
        try {
            String contractAddress = loadAssetAddr();

            Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = asset.query(id).send();
            List<Asset.QueryEventEventResponse> response = asset.getQueryEventEvents(result);
            if (response.isEmpty()){
                System.out.println(" asset is not exist!");
                return false;
            }else {
                Money = Double.valueOf(response.get(0).cash_amount) - Money;
                asset.update_cash(id, String.valueOf(Money));
                return true;
            }
        }catch (Exception e){
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" pay cash asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

    public boolean cash_repay(String id, Double Money){
        try {
            String contractAddress = loadAssetAddr();

            Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = asset.query(id).send();
            List<Asset.QueryEventEventResponse> response = asset.getQueryEventEvents(result);
            if (response.isEmpty()){
                System.out.println(" asset is not exist!");
                return false;
            }else {
                Money = Double.valueOf(response.get(0).cash_amount) + Money;
                asset.update_cash(id, String.valueOf(Money));
                return true;
            }
        }catch (Exception e){
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" repay cash asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

    public boolean commodity_repay(String id , Double Money){
        try {
            String contractAddress = loadAssetAddr();

            Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = asset.query(id).send();
            List<Asset.QueryEventEventResponse> response = asset.getQueryEventEvents(result);
            if (response.isEmpty()){
                System.out.println(" asset is not exist!");
                return false;
            }else {
                Money = Double.valueOf(response.get(0).commodity_amount) + Money;
                asset.update_commodity(id, String.valueOf(Money));
                return true;
            }
        }catch (Exception e){
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" repay commodity asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

    public boolean transfer(String id1, String id2, Double Money){
        try{
            String contractAddress = loadAssetAddr();

            Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result1 = asset.query(id1).send();
            List<Asset.QueryEventEventResponse> response1 = asset.getQueryEventEvents(result1);
            TransactionReceipt result2 = asset.query(id2).send();
            List<Asset.QueryEventEventResponse> response2 = asset.getQueryEventEvents(result2);
            if (response1.isEmpty() || response2.isEmpty()){
                System.out.println("asset is not exist!");
                return false;
            }else if (Double.valueOf(response1.get(0).cash_amount) < Money){
                System.out.println("Sorry, your credit is running low");
                return false;
            }else {
                Double Money1 = Double.valueOf(response1.get(0).cash_amount) - Money;
                Double Money2 = Double.valueOf(response2.get(0).cash_amount) + Money;
                asset.update_cash(id1, String.valueOf(Money1)).send();
                asset.update_cash(id2, String.valueOf(Money2)).send();
                return true;
            }
        }catch (Exception e){
// TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" transfer cash asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

}
