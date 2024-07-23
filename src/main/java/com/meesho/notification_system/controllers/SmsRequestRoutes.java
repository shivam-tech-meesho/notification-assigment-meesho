package com.meesho.notification_system.controllers;


import com.meesho.notification_system.documents.SmsElastic;
import com.meesho.notification_system.dto.req.Sms;
import com.meesho.notification_system.dto.res.ErrorResponse;
import com.meesho.notification_system.dto.res.SmsReqSuccess;
import com.meesho.notification_system.dto.res.SuccessResponse;
import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.expection.SmsRequestException;
import com.meesho.notification_system.services.*;
import com.meesho.notification_system.utils.DateAndTimeConversion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/sms")
public class SmsRequestRoutes {

    @Autowired
    private SmsReqService smsReqService;

    @Autowired
    private KafkaProducerService kafkaProducerService;



    @Autowired
    private ElasticSearchServiceImp elasticSearchServiceImp;


    @Autowired
    private UserService userService;


    @PostMapping("/send")
    public ResponseEntity<?> sendSmsRequest(@Valid @RequestBody Sms sms) {
        try {
            SmsRequest smsRequest = smsReqService.createSmsRequest(sms);
            kafkaProducerService.sendMessage(smsRequest.getId());
            userService.createUser(smsRequest.getPhoneNumber());
            SmsReqSuccess smsobj = new SmsReqSuccess(String.valueOf(smsRequest.getId()), "Successfully Sent");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SuccessResponse(smsobj));
        }
        catch(Exception ex){
              return ResponseEntity
                      .status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body("Internal server error");
        }
    }

    @GetMapping("/{request_id}")
    public ResponseEntity<?> findSmsRequest(@PathVariable("request_id") int requestId)  {
        try{
            SmsRequest smsRequest = smsReqService.getSmsRequest(requestId);
            return ResponseEntity.ok(smsRequest);
        }
        catch(SmsRequestException exp){

            ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.toString(),exp.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(err);
        }

    }

    @GetMapping("/get_sms")
    public List<SmsElastic> searchSmsByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end,
            @RequestParam("status") String status) {

           long startEpoch = DateAndTimeConversion.convertToEpochMillis(start.toString());
           long endEpoch = DateAndTimeConversion.convertToEpochMillis(end.toString());

           return elasticSearchServiceImp.getSms(startEpoch,endEpoch,status);
    }

    @GetMapping("/search_sms")
    public Page<SmsElastic> searchMessages(
            @RequestParam String text,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return elasticSearchServiceImp.searchMessages(text, page, size);
    }




}
