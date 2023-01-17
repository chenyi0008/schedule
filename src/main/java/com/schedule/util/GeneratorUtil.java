package com.schedule.util;

import com.schedule.entity.Flow;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class GeneratorUtil {
    /**
     * 如果flag为true说明时间是周一-周五，如果为false说明时间是周末
     * @param flag
     * @return
     */
    public static String generator(boolean flag){

        double[] value;
        if(flag == true)value = new double[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0.1,1.3,5.7,11.1,13.4,13.3,17.3,18.1,22.8,26.9,21.6,18.3,17.2,15.3,14.3,11.6,8.3,8.3,7.2,5.6,5.6,2.5,2.1,0.1,0.1,0,0,0,0,0,0};
        else value = new double[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0.1,1.3,5.7,11.1,13.4,13.3,17.3,18.1,22.8,26.9,21.6,18.3,17.2,15.3,14.3,11.6,8.3,8.3,7.2,5.6,5.6,2.5,2.1,0.1,0.1,0,0,0,0};

        for (int i = 0; i < value.length; i++) {
            double randomNum = Math.random() * 0.4 + 0.8;
            value[i] *= randomNum * 10;
            value[i] = Math.round(value[i]) /10.0;
        }
        return Arrays.stream(value).mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    public static List<Flow> RandomGeneration(String datetime,int Cdays,Long storeId){

        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        Date datet=null;
        try{
            datet=f.parse(datetime);
            cal.setTime(datet);
        }catch (ParseException e){
            e.printStackTrace();
        }

        int week=cal.get(Calendar.DAY_OF_WEEK)-1;
        if(week<0)week=0;

        List<Flow> list=new ArrayList<>();

        for(int i=0;i<Cdays;i++){
            Flow flow=new Flow();
            flow.setStoreId(storeId);
            String temp;
            if(week==0||week==7){  //{天，一，二，三，四，五，六}
                 temp=generator(false);
            }else{
                 temp=generator(true);
            }
            flow.setValue(temp);
            flow.setDate(datetime);
            Date tdate=cal.getTime();
            String ss=tdate.toString();
            flow.setDate(ss);
            list.add(flow);
            week=(week+1)%7;
            cal.add(Calendar.DAY_OF_MONTH,1);
        }

        return list;
    }



    public static void main(String[] args) {
        String generator = generator(true);
        System.out.println(generator);
    }
}
