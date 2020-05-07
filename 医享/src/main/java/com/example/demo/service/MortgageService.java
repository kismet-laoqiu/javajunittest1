package com.example.demo.service;

import com.example.demo.pojo.table.Mortgage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @InterfaceName: MortgageService
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/8/6 22:55
 **/
public interface MortgageService {
    Mortgage findMortgageById(long mortgageId);

    int addMortgage(Mortgage mortgage, long patientId, MultipartFile file);

    List<Mortgage> listMortgageByPatient(long patientId);

    List<Mortgage> listExaminedMortgageByBank(long bankId);

    List<Mortgage> listUnExaminedMortgageByBank(long bankId);

    int agreeMortgage(long mortgageId);

    int refuseMortgage(long mortgageId);
}
