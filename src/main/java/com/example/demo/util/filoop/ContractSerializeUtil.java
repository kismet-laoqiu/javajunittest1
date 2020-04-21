package com.example.demo.util.filoop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ChainCodeUtil
 * @Description: java链码序列化
 * @Author: 刘敬
 * @Date: 2019/8/9 13:50
 **/
public class ContractSerializeUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, String> contractAddressMap = new HashMap<>();
    private Map<String, String> contractAbiMap = new HashMap<>();

    public ContractSerializeUtil(Map<String, String> contractAddressMap, Map<String, String> contractAbiMap) {
        this.contractAddressMap = contractAddressMap;
        this.contractAbiMap = contractAbiMap;
    }

    public Map<String, String> getContractAddressMap() {
        return contractAddressMap;
    }

    public void setContractAddressMap(Map<String, String> contractAddressMap) {
        this.contractAddressMap = contractAddressMap;
    }

    public Map<String, String> getContractAbiMap() {
        return contractAbiMap;
    }

    public void setContractAbiMap(Map<String, String> contractAbiMap) {
        this.contractAbiMap = contractAbiMap;
    }
}
