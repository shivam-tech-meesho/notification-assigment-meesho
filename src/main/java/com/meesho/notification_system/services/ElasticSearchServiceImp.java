package com.meesho.notification_system.services;

import com.meesho.notification_system.documents.SmsElastic;
import com.meesho.notification_system.repositories.SmsElasticRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticSearchServiceImp implements ElasticSearchService{

    @Autowired
    private SmsElasticRepo smsElasticRepo;
    @Override
    public List<SmsElastic> getSms(long startMillis, long endMillis, String status) {
        try {
            return smsElasticRepo.findByCreatedAtBetweenAndStatus(startMillis,endMillis,status);
        } catch (Exception e) {
            System.out.println("Error in getting sms from elastic search" + e.getMessage());
            return null;
        }
    }

    @Override
    public Page<SmsElastic> searchMessages(String searchText, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return smsElasticRepo.findByMessageContaining(searchText, pageable);
        } catch (Exception e) {
            System.out.println("Error in searching messages from elastic search" + e.getMessage());
            return null;
        }
    }
}
