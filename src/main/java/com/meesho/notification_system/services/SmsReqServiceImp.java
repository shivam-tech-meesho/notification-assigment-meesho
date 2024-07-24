package com.meesho.notification_system.services;

import com.meesho.notification_system.dto.req.Sms;
import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.exception.SmsRequestException;
import com.meesho.notification_system.repositories.SmsRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsReqServiceImp implements SmsReqService {

    @Autowired
    private SmsRequestRepo smsRequestRepo;


    @Override
    public SmsRequest createSmsRequest(Sms sms) {
        try {
            SmsRequest smsRequest = SmsRequest.builder()
                    .message(sms.getMessage())
                    .failureCode(null)
                    .failureComments(null)
                    .phoneNumber(sms.getPhoneNumber())
                    .status(SmsRequest.Status.IN_PROGRESS)
                    .build();

            return smsRequestRepo.save(smsRequest);
        } catch (Exception e) {
            System.out.println("Error in creating sms request" + e.getMessage());
            return null;
        }
    }

    @Override
    public SmsRequest getSmsRequest(int request_id) throws SmsRequestException {
        try {
            SmsRequest  smsRequest = smsRequestRepo.findById(request_id);
            if(smsRequest!=null)
                return smsRequest;
            else{
                throw new SmsRequestException("request_id " + request_id +  " not found");
            }
        } catch (SmsRequestException e) {
            System.out.println("Error in getting sms request" + e.getMessage());
            throw e;
        }
    }
}
