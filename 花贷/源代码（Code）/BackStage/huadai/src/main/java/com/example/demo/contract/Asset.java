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
public class Asset extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50611466806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806331c3e4561461007257806366746885146101675780637c2619291461025c5780638f63b9b714610416578063f59d0826146104c5575b600080fd5b34801561007e57600080fd5b50610165600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610574565b005b34801561017357600080fd5b5061025a600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610703565b005b34801561026857600080fd5b506102c3600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061095f565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b8381101561030b5780820151818401526020810190506102f0565b50505050905090810190601f1680156103385780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b83811015610371578082015181840152602081019050610356565b50505050905090810190601f16801561039e5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156103d75780820151818401526020810190506103bc565b50505050905090810190601f1680156104045780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b34801561042257600080fd5b506104c3600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610d96565b005b3480156104d157600080fd5b50610572600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050611012565b005b61057e8383610d96565b6105888382610d96565b7f0f24a19d28b7842785657ca099cfc4d70d9e1005a3ce66bf1432becc7c3b279d83838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156105f45780820151818401526020810190506105d9565b50505050905090810190601f1680156106215780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561065a57808201518184015260208101905061063f565b50505050905090810190601f1680156106875780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156106c05780820151818401526020810190506106a5565b50505050905090810190601f1680156106ed5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a1505050565b7f550c5ec20915fc22d7fd95b6ec029b44016da56b0e3b207c792d8d9dce1ace4583838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b8381101561076f578082015181840152602081019050610754565b50505050905090810190601f16801561079c5780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156107d55780820151818401526020810190506107ba565b50505050905090810190601f1680156108025780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561083b578082015181840152602081019050610820565b50505050905090810190601f1680156108685780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a1606060405190810160405280848152602001838152602001828152506000846040518082805190602001908083835b6020831015156108cd57805182526020820191506020810190506020830392506108a8565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600082015181600001908051906020019061091c92919061128e565b50602082015181600101908051906020019061093992919061128e565b50604082015181600201908051906020019061095692919061128e565b50905050505050565b6060806060600080856040518082805190602001908083835b60208310151561099d5780518252602082019150602081019050602083039250610978565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090507f76e7a2b16861c62aa4daa7f385f27b960d7d5a5c6137f90eccd337bd0676fae381600001826001018360020160405180806020018060200180602001848103845287818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610a905780601f10610a6557610100808354040283529160200191610a90565b820191906000526020600020905b815481529060010190602001808311610a7357829003601f168201915b5050848103835286818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610b135780601f10610ae857610100808354040283529160200191610b13565b820191906000526020600020905b815481529060010190602001808311610af657829003601f168201915b5050848103825285818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610b965780601f10610b6b57610100808354040283529160200191610b96565b820191906000526020600020905b815481529060010190602001808311610b7957829003601f168201915b5050965050505050505060405180910390a1806000018160010182600201828054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c495780601f10610c1e57610100808354040283529160200191610c49565b820191906000526020600020905b815481529060010190602001808311610c2c57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ce55780601f10610cba57610100808354040283529160200191610ce5565b820191906000526020600020905b815481529060010190602001808311610cc857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d815780601f10610d5657610100808354040283529160200191610d81565b820191906000526020600020905b815481529060010190602001808311610d6457829003601f168201915b50505050509050935093509350509193909250565b600080836040518082805190602001908083835b602083101515610dcf5780518252602082019150602081019050602083039250610daa565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020905081816001019080519060200190610e1c92919061130e565b50806000846040518082805190602001908083835b602083101515610e565780518252602082019150602081019050602083039250610e31565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008201816000019080546001816001161561010002031660029004610eb092919061138e565b5060018201816001019080546001816001161561010002031660029004610ed892919061138e565b5060028201816002019080546001816001161561010002031660029004610f0092919061138e565b509050507f1daf1aeccbfee97c5a102c797f0aa3b156ec08627eca5b28b1704989b1ed37de8383604051808060200180602001838103835285818151815260200191508051906020019080838360005b83811015610f6b578082015181840152602081019050610f50565b50505050905090810190601f168015610f985780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b83811015610fd1578082015181840152602081019050610fb6565b50505050905090810190601f168015610ffe5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a1505050565b600080836040518082805190602001908083835b60208310151561104b5780518252602082019150602081019050602083039250611026565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090508181600201908051906020019061109892919061130e565b50806000846040518082805190602001908083835b6020831015156110d257805182526020820191506020810190506020830392506110ad565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000820181600001908054600181600116156101000203166002900461112c92919061138e565b506001820181600101908054600181600116156101000203166002900461115492919061138e565b506002820181600201908054600181600116156101000203166002900461117c92919061138e565b509050507f1daf1aeccbfee97c5a102c797f0aa3b156ec08627eca5b28b1704989b1ed37de8383604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156111e75780820151818401526020810190506111cc565b50505050905090810190601f1680156112145780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561124d578082015181840152602081019050611232565b50505050905090810190601f16801561127a5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a1505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106112cf57805160ff19168380011785556112fd565b828001600101855582156112fd579182015b828111156112fc5782518255916020019190600101906112e1565b5b50905061130a9190611415565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061134f57805160ff191683800117855561137d565b8280016001018555821561137d579182015b8281111561137c578251825591602001919060010190611361565b5b50905061138a9190611415565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106113c75780548555611404565b8280016001018555821561140457600052602060002091601f016020900482015b828111156114035782548255916001019190600101906113e8565b5b5090506114119190611415565b5090565b61143791905b8082111561143357600081600090555060010161141b565b5090565b905600a165627a7a72305820da63509010e965bcad8f7afc62bb074c714d17c21ce7bc84627b302838f9cc990029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"ID\",\"type\":\"string\"},{\"name\":\"cash\",\"type\":\"string\"},{\"name\":\"commodity\",\"type\":\"string\"}],\"name\":\"update\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"ID\",\"type\":\"string\"},{\"name\":\"cash_amount\",\"type\":\"string\"},{\"name\":\"commodity_amount\",\"type\":\"string\"}],\"name\":\"regist\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"ID\",\"type\":\"string\"},{\"name\":\"cash_amount\",\"type\":\"string\"},{\"name\":\"commodity_amount\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"ID\",\"type\":\"string\"},{\"name\":\"Money\",\"type\":\"string\"}],\"name\":\"update_cash\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"ID\",\"type\":\"string\"},{\"name\":\"Money\",\"type\":\"string\"}],\"name\":\"update_commodity\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"cash_amount\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"commodity_amount\",\"type\":\"string\"}],\"name\":\"QueryEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"codecash_amount\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"commodity_amount\",\"type\":\"string\"}],\"name\":\"RegistEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"cash\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"commodity_amount\",\"type\":\"string\"}],\"name\":\"UpdateEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"Money\",\"type\":\"string\"}],\"name\":\"Update_One_Event\",\"type\":\"event\"}]";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_REGIST = "regist";

    public static final String FUNC_QUERY = "query";

    public static final String FUNC_UPDATE_CASH = "update_cash";

    public static final String FUNC_UPDATE_COMMODITY = "update_commodity";

    public static final Event QUERYEVENT_EVENT = new Event("QueryEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event REGISTEVENT_EVENT = new Event("RegistEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event UPDATEEVENT_EVENT = new Event("UpdateEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event UPDATE_ONE_EVENT_EVENT = new Event("Update_One_Event", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> update(String ID, String cash, String commodity) {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(cash),
                new Utf8String(commodity)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void update(String ID, String cash, String commodity, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(cash),
                new Utf8String(commodity)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateSeq(String ID, String cash, String commodity) {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(cash),
                new Utf8String(commodity)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> regist(String ID, String cash_amount, String commodity_amount) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(cash_amount),
                new Utf8String(commodity_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void regist(String ID, String cash_amount, String commodity_amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(cash_amount),
                new Utf8String(commodity_amount)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registSeq(String ID, String cash_amount, String commodity_amount) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(cash_amount),
                new Utf8String(commodity_amount)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public RemoteCall<TransactionReceipt> update_cash(String ID, String Money) {
        final Function function = new Function(
                FUNC_UPDATE_CASH, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(Money)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void update_cash(String ID, String Money, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATE_CASH, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(Money)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String update_cashSeq(String ID, String Money) {
        final Function function = new Function(
                FUNC_UPDATE_CASH, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(Money)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> update_commodity(String ID, String Money) {
        final Function function = new Function(
                FUNC_UPDATE_COMMODITY, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(Money)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void update_commodity(String ID, String Money, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATE_COMMODITY, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(Money)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String update_commoditySeq(String ID, String Money) {
        final Function function = new Function(
                FUNC_UPDATE_COMMODITY, 
                Arrays.<Type>asList(new Utf8String(ID),
                new Utf8String(Money)),
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
            typedResponse.cash_amount = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.commodity_amount = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse.cash_amount = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.commodity_amount = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
            typedResponse.codecash_amount = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.commodity_amount = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse.codecash_amount = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.commodity_amount = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegistEventEventResponse> registEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTEVENT_EVENT));
        return registEventEventFlowable(filter);
    }

    public List<UpdateEventEventResponse> getUpdateEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEEVENT_EVENT, transactionReceipt);
        ArrayList<UpdateEventEventResponse> responses = new ArrayList<UpdateEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UpdateEventEventResponse typedResponse = new UpdateEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.cash = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.commodity_amount = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateEventEventResponse> updateEventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateEventEventResponse>() {
            @Override
            public UpdateEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEEVENT_EVENT, log);
                UpdateEventEventResponse typedResponse = new UpdateEventEventResponse();
                typedResponse.log = log;
                typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.cash = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.commodity_amount = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateEventEventResponse> updateEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEEVENT_EVENT));
        return updateEventEventFlowable(filter);
    }

    public List<Update_One_EventEventResponse> getUpdate_One_EventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATE_ONE_EVENT_EVENT, transactionReceipt);
        ArrayList<Update_One_EventEventResponse> responses = new ArrayList<Update_One_EventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            Update_One_EventEventResponse typedResponse = new Update_One_EventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.Money = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<Update_One_EventEventResponse> update_One_EventEventFlowable(BcosFilter filter) {
        return web3j.logFlowable(filter).map(new io.reactivex.functions.Function<Log, Update_One_EventEventResponse>() {
            @Override
            public Update_One_EventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATE_ONE_EVENT_EVENT, log);
                Update_One_EventEventResponse typedResponse = new Update_One_EventEventResponse();
                typedResponse.log = log;
                typedResponse.ID = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.Money = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<Update_One_EventEventResponse> update_One_EventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATE_ONE_EVENT_EVENT));
        return update_One_EventEventFlowable(filter);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class QueryEventEventResponse {
        public Log log;

        public String ID;

        public String cash_amount;

        public String commodity_amount;

        @Override
        public String toString() {
            return "QueryEventEventResponse{" +
                    "log=" + log +
                    ", ID='" + ID + '\'' +
                    ", cash_amount='" + cash_amount + '\'' +
                    ", commodity_amount='" + commodity_amount + '\'' +
                    '}';
        }
    }

    public static class RegistEventEventResponse {
        public Log log;

        public String ID;

        public String codecash_amount;

        public String commodity_amount;

        @Override
        public String toString() {
            return "RegistEventEventResponse{" +
                    "log=" + log +
                    ", ID='" + ID + '\'' +
                    ", codecash_amount='" + codecash_amount + '\'' +
                    ", commodity_amount='" + commodity_amount + '\'' +
                    '}';
        }
    }

    public static class UpdateEventEventResponse {
        public Log log;

        public String ID;

        public String cash;

        public String commodity_amount;

        @Override
        public String toString() {
            return "UpdateEventEventResponse{" +
                    "log=" + log +
                    ", ID='" + ID + '\'' +
                    ", cash='" + cash + '\'' +
                    ", commodity_amount='" + commodity_amount + '\'' +
                    '}';
        }
    }

    public static class Update_One_EventEventResponse {
        public Log log;

        public String ID;

        public String Money;

        @Override
        public String toString() {
            return "Update_One_EventEventResponse{" +
                    "log=" + log +
                    ", ID='" + ID + '\'' +
                    ", Money='" + Money + '\'' +
                    '}';
        }
    }
}
