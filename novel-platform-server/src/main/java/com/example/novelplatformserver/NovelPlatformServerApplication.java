package com.example.novelplatformserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.example")
@Slf4j
@EnableCaching
public class NovelPlatformServerApplication {

    public static void main(String[] args) {
        System.out.println("nihao");
        SpringApplication.run(NovelPlatformServerApplication.class, args);
    }

}
