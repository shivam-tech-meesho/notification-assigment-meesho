package com.meesho.notification_system.services;


import com.meesho.notification_system.documents.SmsElastic;
import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.expection.SmsRequestException;
import com.meesho.notification_system.repositories.SmsElasticRepo;
import com.meesho.notification_system.repositories.SmsRequestRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImp implements KafkaConsumerService{

    @Autowired
    private SmsReqService smsReqService;

    @Autowired
    private SmsRequestRepo smsRequestRepo;

    @Autowired
    private SmsElasticRepo smsElasticRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @KafkaListener(topics="notifications",groupId = "notification_consumer_1")
    public void listen(Integer request_id) throws SmsRequestException {
        SmsRequest smsRequest = smsReqService.getSmsRequest(request_id);
        String message  = smsRequest.getMessage();
        String phoneNumber = smsRequest.getPhoneNumber();


        smsRequest.setStatus(SmsRequest.Status.SENT);
        smsRequestRepo.save(smsRequest);

        SmsElastic smsElastic = modelMapper.map(smsRequest, SmsElastic.class);
        smsElasticRepo.save(smsElastic);

    }
}
