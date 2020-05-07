pragma solidity ^ 0.4 .10;
contract InsuranceClaim41 {
    function judge(int intervalTime, int cost, string diseaseType) returns(bool, int) {
        if (keccak256(diseaseType) != keccak256("")) {
            return (false, 0);
        }
        if (intervalTime > 365 * ) {
            return (false, 0);
        }
        int result = cost *  / 100;
        if (result > ) {
            result = ;
        }
        return (true, result);
    }
}