package com.meesho.notification_system.services;

import com.meesho.notification_system.documents.SmsElastic;
import com.meesho.notification_system.dto.req.Sms;
import com.meesho.notification_system.repositories.SmsElasticRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ElasticSearchServiceImpTest {


    @Mock
    private SmsElasticRepo smsElasticRepo;

    @InjectMocks
    private ElasticSearchServiceImp elasticSearchServiceImp;


    @Test
    void getSms_ReturnsSmsElasticList() {

        long startMillis = System.currentTimeMillis() - 100000;
        long endMillis = System.currentTimeMillis();
        String status = "SENT";

        List<SmsElastic> expectedSmsElasticList = Arrays.asList(
                SmsElastic.builder()
                        .id(12345)
                        .updatedAt(new Date())
                        .message("Hello_1")
                        .phoneNumber("+919876543210")
                        .status(SmsElastic.Status.SENT)
                        .build(),
                SmsElastic.builder()
                        .id(12346)
                        .updatedAt(new Date())
                        .message("Hello_2")
                        .phoneNumber("+919876543210")
                        .status(SmsElastic.Status.SENT)
                        .build()
        );

        // when

        when(smsElasticRepo.findByCreatedAtBetweenAndStatus(startMillis, endMillis, status)).thenReturn(expectedSmsElasticList);


        // then

        List<SmsElastic> actualSmsElasticList = elasticSearchServiceImp.getSms(startMillis, endMillis, status);

        assertThat(actualSmsElasticList.size()).isEqualTo(expectedSmsElasticList.size());
        verify(smsElasticRepo,times(1)).findByCreatedAtBetweenAndStatus(startMillis, endMillis, status);
    }

    @Test
    void searchMessages_ReturnsPageOfSmsElastic() {
        String searchText = "SomeRandomText";
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        // Sample data to mock the behavior of the Page
        List<SmsElastic> smsElasticList = Arrays.asList(
                SmsElastic.builder()
                        .id(12345)
                        .updatedAt(new Date())
                        .message("SomeRandomText 1")
                        .phoneNumber("+919876543210")
                        .status(SmsElastic.Status.SENT)
                        .build(),
                SmsElastic.builder()
                        .id(12346)
                        .updatedAt(new Date())
                        .message("SomeRandomText 2")
                        .phoneNumber("+919876543210")
                        .status(SmsElastic.Status.SENT)
                        .build()
        );

        Page<SmsElastic> smsElasticPage = new PageImpl<>(smsElasticList, pageable, smsElasticList.size());

        // when
        when(smsElasticRepo.findByMessageContaining(searchText, pageable)).thenReturn(smsElasticPage);

        // then
        Page<SmsElastic> actualSmsElasticPage = elasticSearchServiceImp.searchMessages(searchText, page, size);

        // Assertions
        assertThat(actualSmsElasticPage.getContent().size()).isEqualTo(2);
        assertThat(actualSmsElasticPage.getTotalElements()).isEqualTo(2);
        verify(smsElasticRepo, times(1)).findByMessageContaining(searchText, pageable);
    }

}