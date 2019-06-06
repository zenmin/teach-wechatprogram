package com.teach.wecharprogram;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2Doc
@EnableAsync
public class WecharprogramApplication {

    public static void main(String[] args) {
        SpringApplication.run(WecharprogramApplication.class, args);
    }

}
