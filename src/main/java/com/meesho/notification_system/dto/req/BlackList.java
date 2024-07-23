package com.meesho.notification_system.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class BlackList {
    private List<
            @NotNull
            @Pattern(regexp = "^\\+91\\d{10}$", message = "Phone number must start with +91 followed by 10 digits") String> phoneNumberList;
}
