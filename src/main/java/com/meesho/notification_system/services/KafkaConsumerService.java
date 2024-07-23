package com.meesho.notification_system.services;

import com.meesho.notification_system.expection.SmsRequestException;

public interface KafkaConsumerService {
    public void listen(Integer request_id) throws SmsRequestException, SmsRequestException;
}
