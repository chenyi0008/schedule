package com.schedule.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.schedule.common.R;
import com.schedule.entity.Flow;
import com.schedule.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author akuya
 * @create 2023-04-11-16:32
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private FlowService flowservice;

    @PostMapping("/uploadCSV")
    public R<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {

        try {
            // 读取CSV文件数据
            InputStream inputStream = file.getInputStream();
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            String[] line;
            while ((line = reader.readNext()) != null) {
                String tdata = null;
                String sid = null;
                String value = null;
                if(line.length >= 1) {
                    tdata = line[0];
                    String temp[]=tdata.split("/");
                    StringBuffer b=new StringBuffer();
                    for(int i=0;i<temp.length;i++){
                        if(i==0){
                            b.append(temp[i]);
                            b.append("-");
                        }
                        if(i==1){
                            if(Integer.parseInt(temp[i])<10){
                                b.append("0");
                                b.append(temp[i]);
                                b.append("-");
                            }
                        }
                        if(i==2){
                            if(Integer.parseInt(temp[i])<10){
                                b.append("0");
                                b.append(temp[i]);
                            }
                        }
                    }
                    tdata=b.toString();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date td = formatter.parse(tdata);
                    tdata=td.toString();
                }
                if(line.length >= 2) {
                    sid = line[1];
                }
                if(line.length >= 3) {
                    value = line[2];
                }
                // 创建flow实体对象，将读取的数据分别赋值给flow对象的三个属性
                Flow flow=new Flow();
                if(!tdata.equals(null)) flow.setDate(tdata);
                if(!sid.equals(null))flow.setStoreId((long)Integer.parseInt(sid));
                if(!value.equals(null))flow.setValue(value);
                flowservice.save(flow);
            }
            reader.close();
            return R.msg("成功上传数据");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("上传数据失败");
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return R.error("上传数据失败");
    }



}
