package com.schedule.util;

import com.schedule.entity.Flow;
import com.schedule.entity.Plan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class CalculateUtil {

    public static void getPlan(List<Flow> flowList, int n1, int openNum, int n2,int closeNum, double k3, int n4, String[][] job){
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
                    stack.push(new Plan(time - n1, n1, f.getDate(), "开店准备工作"));
                }
            else
                for (int i = 0; i < openNum; i++) {
                    stack.push(new Plan(time - n1, n1, f.getDate(), "开店准备工作", job[0]));
                }







        }
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

    public static int f(double x){
        return (int) Math.ceil(x);
    }


}
