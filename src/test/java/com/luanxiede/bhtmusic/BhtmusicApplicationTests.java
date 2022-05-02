package com.luanxiede.bhtmusic;

import com.luanxiede.bhtmusic.domain.Consumer;
import com.luanxiede.bhtmusic.domain.History;
import com.luanxiede.bhtmusic.service.HistoryService;
import com.luanxiede.bhtmusic.service.impl.ConsumerServiceImpl;
import com.luanxiede.bhtmusic.utils.BizIdHelper;
import com.luanxiede.bhtmusic.utils.EmailHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
@SpringBootTest
@Slf4j
class BhtmusicApplicationTests {
    @Autowired
    EmailHelper emailHelper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    BizIdHelper bizIdHelper;

    @Autowired
    ConsumerServiceImpl consumerService;

    @Autowired
    HistoryService historyService;

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
        System.out.println(bizIdHelper.id());
    }

    /**
     * 用于刷用户数量
     */
    @Test
    void addUsers(){
        Consumer consumer = new Consumer();
        int num = 20225701;
        String username = null;
        String password = null;
        byte sex = 0;
        for (int i = 0;i<2;i++){
/*            if (sex == 1){
                sex--;
            }*/
            consumer.setUsername(String.valueOf(num));
            consumer.setPassword(String.valueOf(num));
            consumer.setSex(new Byte(sex));
            consumer.setPhoneNum(null);
            consumer.setEmail(null);
            consumer.setCreateTime(new Date());
            consumer.setUpdateTime(new Date());
            try{
            consumerService.addUser(consumer);
            log.info("用户"+String.valueOf(num)+"注册成功"+String.valueOf(new Date()));
            }
            catch (Exception e){
                System.out.println(e);
            }
            num++;
/*            sex ++;*/
        }
    }


    @Test
    void HistoryFind(){
        SimpleDateFormat a=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date=new Date();
        System.out.println(a.format(date));


    }

}
