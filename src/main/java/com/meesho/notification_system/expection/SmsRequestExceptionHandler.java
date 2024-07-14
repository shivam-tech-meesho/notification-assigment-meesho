package com.meesho.notification_system.expection;

public class SmsRequestExceptionHandler extends Exception {
    public SmsRequestExceptionHandler(String msg)
    {
        super(msg);
    }
}
