package com.meesho.notification_system.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class BlackList {
    private List<String> phoneNumberList;
}
