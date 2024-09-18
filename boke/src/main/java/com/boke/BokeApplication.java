package com.boke;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.boke.**.mapper")
@SpringBootApplication
public class BokeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BokeApplication.class, args);
    }

}