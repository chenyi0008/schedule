package com.schedule.util;

import com.schedule.entity.Flow;
import com.schedule.entity.Plan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class CalculateUtil {

    public static List<List<Plan>> getPlan(List<Flow> flowList, int n1, int openNum, int n2,int closeNum, double k3, int n4, String[][] job){
        List<List<Plan>> res = new ArrayList<>();
        Flow flow = flowList.get(0);
        int week = getWeekday(flow.getDate());

        for (Flow f : flowList) {
            int time = 9;
            if(week == 0 || week == 6)time = 10;
            double[] arr = f.getArr();

            int[] staffNum = new int[24];
            //计算每段时间需要的员工数
            for (int i = 0; i < arr.length / 2; i++) {
                staffNum[i] = f(( arr[ 2 * i ] + arr[ 2 * i + 1 ] ) / 2 / k3);
                if(staffNum[i] == 0) staffNum[i] = n4;
            }

            Stack<Plan> stack = new Stack<>();

            //开店准备工作加入栈
            if(job == null)
                for (int i = 0; i < openNum; i++) {
                    System.out.println("job = null");
                    stack.push(new Plan(time - n1, Math.max(n1, 2), f.getDate(), "准备工作", week));
                }
            else
                for (int i = 0; i < openNum; i++) {
                    System.out.println("job != null");
                    stack.push(new Plan(time - n1, Math.max(n1, 2), f.getDate(), "准备工作", job[0], week));
                }

            //对值班环节进行压栈
            for (int i = time; i < time + 12; i++) {
                int classes = 0;
                int index = stack.size() - 1;
                //延长已有的班次
                while (classes < staffNum[i] && index >= 0){

                    Plan plan = stack.elementAt(index);
                    int workTime = i - plan.getStartTime() + 1;
                    if(workTime == 4 && plan.getWorkTime() == 2)break;
                    if(workTime <= 4){
                        classes ++;
                        plan.setWorkTime(workTime);
                    }else{
                        break;
                    }
                    index --;
                }

                //添加班次
                while (classes < staffNum[i]){
                    Plan plan;
                    if(job != null) plan = new Plan(i, 2, f.getDate(), "值班工作", job[1], week);
                    else plan = new Plan(i, 2, f.getDate(), "值班工作", week);
                    stack.push(plan);
                    classes ++;
                }
            }

            //延长已有的班次
            int classes = 0;
            int index = stack.size() - 1;
            int closeTime = time + 12;
            while (classes < closeNum && index >= 0){

                Plan plan = stack.elementAt(index);
                int workTime = closeTime - plan.getStartTime() + 1;
                if(workTime == 4 && plan.getWorkTime() == 2)break;
                if(workTime + n2 <= 4){
                    classes ++;
                    plan.setWorkType("收尾工作");
                    plan.setWorkTime(workTime);
                }else{
                    break;
                }
                index --;
            }
            while (classes < closeNum){
                Plan plan;
                if(job != null) plan = new Plan(closeTime, Math.max(2, n2), f.getDate(), "收尾工作", job[2], week);
                else plan = new Plan(closeTime, Math.max(2, n2), f.getDate(), "收尾工作", week);
                classes ++;
                stack.push(plan);
            }

            List<Plan> list = new ArrayList<>(stack);
            res.add(list);
            week = (week + 1) % 7;
        }
        return res;
    }

    public static int getWeekday(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        Date date = null;
        try{
            date = f.parse(datetime);
        }catch (ParseException e){
            e.printStackTrace();
        }
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(week < 0) week = 0;
        return week;

    }

    public static int dateDifference(String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        Period period = Period.between(startDate, endDate);
        int days = period.getDays();
        int totalDays = days + (period.getYears() * 365) + (period.getMonths() * 30);
        return totalDays;
    }


    public static int f(double x){
        return (int) Math.ceil(x);
    }


}
