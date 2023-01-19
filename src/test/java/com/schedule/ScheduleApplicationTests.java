package com.schedule;

import com.schedule.entity.Staff;
import com.schedule.entity.Flow;
import com.schedule.mapper.StaffGroupMapper;
import com.schedule.service.FlowService;
import com.schedule.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.schedule.util.CalculateUtil.f;
import static com.schedule.util.GeneratorUtil.RandomGeneration;

@SpringBootTest
class ScheduleApplicationTests {

//    @Test
    void contextLoads() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxIiwiaWQiOiIxNjEzNDYxMDkyMTcwODY2Njg5Iiwic3ViIjoidXNlcm5hbWUiLCJleHAiOjE2NzQzMjA1ODEsImp0aSI6ImE1N2MyMDhkLTFiNTctNDg4MC05MGJiLTc4NzY3ZDQzMTEwNCJ9.g_F5QSciF3Pp3KcscFUl3uvnqkdin8XD263hCDc4p-8";
        Long userId = JwtUtil.getUserId(token);
        String username = JwtUtil.getUsername(token);
        System.out.println(userId);
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
        flowService.calculate(1L,"2000-01-02","2000-01-07");
    }


    @Test
    void test6(){
        System.out.println(f(0.00/23/5));
    }
}
