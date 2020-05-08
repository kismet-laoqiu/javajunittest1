pragma solidity ^0.4.25;

contract Hash {
    string hash;

    function Hash() {
        hash = "Hello, World!";
    }

    function get()constant returns(string) {
        return hash;
    }

    function set(string h) {
        hash = h;
    }
}
