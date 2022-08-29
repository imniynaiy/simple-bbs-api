package com.theoriz.cnode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.theoriz.cnode.mapper")
public class CnodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CnodeApplication.class, args);
    }
}