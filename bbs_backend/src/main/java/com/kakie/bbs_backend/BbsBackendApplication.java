package com.kakie.bbs_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.kakie.bbs_backend.mapper")
@EnableScheduling
public class BbsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbsBackendApplication.class, args);
    }

}
