package com.heart.heartcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.heart.heartcloud.dao")
public class HeartCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartCloudApplication.class, args);
    }

}
