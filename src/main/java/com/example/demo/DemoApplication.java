package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        // 配置本地科学上网代理
        System.setProperty("http.proxyHost","127.0.0.1");
        System.setProperty("http.proxyPort","7078");
        System.setProperty("https.proxyHost","127.0.0.1");
        System.setProperty("https.proxyPort","7078");

        // 禁用重新启动应用
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("项目启动完成");
    }

}