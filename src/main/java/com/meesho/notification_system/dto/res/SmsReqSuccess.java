package com.meesho.notification_system.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsReqSuccess {
      private String requestId;
      private String comments;
}
