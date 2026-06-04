package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类。
 *
 * 运行方式：
 *   mvn spring-boot:run
 * 或构建 jar 运行：
 *   mvn package -DskipTests && java -jar target/mybatis-pg-demo-1.0-SNAPSHOT.jar
 */
@SpringBootApplication
@MapperScan("com.example.mapper")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
