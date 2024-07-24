package com.meesho.notification_system.services;

import com.meesho.notification_system.dto.req.Sms;
import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.exception.SmsRequestException;
import com.meesho.notification_system.repositories.SmsRequestRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsReqServiceImpTest {


    @Mock
    private SmsRequestRepo smsRequestRepo;


    @InjectMocks
    private SmsReqServiceImp smsReqService;

    @Test
    void createSmsRequest() {

        Sms sms =  Sms.builder()
                .phoneNumber("+910000000000")
                .message("Hello")
                .build();

        SmsRequest expectedSmsRequest = SmsRequest.builder()
                .message(sms.getMessage())
                .phoneNumber(sms.getPhoneNumber())
                .status(SmsRequest.Status.IN_PROGRESS)
                .build();


        when(smsRequestRepo.save(expectedSmsRequest)).thenAnswer(invocation -> {
            System.out.println(invocation);
            SmsRequest original = invocation.getArgument(0);
            Date now = new Date();
            return SmsRequest.builder()
                    .id(1000000)
                    .message(original.getMessage())
                    .phoneNumber(original.getPhoneNumber())
                    .status(original.getStatus())
                    .failureCode(original.getFailureCode())
                    .failureComments(original.getFailureComments())
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
        });

        SmsRequest actualSmsRequest = smsReqService.createSmsRequest(sms);


        assertThat(actualSmsRequest.getMessage()).isEqualTo(expectedSmsRequest.getMessage());
        assertThat(actualSmsRequest.getPhoneNumber()).isEqualTo(expectedSmsRequest.getPhoneNumber());
        assertThat(actualSmsRequest.getStatus()).isEqualTo(expectedSmsRequest.getStatus());
        assertThat(actualSmsRequest.getId()).isNotNull();
        assertThat(actualSmsRequest.getCreatedAt()).isNotNull();
        assertThat(actualSmsRequest.getUpdatedAt()).isNotEqualTo(0);
    }

    @Test
    void getSmsRequest() throws SmsRequestException {

        SmsRequest expectedSmsRequest = SmsRequest.builder()
                .id(12345)
                .message("Hello")
                .phoneNumber("+910000000000")
                .status(SmsRequest.Status.IN_PROGRESS)
                .build();

        when(smsRequestRepo.findById(12345)).thenReturn(expectedSmsRequest);

        SmsRequest actualSmsRequest = smsReqService.getSmsRequest(12345);

        assertThat(actualSmsRequest).isNotNull();
        assertThat(actualSmsRequest.getId()).isEqualTo(expectedSmsRequest.getId());
        assertThat(actualSmsRequest.getMessage()).isEqualTo(expectedSmsRequest.getMessage());
        assertThat(actualSmsRequest.getPhoneNumber()).isEqualTo(expectedSmsRequest.getPhoneNumber());
        assertThat(actualSmsRequest.getStatus()).isEqualTo(expectedSmsRequest.getStatus());


    }
}