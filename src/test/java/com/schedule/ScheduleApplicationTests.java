package com.schedule;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.schedule.entity.Staff;
import com.schedule.entity.Flow;
import com.schedule.mapper.StaffGroupMapper;
import com.schedule.service.FlowService;
import com.schedule.util.JwtUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static com.schedule.util.CalculateUtil.f;
import static com.schedule.util.GeneratorUtil.RandomGeneration;

@SpringBootTest
class ScheduleApplicationTests {

    @Test
    void contextLoads() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWQiOiIxMjMiLCJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTY3NTE2MTIwOCwianRpIjoiYTMxNzUzOGEtYTI0OS00MmVkLTg0ZWYtNjVlYjQ1YjI3Zjc0In0.kfgXjjeOl4sJaSwrycLQNQ5kqQoVC66cwDL43u2l95s";
//        Long userId = JwtUtil.getUserId(token);
        String username = JwtUtil.getUsername(token);
//        System.out.println(userId);
        System.out.println(username);
    }

    @Test
    void test0(){
        String token = JwtUtil.createToken("admin",123L);
        System.out.println(token);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWQiOiIxMjMiLCJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTY3NTE2MTIwOCwianRpIjoiYTMxNzUzOGEtYTI0OS00MmVkLTg0ZWYtNjVlYjQ1YjI3Zjc0In0.kfgXjjeOl4sJaSwrycLQNQ5kqQoVC66cwDL43u2l95s";
        String username = JwtUtil.getUsername(token);
        System.out.println(username);
    }

//    @Test
    void test1(){
        System.out.println(Long.valueOf("1613461092170866689"));
    }

    @Autowired
    StaffGroupMapper staffGroupMapper;

//    @Test
    void test2(){
        List<Staff> listByGroupId = staffGroupMapper.getListByGroupId(1614920848823713793L);
        System.out.println(listByGroupId);
    }

//    @Test
//    void test3(){
//        Flow flow = new Flow();
//        flow.setValue("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30");
//        double[] arr = flow.getArr();
//        for (int i = 0; i < 900; i++) {
//            arr = flow.getArr();
//        }
//
//        for (double i : arr) {
//            System.out.println(i);
//        }
//    }


    @Autowired
    FlowService flowService;
//    @Test
    void test4(){
        List<Flow> flows = RandomGeneration("2000-01-01", 30, 1L);
        flowService.saveBatch(flows);

    }

    @Test
    void test5(){
        flowService.calculate(1L,"2000-01-01","2000-01-30");
    }

    @Test
    void test7(){
//        flowService.calculate();
    }

    @Test
    void test6(){
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");

        for (int i = 0; i < 3; i++) {
            System.out.println(stack.elementAt(i));
        }

        stack.push("d");
        for (int i = 0; i < 4; i++) {
            System.out.println(stack.size());
            System.out.println(stack.elementAt(i));
        }

    }

    @Test
    void test8(){
        boolean[] arr = new boolean[5];
        for (Boolean aBoolean : arr) {
            System.out.println(aBoolean);
        }
    }
}
