package com.meesho.notification_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class NotificationSystemApplicationTests {

    Calculator cal = new Calculator();

    @Test
    void itShouldAddNumber() {
        //input
        int a = 10;
        int b = 20;



        //when
        int result = cal.add(a,b);

        //then
        int expected = 30;
        assertThat(result).isEqualTo(expected);
    }

    class Calculator{
        int add(int a,int b) {
            return a+b;
        }
    }

}
