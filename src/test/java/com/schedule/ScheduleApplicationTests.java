package com.schedule;

import com.schedule.common.R;
import com.schedule.controller.FlowController;
import com.schedule.entity.Staff;
import com.schedule.entity.Flow;
import com.schedule.mapper.PlanMapper;
import com.schedule.mapper.StaffGroupMapper;
import com.schedule.mapper.StaffMapper;
import com.schedule.service.FlowService;
import com.schedule.service.StaffService;
import com.schedule.util.Information;
import com.schedule.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Scanner;

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
//        double[] arr = flow.fGetArr();
//        for (int i = 0; i < 900; i++) {
//            arr = flow.fGetArr();
//        }
//
//        for (double i : arr) {
//            System.out.println(i);
//        }
//    }


    @Autowired
    FlowService flowService;
    @Test
    void test4(){
        List<Flow> flows = RandomGeneration("2023-04-14", 30, 1645395272186007554L);
        for (Flow flow : flows) {
            System.out.println(flow.getValue());
        }
//        flowService.saveBatch(flows);

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
//        Stack<String> stack = new Stack<>();
//        stack.push("a");
//        stack.push("b");
//        stack.push("c");
//
//        for (int i = 0; i < 3; i++) {
//            System.out.println(stack.elementAt(i));
//        }
//
//        stack.push("d");
//        for (int i = 0; i < 4; i++) {
//            System.out.println(stack.size());
//            System.out.println(stack.elementAt(i));
//        }
        for (int i = 1; i <= 1; i++) {
            System.out.println(999);
        }

    }

    @Autowired
    FlowController flowController;

    @Test
    void test8(){
        R<List<Flow>> list = flowController.list(1L, "2023-02-01", "2023-03-01");
        System.out.println(list);
    }

    @Autowired
    StaffMapper staffMapper;

    @Autowired
    StaffService staffService;

    @Test
    void test9(){
//        List<Staff> groupDate = staffMapper.getAllDate(1L, "小",0,5);
//        for (Staff s : groupDate) {
//            System.out.println(s);
//        }
        List<Staff> list = staffService.getListByCondition("小", 1L, 1, 4);
        for (Staff staff : list) {
            System.out.println(staff);
        }

        Scanner sc = new Scanner(System.in);
    }

    @Test
    void test(){

            int n;
            Scanner sc = new Scanner(System.in);
            n = sc.nextInt();
            int[] a = new int[n];

            for(int i = 0;i < n ; i++){
                a[i] = sc.nextInt();
            }

            System.out.print("N=" + a);



            /********* End *********/
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    void redisTest(){



        redisTemplate.opsForValue().set("chenyi","sdfdsa");


    }


    @Autowired
    PlanMapper planMapper;

    @Test
    void test11(){
        List<Information> information = planMapper.getInformation(1L);
        for (Information imformation : information) {
            System.out.println(imformation);
        }
    }



}
