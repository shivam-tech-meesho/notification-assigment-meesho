package com.meesho.notification_system.services;

import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.exception.SmsRequestException;
import com.meesho.notification_system.repositories.SmsRequestRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsReqServiceExceptionTest {

    @Mock
    private SmsRequestRepo smsRequestRepo;

    @InjectMocks
    private SmsReqServiceImp smsReqService;

    @Test
    void getSmsRequestNotFound() {
        int requestId = 12345;

        when(smsRequestRepo.findById(requestId)).thenReturn(null);

        assertThatThrownBy(() -> smsReqService.getSmsRequest(requestId))
                .isInstanceOf(SmsRequestException.class)
                .hasMessageContaining("request_id " + requestId + " not found");
    }

    @Test
    void getSmsRequestInternalError() {
        int requestId = 12345;

        when(smsRequestRepo.findById(requestId)).thenThrow(new RuntimeException("Internal server error"));

        assertThatThrownBy(() -> smsReqService.getSmsRequest(requestId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Internal server error");
    }
}
