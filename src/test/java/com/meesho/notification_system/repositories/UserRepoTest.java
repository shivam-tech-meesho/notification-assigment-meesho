package com.meesho.notification_system.repositories;

import com.meesho.notification_system.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepoTest {


    @Autowired
    private UserRepo userRepo;

    @Test
    void UserRepo_existsByPhoneNumber_ReturnBoolean() {
        User user = User.builder()
                .phoneNumber("+910000000000")
                .status(User.Status.WHITELIST)
                .build();

        userRepo.save(user);

        boolean result = userRepo.existsByPhoneNumber("+910000000000");

        assertThat(result).isTrue();
    }

    @Test
    void UserRepo_updateStatusToBlacklist() {

        User user_1 = User.builder()
                .phoneNumber("+910000000001")
                .status(User.Status.WHITELIST)
                .build();

        User user_2 = User.builder()
                .phoneNumber("+910000000002")
                .status(User.Status.WHITELIST)
                .build();

        userRepo.save(user_1);
        userRepo.save(user_2);

        userRepo.updateStatusToBlacklist(List.of("+910000000001", "+910000000002"));

        User user1 = userRepo.findByPhoneNumber("+910000000001");
        User user2 = userRepo.findByPhoneNumber("+910000000002");

        assertThat(user1.getStatus()).isEqualTo(User.Status.BLACKLIST);
        assertThat(user2.getStatus()).isEqualTo(User.Status.BLACKLIST);

    }

    @Test
    void UserRepo_updateStatusToWhitelist() {
        User user_1 = User.builder()
                .phoneNumber("+910000000003")
                .status(User.Status.BLACKLIST)
                .build();

        User user_2 = User.builder()
                .phoneNumber("+910000000004")
                .status(User.Status.BLACKLIST)
                .build();

        userRepo.save(user_1);
        userRepo.save(user_2);

        userRepo.updateStatusToWhitelist(List.of("+910000000003", "+910000000004"));

        User user1 = userRepo.findByPhoneNumber("+910000000003");
        User user2 = userRepo.findByPhoneNumber("+910000000004");

        assertThat(user1.getStatus()).isEqualTo(User.Status.WHITELIST);
        assertThat(user2.getStatus()).isEqualTo(User.Status.WHITELIST);

    }

    @Test
    void UserRepo_findAllBlacklistedNumbers_ReturnListOfString() {

        List<String> result_before = userRepo.findAllBlacklistedNumbers();

        User user_1 = User.builder()
                .phoneNumber("+918619674565")
                .status(User.Status.BLACKLIST)
                .build();

        User user_2 = User.builder()
                .phoneNumber("+918619674566")
                .status(User.Status.BLACKLIST)
                .build();


        userRepo.save(user_1);

        userRepo.save(user_2);

        List<String> result_after = userRepo.findAllBlacklistedNumbers();

        assertThat(result_after.size()).isEqualTo(2+result_before.size());
    }
}