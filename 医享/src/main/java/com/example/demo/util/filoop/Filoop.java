package com.example.demo.util.filoop;

import cn.filoop.sdk.client.SDKClient;
import cn.hyperchain.sdk.account.Account;
import cn.hyperchain.sdk.common.solidity.Abi;
import cn.hyperchain.sdk.common.utils.ByteUtil;
import cn.hyperchain.sdk.common.utils.FileUtil;
import cn.hyperchain.sdk.common.utils.FuncParams;
import cn.hyperchain.sdk.response.CompileResponse;
import cn.hyperchain.sdk.response.ReceiptResponse;
import cn.hyperchain.sdk.service.AccountService;
import cn.hyperchain.sdk.service.ServiceManager;
import cn.hyperchain.sdk.transaction.Transaction;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: Filoop
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/9/30 22:30
 **/
public class Filoop {
    private static SDKClient sdkClient;
    private static SDKClient sdkClientTest;
    private static String json = "{\"address\":\"0x2a87c6f852968c6d4b50694f1e6b5eb4b7c48555\",\"algo\":\"0x03\",\"encrypted\":\"7958461548233e8dfa3ccfb85824a4b87edfc2dd6a9bac7c8305f0b3d81d7846\",\"version\":\"2.0\",\"privateKeyEncrypted\":false}";
    private static Account account;
    private static String accountAddress = "0x4aadbdff9e4fb6be9a324c166356b1e0a2e4c480";
    private static String accountAbi = "[{\"constant\":false,\"inputs\":[{\"name\":\"Id\",\"type\":\"string\"},{\"name\":\"Money\",\"type\":\"uint256\"}],\"name\":\"issue\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"Id\",\"type\":\"string\"},{\"name\":\"Type\",\"type\":\"string\"},{\"name\":\"Name\",\"type\":\"string\"},{\"name\":\"Rest\",\"type\":\"uint256\"}],\"name\":\"createEntity\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"Id\",\"type\":\"string\"},{\"name\":\"Type\",\"type\":\"string\"},{\"name\":\"Name\",\"type\":\"string\"},{\"name\":\"Rest\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"Id1\",\"type\":\"string\"},{\"name\":\"Id2\",\"type\":\"string\"},{\"name\":\"Money\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[],\"payable\":false,\"type\":\"function\"}]";
    private static String medicalRecordAddress = "0xe1228bae4f567a1784297487fb9ea4ac0bf864f1";
    private static String medicalRecordAbi = "[{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"string\"}],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"hash\",\"type\":\"string\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"type\":\"constructor\"}]";

    private static Map<String, String> contractAddressMap;
    private static Map<String, String> contractAbiMap;

