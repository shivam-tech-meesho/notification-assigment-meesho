package com.meesho.notification_system.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Sms {

    @NotNull
    @Pattern(regexp = "^\\+91\\d{10}$", message = "Phone number must start with +91 followed by 10 digits")
    private String phoneNumber;

    @NotNull
    @NotEmpty(message = "Message should not be empty")
    private String message;
}
