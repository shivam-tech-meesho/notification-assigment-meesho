package com.meesho.notification_system.repositories;

import com.meesho.notification_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
}
