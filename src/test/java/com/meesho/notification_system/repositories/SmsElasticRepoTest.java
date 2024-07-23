package com.meesho.notification_system.repositories;

import com.meesho.notification_system.documents.SmsElastic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmsElasticRepoTest {


    @Autowired
    private SmsElasticRepo smsElasticRepo;

    @Test
    void SmsElasticRepo_findByCreatedAtBetweenAndStatus_ReturnAllMatchingSmsRequest() {

        // insert 2 data  with status as SENT with updatedAt value as current time

        SmsElastic smsElastic1 = SmsElastic.builder()
                .id(12345)
                .updatedAt(new Date())
                .message("Hello_1")
                .phoneNumber("+919876543210")
                .status(SmsElastic.Status.SENT)
                .build();

        SmsElastic smsElastic2 = SmsElastic.builder()
                .id(12346)
                .updatedAt(new Date())
                .message("Hello_2")
                .phoneNumber("+919876543210")
                .status(SmsElastic.Status.SENT)
                .build();


        smsElasticRepo.save(smsElastic1);
        smsElasticRepo.save(smsElastic2);

        long starting_time = System.currentTimeMillis() - 10000;
        long ending_time = System.currentTimeMillis();

        // when
        List<SmsElastic> result = smsElasticRepo.findByCreatedAtBetweenAndStatus(starting_time, ending_time, SmsElastic.Status.SENT.toString());

        //then
        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    void SmsElasticRepo_findByMessageContaining_ReturnAllMatchingSmsWithText() {
        // Prepare test data
        String searchText = "SomeRandomText";
        SmsElastic smsElastic1 = SmsElastic.builder()
                .id(12347)
                .updatedAt(new Date())
                .message("SomeRandomText, are you there?")
                .phoneNumber("+919876543211")
                .status(SmsElastic.Status.SENT)
                .build();

        SmsElastic smsElastic2 = SmsElastic.builder()
                .id(12348)
                .updatedAt(new Date())
                .message("Yes, SomeRandomText! How can I assist?")
                .phoneNumber("+919876543212")
                .status(SmsElastic.Status.SENT)
                .build();

        // Save and ensure indexing
        smsElasticRepo.save(smsElastic1);
        smsElasticRepo.save(smsElastic2);


        // Page request for results
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<SmsElastic> results = smsElasticRepo.findByMessageContaining(searchText, pageable);

        // then
        assertThat(results.getTotalElements()).isEqualTo(2);

    }
}