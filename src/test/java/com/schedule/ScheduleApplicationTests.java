package com.schedule;

import com.schedule.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleApplicationTests {

    @Test
    void contextLoads() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxIiwiaWQiOiIxNjEzNDYxMDkyMTcwODY2Njg5Iiwic3ViIjoidXNlcm5hbWUiLCJleHAiOjE2NzQzMjA1ODEsImp0aSI6ImE1N2MyMDhkLTFiNTctNDg4MC05MGJiLTc4NzY3ZDQzMTEwNCJ9.g_F5QSciF3Pp3KcscFUl3uvnqkdin8XD263hCDc4p-8";
        Long userId = JwtUtil.getUserId(token);
        String username = JwtUtil.getUsername(token);
        System.out.println(userId);
    }

    @Test
    void test1(){
        System.out.println(Long.valueOf("1613461092170866689"));
    }

}
