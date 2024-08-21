package com.dashboard;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lw
 */
@EnableDubbo
@MapperScan("com.dashboard.mapper")
@SpringBootApplication
public class DashBoardSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashBoardSystemApplication.class, args);
    }

}
