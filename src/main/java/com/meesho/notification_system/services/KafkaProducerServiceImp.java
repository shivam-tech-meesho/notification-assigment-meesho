package com.meesho.notification_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.Callback;

@Service
public class KafkaProducerServiceImp implements KafkaProducerService{

    @Autowired
    private KafkaTemplate<String, Integer> kafkaTemplate;

    @Override
    @Async
    public void sendMessage(Integer request_id)  {
        try {
            kafkaTemplate.send("notifications",request_id);
        } catch (Exception e) {
            System.out.println("Error in sending message to kafka" + e.getMessage());
        }
    }
}
