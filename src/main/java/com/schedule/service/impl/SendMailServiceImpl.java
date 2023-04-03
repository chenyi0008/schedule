package com.schedule.service.impl;

import com.schedule.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, Integer code) {
        //发送人
        String from = "chenyikeji0000@163.com";
        //标题
        String subject = "验证码";
        //正文
        String context = "您现在正在进行注册验证，您的验证码为：" + code + ",请勿泄露于他人！";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from+"(智能排班系统)");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(context);
        javaMailSender.send(message);
    }
}
