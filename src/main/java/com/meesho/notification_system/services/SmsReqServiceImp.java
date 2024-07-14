package com.meesho.notification_system.services;

import com.meesho.notification_system.dto.req.Sms;
import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.expection.SmsRequestExceptionHandler;
import com.meesho.notification_system.repositories.SmsRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsReqServiceImp implements SmsReqService {

    @Autowired
    private SmsRequestRepo smsRequestRepo;

    @Override
    public SmsRequest createSmsRequest(Sms sms) {
        SmsRequest smsRequest = SmsRequest.builder()
                .message(sms.getMessage())
                .failureCode(null)
                .failureComments(null)
                .phoneNumber(sms.getPhoneNumber())
                .status(SmsRequest.Status.IN_PROGRESS)
                .build();

        return smsRequestRepo.save(smsRequest);
    }

    @Override
    public SmsRequest getSmsRequest(int request_id) throws SmsRequestExceptionHandler {
        SmsRequest  smsRequest = smsRequestRepo.findById(request_id);
        if(smsRequest!=null)
            return smsRequest;
        else{
            throw new SmsRequestExceptionHandler("request_id " + request_id +  " not found");
        }

    }
}
