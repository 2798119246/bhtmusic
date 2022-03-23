package com.luanxiede.bhtmusic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.luanxiede.bhtmusic.dao")
public class BhtmusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BhtmusicApplication.class, args);
    }

}

