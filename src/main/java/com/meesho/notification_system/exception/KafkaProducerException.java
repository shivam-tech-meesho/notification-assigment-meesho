package com.meesho.notification_system.exception;

public class KafkaProducerException extends Exception{
    public KafkaProducerException(String message){
        super(message);
    }
}
