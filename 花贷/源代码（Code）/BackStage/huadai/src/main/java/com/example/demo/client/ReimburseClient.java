package com.example.demo.client;

import com.example.demo.contract.Code;
import com.example.demo.contract.Reimburst;
import com.example.demo.entity.Reimburse;
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

public class ReimburseClient {

    /**
     *
     *   区块链
     *        报销信息上链，确保安全使用
     *
     *
     *    使用顺序：
     *      initialize                         连接区块链
     *      deployReiAssetAndRecordAddr        部署合约
     *
     *    函数：
     *          registerReiAssetAmount         上链
     *          queryReiAssetAmount            查找
     *          updateReiState                 更新报销链信息
     *
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

    public void recordReiAssetAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("rei_address", address);
        final Resource contractResource = new ClassPathResource("Reimburse.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "Reimburse contract address");
    }

    public String loadReiAssetAddr() throws Exception {
        // load Asset contact address from com_contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("Reimburse.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("rei_address");
        System.out.println("contractAddress" + contractAddress);
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load Reimburse Asset contract address failed, please deploy it first. ");
        }
        System.out.printf(" load Reimburse Asset address from .properties, address is %s", contractAddress);
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

    public void deployReiAssetAndRecordAddr() {

        try {
            Reimburst reimburst = Reimburst.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
            System.out.println(" deploy Reimburse Asset success, contract address is " + reimburst.getContractAddress());

            recordReiAssetAddr(reimburst.getContractAddress());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy Reimburse Asset contract failed, error message is  " + e.getMessage());
        }
    }

    public boolean registerReiAssetAmount(Reimburse reimburse) {
        try {
            String contractAddress = loadReiAssetAddr();

            Reimburst reimburst = Reimburst.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = reimburst.regist(reimburse.getOrder_num().toString(), reimburse.getIdenfication(), reimburse.getBussinesslicence(),
                    String.valueOf(reimburse.getValue()), BigInteger.valueOf(reimburse.getState()), reimburse.getDescription(), reimburse.getCommodity_id()).send();
            List<Reimburst.RegistEventEventResponse> response =  reimburst.getRegistEventEvents(result);
            if (!response.isEmpty()) {
                System.out.printf(" regist Reimburst asset account success => asset: %s, value: %s \n", response.get(0).order_num,
                        response.get(0));
                return true;
            } else {
                System.out.printf(" regist Reimburst asset account failed, code asset is %s \n",
                        reimburse.getOrder_num());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" regist code asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

    public Reimburse queryReiAssetAmount(String order_num){
        try {
            String contractAddress = loadReiAssetAddr();

            Reimburst reimburst = Reimburst.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = reimburst.query(order_num).send();
            List<Reimburst.QueryEventEventResponse> response = reimburst.getQueryEventEvents(result);
            if (!response.isEmpty()){
                System.out.printf(" query Reimburst asset account success => asset: %s, value: %s \n", response.get(0).order_num,
                        response.get(0));
                return response.get(0).torei();
            }else {
                System.out.printf(" query Reimburst asset account failed, code asset is %s \n",
                        order_num);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" query Reimburst asset account failed, error message is %s\n", e.getMessage());
        }
        return null;
    }

    public boolean updateReiState(String order_num, BigInteger State, String Description){
        try{
            String contractAddress = loadReiAssetAddr();

            Reimburst reimburst = Reimburst.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = reimburst.update_state(order_num, State, Description).send();
            List<Reimburst.UpdateEventEventResponse> response = reimburst.getUpdateEventEvents(result);
            if (!response.isEmpty()){
                System.out.printf(" query Reimburst asset account success => asset: %s, value: %s \n", response.get(0).order_num,
                        response.get(0));
                if (response.get(0).State.equals(State))
                    return false;
                else
                    return true;
            }else {
                System.out.printf(" query Reimburst asset account failed, code asset is %s \n",
                        order_num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
