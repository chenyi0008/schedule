package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.common.CustomException;
import com.schedule.common.R;
import com.schedule.entity.User;
import com.schedule.service.UserService;
import com.schedule.util.CodeUitl;
import com.schedule.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * user
 */
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

    @GetMapping("/info")
    public R<String> loginInfo(){
        return R.error("用户未登录");
    }

}
