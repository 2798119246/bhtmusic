package com.luanxiede.bhtmusic;

import com.luanxiede.bhtmusic.utils.EmailHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

class BhtmusicApplicationTests {
    @Autowired
    EmailHelper emailHelper;

    @Autowired
    private RedisTemplate redisTemplate;

    private static String REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    @Test
    void contextLoads() {
        System.out.println("dsas");
    }

    @Test
    void sss(){
        String email = "wangdey2018@lzu.edu.cn";
        if ((email != null) && (!email.isEmpty())) {
            System.out.println(
                    Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", email)
            );
        }
    }

    @Test
    void rt(){
        String email = "wangdy2018@lzu.edu.cn";
        System.out.println(emailHelper.checkEmail(REGEX,email));
    }

    @Test
    void redisTest(){
        String email = "123";
        String code = "133";
        redisTemplate.opsForValue().set(email,133,60, TimeUnit.SECONDS);
        System.out.println("存入成功");
    }

    @Test
    void getTest(){
        String email = "2798119246@qq.com";
        System.out.println(redisTemplate.opsForValue().get(email));
    }
    @Test
    void dd(){

        String code = String.valueOf((int)((Math.random()*9+1)*100000));
        System.out.println(code);
    }

}
