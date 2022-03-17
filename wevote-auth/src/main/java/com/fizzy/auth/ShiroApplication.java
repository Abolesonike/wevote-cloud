package com.fizzy.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author FizzyElf
 * @date 2021/10/18 10:40
 */
@SpringBootApplication(scanBasePackages = "com.fizzy.*")
@EnableFeignClients
public class ShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class,args);
    }
}
