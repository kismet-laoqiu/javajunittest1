package com.example.demo.Service;

import com.example.demo.Dao.TD_Dao;
import com.example.demo.entity.TD_code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QrCodeService {

    @Autowired
    private TD_Dao td_dao;

    /*  查找二维码   */
    public List<TD_code> select_code(String identification){
        List<TD_code> td_codes =  td_dao.select_code(identification);
        for (int i = 0 ;i <td_codes.size(); i++){
            td_codes.get(i).setIdentification("");
        }
        return td_codes;
    }
}
