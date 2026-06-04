package com.example;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Spring Boot 启动类。
 * 启动后自动连接 PostgreSQL，并执行演示 CRUD（通过 CommandLineRunner）。
 *
 * 运行方式：
 *   mvn spring-boot:run
 * 或构建 jar 运行：
 *   mvn package -DskipTests && java -jar target/mybatis-pg-demo-1.0-SNAPSHOT.jar
 */
@SpringBootApplication
@MapperScan("com.example.mapper")
public class Main implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("===== Spring Boot + MyBatis + PostgreSQL 启动成功 =====\n");

        // ---- 查询全部用户 ----
        System.out.println("===== 查询全部用户 =====");
        List<User> users = userMapper.findAll();
        for (User u : users) {
            System.out.println(u);
        }

        // ---- 按 ID 查询 ----
        if (!users.isEmpty()) {
            Integer firstId = users.get(0).getId();
            System.out.println("\n===== 查询 ID = " + firstId + " =====");
            User user = userMapper.findById(firstId);
            System.out.println(user);
        }

        // ---- 新增 ----
        System.out.println("\n===== 新增用户 =====");
        User newUser = new User("张三", "zhangsan@example.com", 28);
        int affected = userMapper.insert(newUser);
        System.out.println("影响行数: " + affected + "，新用户 ID: " + newUser.getId());

        // ---- 更新 ----
        if (newUser.getId() != null) {
            newUser.setAge(29);
            userMapper.update(newUser);
            System.out.println("更新后: " + userMapper.findById(newUser.getId()));
        }

        // ---- 删除（默认注释掉） ----
        // userMapper.deleteById(newUser.getId());

        System.out.println("\n===== 演示完毕 =====");
    }
}