    static {
        sdkClient = new SDKClient();
        sdkClient.setAppKey("5KxZAXlxyBygtvmITqmj");
        sdkClient.setAppSecret("dB7IS7WqW9zdds9SVRANpHD6HAezcH");
        sdkClient.setUuid("571cc20f-e114-11e9-8012-000000000000");
        sdkClient.setCertPath(System.getProperty("user.dir"));
        sdkClient.init();

        sdkClientTest = new SDKClient();
        sdkClientTest.setAppKey("5KxZAXlxyBygtvmITqmj");
        sdkClientTest.setAppSecret("dB7IS7WqW9zdds9SVRANpHD6HAezcH");
        sdkClientTest.setUuid("571cc20f-e114-11e9-8012-000000000000");
        sdkClientTest.setFiloceanTest(true);
        sdkClientTest.setCertPath(System.getProperty("user.dir"));
        sdkClientTest.init();
        AccountService accountService = ServiceManager.getAccountService(sdkClient.getProviderManager());
        account = accountService.fromAccountJson(json, "");
        try {
            File file = new File(System.getProperty("user.dir") + "\\filoop\\" + "contractSerialize.dat");
            if (file.exists()) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir") + "\\filoop\\" + "contractSerialize.dat"));
                ContractSerializeUtil contractSerializeUtil = (ContractSerializeUtil) in.readObject();
                in.close();
                contractAddressMap = contractSerializeUtil.getContractAddressMap();
                contractAbiMap = contractSerializeUtil.getContractAbiMap();
            } else {
                contractAddressMap = new HashMap<>();
                contractAbiMap = new HashMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean addMedicalRecord(String key, String value) {
        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(medicalRecordAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        params.addParams(key);
        params.addParams(value);
        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(medicalRecordAddress, "set(string,string)", abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClient.invoke(transaction);
        return "SUCCESS".equals(receiptResponse.getMessage());
    }

    public static boolean updateMedicalRecord(String key, String value) {
        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(medicalRecordAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        params.addParams(key);
        params.addParams(value);
        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(medicalRecordAddress, "set(string,string)", abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClient.invoke(transaction);
        return "SUCCESS".equals(receiptResponse.getMessage());
    }

    public static Map<String, String> queryMedicalRecord(String key) {
        Map<String, String> map = new HashMap<>();

        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(medicalRecordAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        params.addParams(key);
        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(medicalRecordAddress, "get(string)", abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClient.invoke(transaction);
        if (!"SUCCESS".equals(receiptResponse.getMessage())) {
            map.put("state", "error");
            map.put("data", "合约执行失败");
            return map;
        }

        byte[] bytes = ByteUtil.fromHex(receiptResponse.getRet());
        List<?> objects = abi.getFunction("get(string)").decodeResult(bytes);
        map.put("state", "success");
        map.put("data", (String) objects.get(0));
        return map;
    }

    public static Map<String, Object> queryAccount(String key) {
        Map<String, Object> map = new HashMap<>();

        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(accountAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        params.addParams(key);
        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(accountAddress, "query(string)", abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClient.invoke(transaction);

        if (!"SUCCESS".equals(receiptResponse.getMessage())) {
            map.put("state", "error");
            map.put("data", "合约执行失败");
            return map;
        }

        byte[] bytes = ByteUtil.fromHex(receiptResponse.getRet());
        List<?> objects = abi.getFunction("query(string)").decodeResult(bytes);
        com.example.demo.pojo.util.Account accountOutput = new com.example.demo.pojo.util.Account((String) objects.get(0), (String) objects.get(1), (String) objects.get(2), Long.parseLong(((String) objects.get(2))));
        map.put("state", "success");
        map.put("data", accountOutput);
        return map;
    }

    public static boolean createEntity(com.example.demo.pojo.util.Account accountInput) {
        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(accountAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        params.addParams(accountInput.getId());
        params.addParams(accountInput.getType());
        params.addParams(accountInput.getName());
        params.addParams(new BigInteger(accountInput.getRest() + ""));
        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(accountAddress, "createEntity(string,string,string,uint256)", abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClient.invoke(transaction);
        return "SUCCESS".equals(receiptResponse.getMessage());
    }

    public static boolean issue(String id, String money) {
        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(accountAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        params.addParams(id);
        params.addParams(new BigInteger(money));
        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(accountAddress, "issue(string,uint256)", abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClient.invoke(transaction);
        return "SUCCESS".equals(receiptResponse.getMessage());
    }

    public static boolean transfer(String transfererId, String transferreeId, String money) {
        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(accountAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        params.addParams(transfererId);
        params.addParams(transferreeId);
        params.addParams(new BigInteger(money));
        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(accountAddress, "transfer(string,string,uint256)", abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClient.invoke(transaction);
        return "SUCCESS".equals(receiptResponse.getMessage());
    }

    public static String deployContract(String name) {
        try {
            String sourceCode = FileUtil.readFile(new FileInputStream(new File(System.getProperty("user.dir") + "\\solidity\\InsuranceClaim\\" + name + ".sol")));
            CompileResponse response = sdkClientTest.Complie(sourceCode);
            String abiStr = response.getAbi()[0];
            String bin = response.getBin()[0];

            //无构造函数构造交易
            Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).deploy(bin).build();
            //交易签名
            transaction.sign(account);
            //通过sdkClient部署合约
            ReceiptResponse receiptResponse = sdkClientTest.deploy(transaction);
            if (!"SUCCESS".equals(receiptResponse.getMessage())) {
                return "";
            }
            String contractAddress = receiptResponse.getContractAddress();
            contractAddressMap.put(name, contractAddress);
            contractAbiMap.put(name, abiStr);
            File file = new File(System.getProperty("user.dir") + "\\filoop\\" + "contractSerialize.dat");
            ContractSerializeUtil contractSerializeUtil = new ContractSerializeUtil(contractAddressMap, contractAbiMap);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(contractSerializeUtil);
            out.close();
            return contractAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static Map<String, Object> callMethod(String contractName, String methodName, List<Object> list) {
        Map<String, Object> map = new HashMap<>();
        String contractAddress = contractAddressMap.get(contractName);
        String contractAbi = contractAbiMap.get(contractName);
        //加载本地合约bin、abi，当本地存在bin、abi时，可按下面方式直接加载bin、abi
        Abi abi = Abi.fromJson(contractAbi);
        //构造交易参数
        FuncParams params = new FuncParams();
        for (Object param : list) {
            params.addParams(param);
        }

        //构造交易
        Transaction transaction = new Transaction.EVMBuilder(account.getAddress()).invoke(contractAddress, methodName, abi, params).build();
        transaction.sign(account);
        //通过sdkClient调用合约
        ReceiptResponse receiptResponse = sdkClientTest.invoke(transaction);
        if (!"SUCCESS".equals(receiptResponse.getMessage())) {
            map.put("state", "error");
            map.put("data", "合约执行失败");
            return map;
        }

        byte[] bytes = ByteUtil.fromHex(receiptResponse.getRet());
        List<?> objects = abi.getFunction("judge(int256,int256,string)").decodeResult(bytes);
        map.put("state", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("flag", objects.get(0));
        data.put("claimAmount", objects.get(1));
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> judge(String contractName, int intervalTime, int cost, String diseaseType) {
        List<Object> list = new ArrayList<>();
        list.add(new BigInteger(intervalTime + ""));
        list.add(new BigInteger(cost + ""));
        list.add(diseaseType);
        return callMethod(contractName, "judge(int256,int256,string)", list);
    }


    public static void main(String[] args) {
        Map<String, Object> map = judge("InsuranceClaim25", 300, 10000, "心脏病");
        Map<String, Object> data= (Map<String, Object>) map.get("data");
        Boolean flag= (Boolean) data.get("flag");
        BigInteger claimAmount=(BigInteger)data.get("claimAmount");
        if(flag){
            System.out.println("1-"+claimAmount.intValue());
        }else{
            System.out.println(claimAmount);
        }

    }

}
