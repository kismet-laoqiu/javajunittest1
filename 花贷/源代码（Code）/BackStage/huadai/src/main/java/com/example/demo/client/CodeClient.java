package com.example.demo.client;

import com.example.demo.contract.Code;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
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

public class CodeClient {

    /**
     *
     *   区块链
     *        二维码上链，确保安全使用
     *
     *
     *    使用顺序：
     *      initialize                          连接区块链
     *      deployCodeAssetAndRecordAddr        部署合约
     *
     *    函数：
     *          registerCodeAssetAmount         上链
     *          queryCodeAssetAmount            查找
     *
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

    public void recordCodeAssetAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("code_address", address);
        final Resource contractResource = new ClassPathResource("Code.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "code contract address");
    }

    public String loadCodeAssetAddr() throws Exception {
        // load Asset contact address from com_contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("Code.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("code_address");
        System.out.println("contractAddress" + contractAddress);
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load Code Asset contract address failed, please deploy it first. ");
        }
        System.out.printf(" load Code Asset address from .properties, address is %s", contractAddress);
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

    public void deployCodeAssetAndRecordAddr() {

        try {
            Code code = Code.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
            System.out.println(" deploy Code Asset success, contract address is " + code.getContractAddress());

            recordCodeAssetAddr(code.getContractAddress());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy Code Asset contract failed, error message is  " + e.getMessage());
        }
    }

    public boolean registerCodeAssetAmount(String codenum, String codehash) {
        try {
            String contractAddress = loadCodeAssetAddr();

            Code code = Code.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = code.regist(codenum, codehash).send();
            List<Code.RegistEventEventResponse> response =  code.getRegistEventEvents(result);
            if (!response.isEmpty()) {
                System.out.printf(" regist code asset account success => asset: %s, value: %s \n", response.get(0).ID,
                            response.get(0).codehash);
                return true;
            } else {
                System.out.printf(" regist code asset account failed, code asset is %s \n",
                        codenum);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" regist code asset account failed, error message is %s\n", e.getMessage());
        }
        return false;
    }

    public String queryCodeAssetAmount(String codenum){
        try {
            String contractAddress = loadCodeAssetAddr();

            Code code = Code.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
            TransactionReceipt result = code.query(codenum).send();
            List<Code.QueryEventEventResponse> response = code.getQueryEventEvents(result);
            if (!response.isEmpty()){
                System.out.printf(" query code asset account success => asset: %s, value: %s \n", response.get(0).ID,
                        response.get(0).codehash);
                return response.get(0).codehash;
            }else {
                System.out.printf(" query code asset account failed, code asset is %s \n",
                        codenum);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            //logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" query code asset account failed, error message is %s\n", e.getMessage());
        }
        return "-1";
    }
}
