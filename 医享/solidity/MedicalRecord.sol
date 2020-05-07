pragma solidity ^0.4.10;
//  存医疗记录
contract MedicalRecord {

  mapping(string => string)  medicalRecordMap;

  function MedicalRecord(){
  }

  function get (string id) returns (string) {
    return medicalRecordMap[id];
  }


  function set (string id,string hash)  {//存医疗记录ID  和hash  验证验证
    medicalRecordMap[id] = hash;
  }
 }