pragma solidity ^ 0.4 .10;
contract InsuranceClaim54 {
    function judge(int intervalTime, int cost, string diseaseType) returns(bool, int) {
        if (keccak256(diseaseType) != keccak256("1")) {
            return (false, 0);
        }
        if (intervalTime > 365 * 1) {
            return (false, 0);
        }
        int result = cost * 1 / 100;
        if (result > 10000001) {
            result = 10000001;
        }
        return (true, result);
    }
}