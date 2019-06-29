package com.teach.wecharprogram;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zm
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2Doc
@EnableAsync
public class WechatProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatProgramApplication.class, args);
    }

}
