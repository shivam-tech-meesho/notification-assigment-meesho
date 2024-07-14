package com.meesho.notification_system.dto.res;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class ErrorResponse {
    private Map<String, String> error;

    public ErrorResponse(String code, String message) {
        this.error = new HashMap<>();
        this.error.put("code", code);
        this.error.put("message", message);
    }

}
