package com.schedule.util;

import com.schedule.entity.Flow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalculateUtil {

    public static void getPlan(List<Flow> flowList){
        Flow flow = flowList.get(0);
        int week = getWeekday(flow.getDate());


        for (Flow f : flowList) {
            boolean flag = true;
            if(week == 0 || week == 6)flag = false;
            double[] arr = f.getArr();
            int[] passengerFlow = new int[24];
            for (int i = 0; i < arr.length / 2; i++) {
                passengerFlow[i] = (int) Math.ceil(( arr[ 2 * i ] + arr[ 2 * i + 1 ] ) / 2);
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


}
