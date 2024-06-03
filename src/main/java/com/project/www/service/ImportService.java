package com.project.www.service;

import com.project.www.domain.PaymentDTO;
import com.project.www.repository.PaymentMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ImportService {

    private IamportClient api;

    @Autowired
    private PaymentMapper paymentMapper;

    public ImportService() {
        this.api = new IamportClient(
                "2045855176781372",
                "Ge0epYCqg47ZoTVdMM2eRUOqW2SOeHbiJw4E8e9nqGLP7N8BRXI0mWw1gsfvhunBq4fn2U2gCQUgBxWS");
    }


    public void postPrepare(PaymentDTO paymentDTO) throws IamportResponseException, IOException {

        PrepareData prepareData = new PrepareData(
                paymentDTO.getMerchantUid(),
                BigDecimal.valueOf(paymentDTO.getPayPrice()) // double 또는 long 타입일 경우
        );

    }
}
