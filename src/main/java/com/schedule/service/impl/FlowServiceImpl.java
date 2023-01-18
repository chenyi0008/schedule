package com.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.*;
import com.schedule.mapper.FlowMapper;
import com.schedule.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {

    @Autowired
    RuleService ruleService;

    @Autowired
    StaffService staffService;

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    StoreService storeService;



    @Override
    public void calculate(Long storeId, String startDate, String endDate) {

        /**
         * 为算法做准备 将需要用到的数据全部查询出来
         */
        //根据日期获取flow的记录
        LambdaQueryWrapper<Flow> flowWrapper = new LambdaQueryWrapper<>();
        flowWrapper.eq(Flow::getStoreId,storeId)
                .ge(Flow::getDate,startDate)
                .le(Flow::getDate,endDate);
        List<Flow> flowList = this.list(flowWrapper);

        //根据商店id查询员工，再根据员工id查询偏好
        LambdaQueryWrapper<Staff> staffWrapper = new LambdaQueryWrapper<>();
        staffWrapper.eq(Staff::getStoreId,storeId);
        List<Staff> staffList = staffService.list(staffWrapper);
        List<Long> staffIds = new ArrayList<>();
        for (Staff staff : staffList) {
            staffIds.add(staff.getId());
        }
        LambdaQueryWrapper<Preference> preferenceWrapper = new LambdaQueryWrapper<>();
        preferenceWrapper.in(Preference::getStaffId,staffIds);
        List<Preference> preferenceList = preferenceService.list(preferenceWrapper);

        //对应偏好匹配对应员工
        List<Long> ll=new ArrayList<>();
        List<StaffWithPre> staffWithPreList =new ArrayList<>();
        for(Preference p:preferenceList){
            Long IdOfStaff=p.getStaffId();
            Staff s=staffService.getById(IdOfStaff);
            if(ll.contains(IdOfStaff)){
                continue;
            }
                ll.add(IdOfStaff);
                StaffWithPre staffWithPre = new StaffWithPre();
                BeanUtils.copyProperties(s,staffWithPre);
            LambdaQueryWrapper<Preference> lp=new LambdaQueryWrapper<>();
            lp.eq(Preference::getStaffId,IdOfStaff);
            List<Preference> preferenceList1 = preferenceService.list(lp);
            for(Preference pp:preferenceList1){
                if(pp.getPreferenceType().equals("工作日偏好")){
                    staffWithPre.setDayPre(pp);
                }else if(pp.getPreferenceType().equals("工作时间偏好")){
                    staffWithPre.setWorkTimePre(pp);
                }else{
                    staffWithPre.setShiftTimePre(pp);
                }
            }

            staffWithPreList.add(staffWithPre);
        }


        //根据商店id获取商店信息
        Store store = storeService.getById(storeId);

        //根据商店id获取商店规则
        LambdaQueryWrapper<Rule> ruleWrapper = new LambdaQueryWrapper<>();
        ruleWrapper.eq(Rule::getStoreId,storeId);
        List<Rule> ruleList = ruleService.list(ruleWrapper);



    }



}
