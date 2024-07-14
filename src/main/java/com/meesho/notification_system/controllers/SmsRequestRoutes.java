package com.meesho.notification_system.controllers;


import com.meesho.notification_system.dto.req.Sms;
import com.meesho.notification_system.dto.res.ErrorResponse;
import com.meesho.notification_system.dto.res.SmsReqSuccess;
import com.meesho.notification_system.dto.res.SuccessResponse;
import com.meesho.notification_system.entities.SmsRequest;
import com.meesho.notification_system.expection.SmsRequestExceptionHandler;
import com.meesho.notification_system.services.SmsReqService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/sms")
public class SmsRequestRoutes {

    @Autowired
    private SmsReqService smsReqService;

    @PostMapping("/send")
    public ResponseEntity<?> sendSmsRequest(@Valid @RequestBody Sms sms) {
        SmsRequest smsRequest = smsReqService.createSmsRequest(sms);
        SmsReqSuccess smsobj = new SmsReqSuccess(String.valueOf(smsRequest.getId()), "Successfully Sent");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse(smsobj));
    }

    @GetMapping("/{request_id}")
    public ResponseEntity<?> findSmsRequest(@PathVariable("request_id") int requestId)  {
        try{
            SmsRequest smsRequest = smsReqService.getSmsRequest(requestId);
            return ResponseEntity.ok(smsRequest);
        }
        catch(SmsRequestExceptionHandler exp){

            ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.toString(),exp.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(err);
        }

    }




}
