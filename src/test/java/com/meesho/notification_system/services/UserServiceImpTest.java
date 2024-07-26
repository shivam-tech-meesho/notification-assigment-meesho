package com.meesho.notification_system.services;

import com.meesho.notification_system.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    void UserServiceImp_createUser_PersistUser() {
        String phoneNumber = "+911234567890";
        userService.createUser(phoneNumber);

        // because we are not returning anything from the method, so we are only care about if method is called or not , so we use verify
        verify(userRepo, times(1)).save(any());
    }

    @Test
    void UserRepo_existsByPhoneNumber_ReturnBoolean() {
        String phoneNumber = "+911234567890";
        Mockito.when(userRepo.existsByPhoneNumber(phoneNumber)).thenReturn(true);
        assertTrue(userService.isUser(phoneNumber));
    }

    @Test
    void UserServiceImp_changeStatusToBlockList_UpdateStatus() {
        List<String> numbers = List.of("+911234567890");
        userService.changeStatusToBlockList(numbers);
        verify(userRepo, times(1)).updateStatusToBlacklist(numbers);
    }

    @Test
    void UserServiceImp_changeStatusToWhiteList_UpdateStatus() {
        List<String> numbers = List.of("+911234567890");
        userService.changeStatusToWhiteList(numbers);
        verify(userRepo, times(1)).updateStatusToWhitelist(numbers);
    }

    @Test
    void UserRepo_getAllBlackListNumbers_ReturnList() {
        List<String> expectedNumbers = List.of("+911234567890");
        Mockito.when(userRepo.findAllBlacklistedNumbers()).thenReturn(expectedNumbers);
        assertEquals(expectedNumbers, userService.getAllBlackListNumbers());
    }
}
