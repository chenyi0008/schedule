package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.common.CustomException;
import com.schedule.common.R;
import com.schedule.entity.User;
import com.schedule.service.SendMailService;
import com.schedule.service.UserService;
import com.schedule.util.CodeUitl;
import com.schedule.util.JwtUtil;
import com.schedule.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import javax.servlet.http.HttpSession;

/**
 * user
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user
     * @return
     */
    @PutMapping
    public R<String> register(@RequestBody User user){

        //判断账号是否已存在
        String username = user.getUsername();
        if(username == null || user.getPassword() == null)throw new CustomException("账号和密码不能为空");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        int count = userService.count(queryWrapper);
        if(count != 0)return R.error("账号已存在");

        //base64加密
        String password = user.getPassword();
        String password_base64 = CodeUitl.encodeToString(password);
        user.setPassword(password_base64);
        userService.save(user);

        return R.msg("注册成功");
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping
    public R<String> login(@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        String password = CodeUitl.encodeToString(user.getPassword());

        queryWrapper.eq(User::getUsername,user.getUsername());
        queryWrapper.eq(User::getPassword,password);
        User one = userService.getOne(queryWrapper);
        if(one != null) {
            String token = JwtUtil.createToken(user.getUsername(),one.getId());
            return R.success(token,"登录成功");
        }
        else return R.error("账号或密码输入有误");
    }


    /**
     * 注册(需要邮箱验证码）
     * @param user
     * @return
     */
    @PutMapping("/email")
    public R<String> registerByCode(@RequestBody User user, @RequestParam String code, HttpSession session){

        //判断账号是否已存在
        String username = user.getUsername();
        Object codeInSession = session.getAttribute(username);
        if(codeInSession == null || !codeInSession.equals(code))
        return R.msg("验证码有误");


        if(username == null || user.getPassword() == null)throw new CustomException("账号和密码不能为空");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        int count = userService.count(queryWrapper);
        if(count != 0)return R.error("账号已存在");

        //base64加密
        String password = user.getPassword();
        String password_base64 = CodeUitl.encodeToString(password);
        user.setPassword(password_base64);
        userService.save(user);

        return R.msg("注册成功");
    }


    @Autowired
    SendMailService sendMailService;

    /**
     * 发送邮箱验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取邮箱号码
        String username = user.getUsername();

        if(StringUtils.isNotEmpty(username)){
            //生成随机的六位验证码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            //发送邮件
            log.info("验证码：{}",code.toString());
            sendMailService.sendMail(user.getUsername(), code);
            session.setAttribute(username,code.toString());
            return R.success("验证码发送成功");
        }
        return R.error("验证码发送失败");
    }

    @GetMapping("/info")
    public R<String> loginInfo(){
        return R.error("用户未登录");
    }

}
