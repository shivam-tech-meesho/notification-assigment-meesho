package com.meesho.notification_system.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class RedisPipelineLatencyTest {

    @Autowired
    private BlackListServiceImp blackListService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private List<String> numbers;

    @BeforeEach
    public void setUp() {
        // Initialize the list of numbers to be added to the blocklist
        numbers = generatePhoneNumbers(1000); // Generate 1000 unique phone numbers
    }

    // Generate a list of unique random phone numbers
    private List<String> generatePhoneNumbers(int count) {
        List<String> generatedNumbers = new ArrayList<>();
        Random random = new Random();
        while (generatedNumbers.size() < count) {
            StringBuilder phoneNumber = new StringBuilder();
            phoneNumber.append("+91");
            for (int i = 0; i < 10; i++) {
                phoneNumber.append(random.nextInt(10));
            }
            generatedNumbers.add(phoneNumber.toString());
        }
        return generatedNumbers;
    }

    @Test
    public void testAddToBlockListWithPipelining() {
        long startTime = System.nanoTime();
        blackListService.addToBlockList(numbers);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000; // Convert to microseconds
        System.out.println("Pipelined insertion time: " + duration + " microseconds");
    }

    @Test
    public void testAddToBlockListWithoutPipelining() {
        long startTime = System.nanoTime();
        blackListService.addToBlockListWithoutPipelining(numbers);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000; // Convert to microseconds
        System.out.println("Non-pipelined insertion time: " + duration + " microseconds");
    }
}
