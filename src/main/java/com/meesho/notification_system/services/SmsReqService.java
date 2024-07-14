package com.meesho.notification_system.services;

import com.meesho.notification_system.dto.req.Sms;
import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.expection.SmsRequestExceptionHandler;
import org.springframework.stereotype.Service;

@Service
public interface SmsReqService {
     public SmsRequest createSmsRequest(Sms smsRequest);
     public SmsRequest getSmsRequest(int request_id) throws SmsRequestExceptionHandler;
}
