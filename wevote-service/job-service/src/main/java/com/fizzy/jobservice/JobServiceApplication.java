package com.fizzy.jobservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/9 20:09
 */
@SpringBootApplication(scanBasePackages = "com.fizzy.*")
@MapperScan("com.fizzy.jobservice.mapper")
@EnableFeignClients
public class JobServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobServiceApplication.class, args);
    }
}