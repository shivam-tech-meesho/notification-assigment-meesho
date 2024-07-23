package com.meesho.notification_system.expection;

public class KafkaProducerException extends Exception{
    public KafkaProducerException(String message){
        super(message);
    }
}
