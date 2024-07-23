package com.meesho.notification_system.repositories;

import com.meesho.notification_system.entities.SmsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmsRequestRepoTest {

    @Autowired
    private SmsRequestRepo smsRequestRepo;

    @Test
    void smsRequestRepo_findById_ReturnSmsRequest() {

        //input
        SmsRequest smsRequest = SmsRequest.builder()
                .phoneNumber("+919876543210")
                .message("Hello")
                .status(SmsRequest.Status.IN_PROGRESS)
                .build();

        smsRequest = smsRequestRepo.save(smsRequest);

        //when
        SmsRequest result = smsRequestRepo.findById(smsRequest.getId());

        //then
        assertThat(result).isNotNull();

    }
}