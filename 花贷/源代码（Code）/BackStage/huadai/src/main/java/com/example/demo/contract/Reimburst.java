package com.example.demo.contract;

import com.example.demo.entity.Reimburse;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jnr.ffi.annotations.In;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
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
public class Reimburst extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506116dc806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306eeae031461005c5780637c26192914610115578063f58a1c771461041a575b600080fd5b34801561006857600080fd5b50610113600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506105eb565b005b34801561012157600080fd5b5061017c600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506108fb565b6040518080602001806020018060200180602001888152602001806020018060200187810387528e818151815260200191508051906020019080838360005b838110156101d65780820151818401526020810190506101bb565b50505050905090810190601f1680156102035780820380516001836020036101000a031916815260200191505b5087810386528d818151815260200191508051906020019080838360005b8381101561023c578082015181840152602081019050610221565b50505050905090810190601f1680156102695780820380516001836020036101000a031916815260200191505b5087810385528c818151815260200191508051906020019080838360005b838110156102a2578082015181840152602081019050610287565b50505050905090810190601f1680156102cf5780820380516001836020036101000a031916815260200191505b5087810384528b818151815260200191508051906020019080838360005b838110156103085780820151818401526020810190506102ed565b50505050905090810190601f1680156103355780820380516001836020036101000a031916815260200191505b50878103835289818151815260200191508051906020019080838360005b8381101561036e578082015181840152602081019050610353565b50505050905090810190601f16801561039b5780820380516001836020036101000a031916815260200191505b50878103825288818151815260200191508051906020019080838360005b838110156103d45780820151818401526020810190506103b9565b50505050905090810190601f1680156104015780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390f35b34801561042657600080fd5b506105e9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506110dc565b005b600080846040518082805190602001908083835b60208310151561062457805182526020820191506020810190506020830392506105ff565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090508281600401819055508181600501908051906020019061067a929190611504565b50806000856040518082805190602001908083835b6020831015156106b4578051825260208201915060208101905060208303925061068f565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206000820181600001908054600181600116156101000203166002900461070e929190611584565b5060018201816001019080546001816001161561010002031660029004610736929190611584565b506002820181600201908054600181600116156101000203166002900461075e929190611584565b5060038201816003019080546001816001161561010002031660029004610786929190611584565b5060048201548160040155600582018160050190805460018160011615610100020316600290046107b8929190611584565b50600682018160060190805460018160011615610100020316600290046107e0929190611584565b509050507f494a680e126623ea5d92bc579d2682678fed381cc4caf9540273627837556add848484604051808060200184815260200180602001838103835286818151815260200191508051906020019080838360005b83811015610852578082015181840152602081019050610837565b50505050905090810190601f16801561087f5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156108b857808201518184015260208101905061089d565b50505050905090810190601f1680156108e55780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a150505050565b6060806060806000606080600080896040518082805190602001908083835b60208310151561093f578051825260208201915060208101905060208303925061091a565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902090507f0e8b08dd172b0cf3849129e67abb0926ef83bb2656aa5fcde5aff668c0be624c81600001826001018360020184600301856004015486600501876006016040518080602001806020018060200180602001888152602001806020018060200187810387528e818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610a555780601f10610a2a57610100808354040283529160200191610a55565b820191906000526020600020905b815481529060010190602001808311610a3857829003601f168201915b505087810386528d818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610ad85780601f10610aad57610100808354040283529160200191610ad8565b820191906000526020600020905b815481529060010190602001808311610abb57829003601f168201915b505087810385528c818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610b5b5780601f10610b3057610100808354040283529160200191610b5b565b820191906000526020600020905b815481529060010190602001808311610b3e57829003601f168201915b505087810384528b818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610bde5780601f10610bb357610100808354040283529160200191610bde565b820191906000526020600020905b815481529060010190602001808311610bc157829003601f168201915b5050878103835289818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610c615780601f10610c3657610100808354040283529160200191610c61565b820191906000526020600020905b815481529060010190602001808311610c4457829003601f168201915b5050878103825288818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610ce45780601f10610cb957610100808354040283529160200191610ce4565b820191906000526020600020905b815481529060010190602001808311610cc757829003601f168201915b50509d505050505050505050505050505060405180910390a18060000181600101826002018360030184600401548560050186600601868054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610daf5780601f10610d8457610100808354040283529160200191610daf565b820191906000526020600020905b815481529060010190602001808311610d9257829003601f168201915b50505050509650858054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e4b5780601f10610e2057610100808354040283529160200191610e4b565b820191906000526020600020905b815481529060010190602001808311610e2e57829003601f168201915b50505050509550848054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ee75780601f10610ebc57610100808354040283529160200191610ee7565b820191906000526020600020905b815481529060010190602001808311610eca57829003601f168201915b50505050509450838054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f835780601f10610f5857610100808354040283529160200191610f83565b820191906000526020600020905b815481529060010190602001808311610f6657829003601f168201915b50505050509350818054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561101f5780601f10610ff45761010080835404028352916020019161101f565b820191906000526020600020905b81548152906001019060200180831161100257829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110bb5780601f10611090576101008083540402835291602001916110bb565b820191906000526020600020905b81548152906001019060200180831161109e57829003601f168201915b50505050509050975097509750975097509750975050919395979092949650565b60e060405190810160405280888152602001878152602001868152602001858152602001848152602001838152602001828152506000886040518082805190602001908083835b6020831015156111485780518252602082019150602081019050602083039250611123565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600082015181600001908051906020019061119792919061160b565b5060208201518160010190805190602001906111b492919061160b565b5060408201518160020190805190602001906111d192919061160b565b5060608201518160030190805190602001906111ee92919061160b565b506080820151816004015560a082015181600501908051906020019061121592919061160b565b5060c082015181600601908051906020019061123292919061160b565b509050507fe15fac52612d1b34e592fef0db298bfe108e80556e2ae7a3ef4dd185ee12795e878787878787876040518080602001806020018060200180602001888152602001806020018060200187810387528e818151815260200191508051906020019080838360005b838110156112b857808201518184015260208101905061129d565b50505050905090810190601f1680156112e55780820380516001836020036101000a031916815260200191505b5087810386528d818151815260200191508051906020019080838360005b8381101561131e578082015181840152602081019050611303565b50505050905090810190601f16801561134b5780820380516001836020036101000a031916815260200191505b5087810385528c818151815260200191508051906020019080838360005b83811015611384578082015181840152602081019050611369565b50505050905090810190601f1680156113b15780820380516001836020036101000a031916815260200191505b5087810384528b818151815260200191508051906020019080838360005b838110156113ea5780820151818401526020810190506113cf565b50505050905090810190601f1680156114175780820380516001836020036101000a031916815260200191505b50878103835289818151815260200191508051906020019080838360005b83811015611450578082015181840152602081019050611435565b50505050905090810190601f16801561147d5780820380516001836020036101000a031916815260200191505b50878103825288818151815260200191508051906020019080838360005b838110156114b657808201518184015260208101905061149b565b50505050905090810190601f1680156114e35780820380516001836020036101000a031916815260200191505b509d505050505050505050505050505060405180910390a150505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061154557805160ff1916838001178555611573565b82800160010185558215611573579182015b82811115611572578251825591602001919060010190611557565b5b509050611580919061168b565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106115bd57805485556115fa565b828001600101855582156115fa57600052602060002091601f016020900482015b828111156115f95782548255916001019190600101906115de565b5b509050611607919061168b565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061164c57805160ff191683800117855561167a565b8280016001018555821561167a579182015b8281111561167957825182559160200191906001019061165e565b5b509050611687919061168b565b5090565b6116ad91905b808211156116a9576000816000905550600101611691565b5090565b905600a165627a7a723058208a138aad46048e6fc12eb3095a2cc2a5b8ac0c43d67781954cd8c5ee709f8e750029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"order_num\",\"type\":\"string\"},{\"name\":\"State\",\"type\":\"uint256\"},{\"name\":\"Description\",\"type\":\"string\"}],\"name\":\"update_state\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"order_num\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"order_num\",\"type\":\"string\"},{\"name\":\"Id\",\"type\":\"string\"},{\"name\":\"Bussinesslicence\",\"type\":\"string\"},{\"name\":\"Value\",\"type\":\"string\"},{\"name\":\"State\",\"type\":\"uint256\"},{\"name\":\"Description\",\"type\":\"string\"},{\"name\":\"CommodityID\",\"type\":\"string\"}],\"name\":\"regist\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"order_num\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"Bussinesslicence\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"Value\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"State\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"Description\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"CommodityID\",\"type\":\"string\"}],\"name\":\"RegistEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"order_num\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"ID\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"Bussinesslicence\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"Value\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"State\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"Description\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"CommodityID\",\"type\":\"string\"}],\"name\":\"QueryEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"order_num\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"State\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"Description\",\"type\":\"string\"}],\"name\":\"UpdateEvent\",\"type\":\"event\"}]";

    public static final String FUNC_UPDATE_STATE = "update_state";

    public static final String FUNC_QUERY = "query";

    public static final String FUNC_REGIST = "regist";

    public static final Event REGISTEVENT_EVENT = new Event("RegistEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event QUERYEVENT_EVENT = new Event("QueryEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event UPDATEEVENT_EVENT = new Event("UpdateEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Reimburst(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Reimburst(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Reimburst(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Reimburst(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> update_state(String order_num, BigInteger State, String Description) {
        final Function function = new Function(
                FUNC_UPDATE_STATE, 
                Arrays.<Type>asList(new Utf8String(order_num),
                new Uint256(State),
                new Utf8String(Description)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void update_state(String order_num, BigInteger State, String Description, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATE_STATE, 
                Arrays.<Type>asList(new Utf8String(order_num),
                new Uint256(State),
                new Utf8String(Description)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String update_stateSeq(String order_num, BigInteger State, String Description) {
        final Function function = new Function(
                FUNC_UPDATE_STATE, 
                Arrays.<Type>asList(new Utf8String(order_num),
                new Uint256(State),
                new Utf8String(Description)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> query(String order_num) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(order_num)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query(String order_num, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(order_num)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String querySeq(String order_num) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(order_num)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> regist(String order_num, String Id, String Bussinesslicence, String Value, BigInteger State, String Description, String CommodityID) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(order_num),
                new Utf8String(Id),
                new Utf8String(Bussinesslicence),
                new Utf8String(Value),
                new Uint256(State),
                new Utf8String(Description),
                new Utf8String(CommodityID)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void regist(String order_num, String Id, String Bussinesslicence, String Value, BigInteger State, String Description, String CommodityID, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(order_num),
                new Utf8String(Id),
                new Utf8String(Bussinesslicence),
                new Utf8String(Value),
                new Uint256(State),
                new Utf8String(Description),
                new Utf8String(CommodityID)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registSeq(String order_num, String Id, String Bussinesslicence, String Value, BigInteger State, String Description, String CommodityID) {
        final Function function = new Function(
                FUNC_REGIST, 
                Arrays.<Type>asList(new Utf8String(order_num),
                new Utf8String(Id),
                new Utf8String(Bussinesslicence),
                new Utf8String(Value),
                new Uint256(State),
                new Utf8String(Description),
                new Utf8String(CommodityID)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<RegistEventEventResponse> getRegistEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEVENT_EVENT, transactionReceipt);
        ArrayList<RegistEventEventResponse> responses = new ArrayList<RegistEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RegistEventEventResponse typedResponse = new RegistEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.order_num = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ID = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.Bussinesslicence = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.Value = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.State = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.Description = (String) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.CommodityID = (String) eventValues.getNonIndexedValues().get(6).getValue();
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
                typedResponse.order_num = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.ID = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.Bussinesslicence = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.Value = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.State = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.Description = (String) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.CommodityID = (String) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegistEventEventResponse> registEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTEVENT_EVENT));
        return registEventEventFlowable(filter);
    }

    public List<QueryEventEventResponse> getQueryEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(QUERYEVENT_EVENT, transactionReceipt);
        ArrayList<QueryEventEventResponse> responses = new ArrayList<QueryEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            QueryEventEventResponse typedResponse = new QueryEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.order_num = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ID = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.Bussinesslicence = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.Value = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.State = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.Description = (String) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.CommodityID = (String) eventValues.getNonIndexedValues().get(6).getValue();
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
                typedResponse.order_num = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.ID = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.Bussinesslicence = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.Value = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.State = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.Description = (String) eventValues.getNonIndexedValues().get(5).getValue();
                typedResponse.CommodityID = (String) eventValues.getNonIndexedValues().get(6).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<QueryEventEventResponse> queryEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(QUERYEVENT_EVENT));
        return queryEventEventFlowable(filter);
    }

    public List<UpdateEventEventResponse> getUpdateEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEEVENT_EVENT, transactionReceipt);
        ArrayList<UpdateEventEventResponse> responses = new ArrayList<UpdateEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UpdateEventEventResponse typedResponse = new UpdateEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.order_num = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.State = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.Description = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse.order_num = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.State = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.Description = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateEventEventResponse> updateEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        BcosFilter filter = new BcosFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEEVENT_EVENT));
        return updateEventEventFlowable(filter);
    }

    @Deprecated
    public static Reimburst load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Reimburst(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Reimburst load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Reimburst(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Reimburst load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Reimburst(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Reimburst load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Reimburst(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Reimburst> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Reimburst.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Reimburst> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Reimburst.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Reimburst> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Reimburst.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Reimburst> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Reimburst.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class RegistEventEventResponse {
        public Log log;

        public String order_num;

        public String ID;

        public String Bussinesslicence;

        public String Value;

        public BigInteger State;

        public String Description;

        public String CommodityID;

        @Override
        public String toString() {
            return "RegistEventEventResponse{" +
                    "log=" + log +
                    ", order_num='" + order_num + '\'' +
                    ", ID='" + ID + '\'' +
                    ", Bussinesslicence='" + Bussinesslicence + '\'' +
                    ", Value='" + Value + '\'' +
                    ", State=" + State +
                    ", Description='" + Description + '\'' +
                    ", CommodityID='" + CommodityID + '\'' +
                    '}';
        }
    }

    public static class QueryEventEventResponse {
        public Log log;

        public String order_num;

        public String ID;

        public String Bussinesslicence;

        public String Value;

        public BigInteger State;

        public String Description;

        public String CommodityID;

        @Override
        public String toString() {
            return "QueryEventEventResponse{" +
                    "log=" + log +
                    ", order_num='" + order_num + '\'' +
                    ", ID='" + ID + '\'' +
                    ", Bussinesslicence='" + Bussinesslicence + '\'' +
                    ", Value='" + Value + '\'' +
                    ", State=" + State +
                    ", Description='" + Description + '\'' +
                    ", CommodityID='" + CommodityID + '\'' +
                    '}';
        }

        public Reimburse torei(){
            Reimburse reimburse = new Reimburse();
            reimburse.setOrder_num(Integer.valueOf(this.order_num));
            reimburse.setIdenfication(this.ID);
            reimburse.setBussinesslicence(this.Bussinesslicence);
            reimburse.setValue(Double.valueOf(this.Value));
            reimburse.setState(Integer.valueOf(String.valueOf(this.State)));
            reimburse.setDescription(this.Description);
            reimburse.setCommodity_id(this.CommodityID);
            return reimburse;
        }
    }

    public static class UpdateEventEventResponse {
        public Log log;

        public String order_num;

        public BigInteger State;

        public String Description;

        @Override
        public String toString() {
            return "UpdateEventEventResponse{" +
                    "log=" + log +
                    ", order_num='" + order_num + '\'' +
                    ", State=" + State +
                    ", Description='" + Description + '\'' +
                    '}';
        }
    }
}
