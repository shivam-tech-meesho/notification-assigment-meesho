package com.meesho.notification_system.services;

import com.meesho.notification_system.entities.User;
import com.meesho.notification_system.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public void createUser(String phoneNumber) {
        try {
            User user = User.builder()
                    .phoneNumber(phoneNumber)
                    .status(User.Status.WHITELIST)
                    .build();
            userRepo.save(user);
        } catch (Exception e) {
            System.out.println("Error in creating user" + e.getMessage());
        }
    }

    @Override
    public boolean isUser(String phoneNumber) {
        try {
            return userRepo.existsByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            System.out.println("Error in checking user" + e.getMessage());
            return false;
        }
    }

    @Override
    public void changeStatusToBlockList(List<String> numbers) {
        try {
            userRepo.updateStatusToBlacklist(numbers);
        } catch (Exception e) {
            System.out.println("Error in changing status to blacklist" + e.getMessage());
        }
    }

    @Override
    public void changeStatusToWhiteList(List<String> numbers) {
        try {
            userRepo.updateStatusToWhitelist(numbers);
        } catch (Exception e) {
            System.out.println("Error in changing status to whitelist" + e.getMessage());
        }
    }

    @Override
    public List<String> getAllBlackListNumbers() {
        try {
            return userRepo.findAllBlacklistedNumbers();
        } catch (Exception e) {
            System.out.println("Error in getting all black list numbers" + e.getMessage());
            return null;
        }
    }


}
