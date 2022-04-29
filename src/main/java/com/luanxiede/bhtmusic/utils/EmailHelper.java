package com.luanxiede.bhtmusic.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author passer-by
 * @date 2022/4/29
 */
@Component
public class EmailHelper {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate redisTemplate;


    //发送验证码
    public int sendVerCode(String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        String verCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));   //随机数生成6位验证码
        // 存入redis缓存，设置五分钟的超时时间
        redisTemplate.opsForValue().set(email, verCode, 5, TimeUnit.MINUTES);
        message.setFrom("2798119246@qq.com");
        message.setTo(email);
        message.setSubject("bht音乐");
        message.setText("【bht音乐】您的验证码为：" + verCode + "，有效时间为5分钟(若不是本人操作，可忽略该条邮件)");
        try {
            mailSender.send(message);
            return 1;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    //校验邮箱格式是否正确
    public boolean checkEmail(String regex, String email) {
        if (regex != "" || email != "") {
            return Pattern.matches(regex, email);
        } else
            return false;
    }

}
