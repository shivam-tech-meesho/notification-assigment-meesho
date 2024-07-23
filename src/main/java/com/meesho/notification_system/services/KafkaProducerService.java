package com.meesho.notification_system.services;

import org.springframework.stereotype.Service;

@Service
public interface KafkaProducerService {
    public void sendMessage(Integer request_id);
}
