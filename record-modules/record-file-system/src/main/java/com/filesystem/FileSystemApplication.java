package com.filesystem;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lw
 */
@EnableDubbo
@SpringBootApplication
public class FileSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class, args);
    }

}
