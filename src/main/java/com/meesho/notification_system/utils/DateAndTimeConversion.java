package com.meesho.notification_system.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateAndTimeConversion {
    public static long convertToEpochMillis(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }
}
