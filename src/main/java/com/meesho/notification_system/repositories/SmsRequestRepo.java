package com.meesho.notification_system.repositories;


import com.meesho.notification_system.entities.SmsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRequestRepo extends JpaRepository<SmsRequest, Integer> {
     SmsRequest findById(int request_id);
}
