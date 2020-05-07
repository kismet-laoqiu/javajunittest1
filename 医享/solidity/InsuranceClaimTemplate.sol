pragma solidity ^ 0.4 .10;
contract ${contractName} {
    function judge(int intervalTime, int cost, string diseaseType) returns(bool, int) {
        if (keccak256(diseaseType) != keccak256("${claimDisease}")) {
            return (false, 0);
        }
        if (intervalTime > 365 * ${insurancePeriod}) {
            return (false, 0);
        }
        int result = cost * ${compensationRatio} / 100;
        if (result > ${generalMedicalInsurance}) {
            result = ${generalMedicalInsurance};
        }
        return (true, result);
    }
}