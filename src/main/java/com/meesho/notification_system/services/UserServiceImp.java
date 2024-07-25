package com.meesho.notification_system.services;

import com.meesho.notification_system.entities.User;
import com.meesho.notification_system.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void createUser(String phoneNumber) {
        User user = User.builder()  
                .phoneNumber(phoneNumber)
                .status(User.Status.WHITELIST)
                .build();
        userRepo.save(user);
    }

    @Override
    public boolean isUser(String phoneNumber) {
        return userRepo.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public void changeStatusToBlockList(List<String> numbers) {
        userRepo.updateStatusToBlacklist(numbers);
    }

    @Override
    public void changeStatusToWhiteList(List<String> numbers) {
        userRepo.updateStatusToWhitelist(numbers);
    }

    @Override
    public List<String> getAllBlackListNumbers() {
        return userRepo.findAllBlacklistedNumbers();
    }
}
