package com.meesho.notification_system.dto.res;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor

public class SuccessResponse {
    private Object data;
    public SuccessResponse(Object data){
        this.data = data;
    }
}
