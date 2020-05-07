pragma solidity ^ 0.4 .10;
contract InsuranceClaim25 {
    function judge(int intervalTime, int cost, string diseaseType) returns(bool, int) {
        if (keccak256(diseaseType) != keccak256("心脏病")) {
            return (false, 0);
        }
        if (intervalTime > 365 * 1) {
            return (false, 0);
        }
        int result = cost * 90 / 100;
        if (result > 1000000) {
            result = 1000000;
        }
        return (true, result);
    }
}