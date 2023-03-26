package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.common.CustomException;
import com.schedule.common.R;
import com.schedule.entity.Rule;
import com.schedule.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

     import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * rule
 * @author akuya
 * @create 2023-01-11-15:06
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    public static String getExceptionSrintStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 添加店铺规则
     * @param rule
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Rule rule){
        //如果有相同类型的规则，先删除，再添加
        if(rule.getRuleType() == null || rule.getValue() == null || rule.getStoreId() == null
         || rule.getRuleType() == "" || rule.getValue() == "")
            throw new CustomException("参数不符合要求");

        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getStoreId,rule.getStoreId());
        queryWrapper.eq(Rule::getRuleType,rule.getRuleType());
        String value = rule.getValue();

        try{
            double n1,k1,n2,j2,k2,k3,n4;

            String ruleType=rule.getRuleType();
            switch (ruleType){
                case "开店规则" : double[] farr =rule.getArr(); n1=farr[0]; k1=farr[1]; break;
                case "关店规则" : double[] sarr =rule.getArr(); n2=sarr[0]; j2=sarr[1];k2=sarr[2]; break;
                case "客流规则" : double[] tarr =rule.getArr(); k3=tarr[0];  break;
                case "值班规则" : double[] foarr =rule.getArr(); n4=foarr[0]; break;
                case "职位规则" : String [] fiarr=rule.getValue().split("|");
                    String[][] ffarr = new String[3][];
                    ffarr[0]=fiarr[0].split(",");
                    ffarr[1]=fiarr[1].split(",");
                    ffarr[2]=fiarr[2].split(",");
                    break;
                case "休息规则" : Integer.parseInt(rule.getValue());break;
                default:throw new CustomException("不含有此类型的规则");
            }
        }catch (Exception e){
            e.printStackTrace();

            throw new CustomException("数据存在错误：" + e.getMessage());


        }

        ruleService.remove(queryWrapper);
        ruleService.save(rule);
        return R.msg("添加成功");
    }

    /**
     * 更新店铺规则数据
     * @param rule
     */
    @PutMapping
    public R<String> update(@RequestBody Rule rule) {
        ruleService.updateById(rule);
        return R.msg("更新成功");
    }

    /**
     * 删除店铺规则
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        ruleService.removeByIds(ids);
        return R.msg("删除成功");
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Rule> get(@PathVariable Long id) {

        Rule rule = ruleService.getById(id);

        return R.success(rule);
    }

    /**
     * 获取店铺规则所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<Rule>> getAll(HttpServletRequest request, HttpServletResponse response){
        System.out.println("请求头：");
        Enumeration<String> headerNames1 = request.getHeaderNames();
        while (headerNames1.hasMoreElements()) {
            String headerName = headerNames1.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        System.out.println("响应头：");
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            System.out.println(headerName + ": " + response.getHeader(headerName));
        }
        List<Rule> list = ruleService.list();
        return R.success(list);
    }

    /**
     * 根据商店id获取数据
     * @param storeId
     * @return
     */
    @GetMapping
    public R<List<Rule>> getRuleByStoreId(Long storeId){
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(storeId != null,Rule::getStoreId,storeId);
        List<Rule> list = ruleService.list(queryWrapper);
        return R.success(list);
    }







}
