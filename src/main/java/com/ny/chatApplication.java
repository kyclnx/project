package com.ny;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author njx
 * @date 2024/11/3
 * @Description
 */
@SpringBootApplication
@MapperScan("com.ny.mapper")
public class chatApplication {
    public static void main(String[] args) {
        SpringApplication.run(chatApplication.class, args);
    }
}
