package com.meesho.notification_system.repositories;

import com.meesho.notification_system.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
        boolean existsByPhoneNumber(String phoneNumber);

        @Modifying
        @Transactional
        @Query("UPDATE User u SET u.status = 'BLACKLIST' WHERE u.phoneNumber IN :numbers")
        void updateStatusToBlacklist(List<String> numbers);

        @Modifying
        @Transactional
        @Query("UPDATE User u SET u.status = 'WHITELIST' WHERE u.phoneNumber IN :numbers")
        void updateStatusToWhitelist(List<String> numbers);

        @Query("SELECT u.phoneNumber FROM User u WHERE u.status = 'BLACKLIST'")
        List<String> findAllBlacklistedNumbers();


        User findByPhoneNumber(String phoneNumber);
}
