package com.meesho.notification_system.services;

import java.util.List;

public interface UserService {
    void createUser(String phoneNumber);
    boolean isUser(String phoneNumber);
    void changeStatusToBlockList(List<String> numbers);
    void changeStatusToWhiteList(List<String> numbers);
    List<String> getAllBlackListNumbers();
}
