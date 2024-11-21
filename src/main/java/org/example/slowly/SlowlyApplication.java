package org.example.slowly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.slowly.mapper")
public class SlowlyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SlowlyApplication.class, args);
    }

}
