package com.schedule.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.schedule.common.BaseContext;
import com.schedule.common.R;
import com.schedule.entity.Flow;
import com.schedule.entity.Store;
import com.schedule.service.FlowService;
import com.schedule.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author akuya
 * @create 2023-04-11-16:32
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private FlowService flowservice;

    public static String convertDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/M/d");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(dateString);
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Autowired
    StoreService storeService;

    @PostMapping("/uploadCSV")
    public R<String> uploadCSVFile(@RequestParam("file") MultipartFile file, @RequestParam  Long storeId) {
        List<Flow> flowList=new ArrayList<>();
        Long userId = BaseContext.getUserId();
        LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Store::getUserId,userId);
        List<Store> list = storeService.list(wrapper);
        boolean flag = false;
        for (Store store : list) {

            System.out.println("---------------");
            System.out.println(store.getId());
            System.out.println(storeId);
            if(store.getId().equals(storeId) ){
                flag = true;
                break;
            }
        }
        if(!flag)return R.error("权限不足");


        try {
            // 读取CSV文件数据
            InputStream inputStream = file.getInputStream();
            CSVReader reader =  new CSVReader(new InputStreamReader(inputStream));


            String[] line;
            flag = false;
            while ((line = reader.readNext()) != null) {
                String tdata = null;
                String value = null;
                if(line.length >= 2) {
                    tdata = line[0];
                    if(flag){
                        tdata = convertDate(tdata);

                    }else{
                        tdata = convertDate(tdata.substring(1));
                        flag = true;
                    }

                    value = line[1];
                    String[] split = value.split(",");
                    if(split.length != 48)return R.error("value的值不符合要求");
                }


                // 创建flow实体对象，将读取的数据分别赋值给flow对象的三个属性
                Flow flow=new Flow();

                if(!tdata.equals(null)) flow.setDate(tdata);
                if(!storeId.equals(null))flow.setStoreId(storeId);
                if(!value.equals(null))flow.setValue(value);
                flow.setStoreId(storeId);
                flowList.add(flow);
            }

            Flow f1 = flowList.get(0);
            String startDate = f1.getDate();
            Flow f2 = flowList.get(flowList.size() - 1);
            String endDate = f2.getDate();
            LambdaQueryWrapper<Flow> removeWrapper = new LambdaQueryWrapper<>();
            removeWrapper.ge(Flow::getDate, startDate).le(Flow::getDate, endDate).eq(Flow::getStoreId, storeId);
            flowservice.remove(removeWrapper);

            flowservice.saveBatch(flowList);

            reader.close();






            return R.msg("成功上传数据");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("上传数据失败");
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }


        return R.error("上传数据失败");
    }



}
