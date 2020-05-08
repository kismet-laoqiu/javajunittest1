package com.example.demo.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.request.BcosFilter;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Code extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506107ed806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680637c26192914610051578063934252261461019f575b600080fd5b34801561005d57600080fd5b506100b8600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061024e565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156100fc5780820151818401526020810190506100e1565b50505050905090810190601f1680156101295780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b83811015610162578082015181840152602081019050610147565b50505050905090810190601f16801561018f5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b3480156101ab57600080fd5b5061024c600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610552565b005b606080600080846040518082805190602001908083835b60208310151561028a5780518252602082019150602081019050602083039250610265565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090507f3ee9eb791bf96e0bb342d5e845b58f3eb0ed1dc3f047e25fbe2cf1a0e93be0ed81600001826001016040518080602001806020018381038352858181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156103755780601f1061034a57610100808354040283529160200191610375565b820191906000526020600020905b81548152906001019060200180831161035857829003601f168201915b50508381038252848181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156103f85780601f106103cd576101008083540402835291602001916103f8565b820191906000526020600020905b8154815290600101906020018083116103db57829003601f168201915b505094505050505060405180910390a18060000181600101818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104a55780601f1061047a576101008083540402835291602001916104a5565b820191906000526020600020905b81548152906001019060200180831161048857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105415780601f1061051657610100808354040283529160200191610541565b820191906000526020600020905b81548152906001019060200180831161052457829003601f168201915b505050505090509250925050915091565b7fdb59803837782523800c4cc7e165d599a49a915cb91d0e63c9b5403537fd91c48282604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156105b957808201518184015260208101905061059e565b50505050905090810190601f1680156105e65780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561061f578082015181840152602081019050610604565b50505050905090810190601f16801561064c5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a16040805190810160405280838152602001828152506000836040518082805190602001908083835b6020831015156106a85780518252602082019150602081019050602083039250610683565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008201518160000190805190602001906106f792919061071c565b50602082015181600101908051906020019061071492919061071c565b509050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061075d57805160ff191683800117855561078b565b8280016001018555821561078b579182015b8281111561078a57825182559160200191906001019061076f565b5b509050610798919061079c565b5090565b6107be91905b808211156107ba5760008160009055506001016107a2565b5090565b905600a165627a7a723058204ead4ab77db7d931708045959e899cc5531ceb7c3bbe7943e559c0a1ebd328810029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"ID\",\"type\":\"string\"},{\"name\":\"codehash\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"ID\",\"type\":\"string\"},{\"name\":\"codehash\",\"type\":\"string\"}],\"name\":\"regist\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"codehash\",\"type\":\"string\"}],\"name\":\"QueryEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"codehash\",\"type\":\"string\"}],\"name\":\"RegistEvent\",\"type\":\"event\"}]";

    public static final String FUNC_QUERY = "query";

    public static final String FUNC_REGIST = "regist";

    public static final Event QUERYEVENT_EVENT = new Event("QueryEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event REGISTEVENT_EVENT = new Event("RegistEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Code(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Code(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Code(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Code(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> query(String id) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query(String id, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(id)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String querySeq(String id) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(id)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> regist(String ID, String codehash) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(codehash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void regist(String ID, String codehash, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(codehash)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registSeq(String ID, String codehash) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(codehash)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<QueryEventEventResponse> getQueryEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(QUERYEVENT_EVENT, transactionReceipt);
        ArrayList<QueryEventEventResponse> responses = new ArrayList<QueryEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            QueryEventEventResponse typedResponse = new QueryEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.codehash = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<QueryEventEventResponse> queryEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, QueryEventEventResponse>() {
            @Override
            public QueryEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(QUERYEVENT_EVENT, log);
                QueryEventEventResponse typedResponse = new QueryEventEventResponse();
                typedResponse.log = log;
                typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.codehash = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<QueryEventEventResponse> queryEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(QUERYEVENT_EVENT));
        return queryEventEventFlowable(filter);
    }

    public List<RegistEventEventResponse> getRegistEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEVENT_EVENT, transactionReceipt);
        ArrayList<RegistEventEventResponse> responses = new ArrayList<RegistEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RegistEventEventResponse typedResponse = new RegistEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.codehash = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RegistEventEventResponse> registEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, RegistEventEventResponse>() {
            @Override
            public RegistEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTEVENT_EVENT, log);
                RegistEventEventResponse typedResponse = new RegistEventEventResponse();
                typedResponse.log = log;
                typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.codehash = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegistEventEventResponse> registEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTEVENT_EVENT));
        return registEventEventFlowable(filter);
    }

    @Deprecated
    public static Code load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Code(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Code load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Code(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Code load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Code(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Code load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Code(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Code> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Code.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Code> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Code.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Code> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Code.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Code> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Code.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class QueryEventEventResponse {
        public Log log;

        public String ID;

        public String codehash;
    }

    public static class RegistEventEventResponse {
        public Log log;

        public String ID;

        public String codehash;
    }
}
