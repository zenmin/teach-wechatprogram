package com.teach.wecharprogram;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StopWatch;

/**
 * @author zm
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2Doc
@EnableAsync
public class WechatProgramApplication {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext run = SpringApplication.run(WechatProgramApplication.class, args);
        stopWatch.stop();
        WebServer webServer = ((AnnotationConfigServletWebServerApplicationContext) run).getWebServer();
        System.out.println("\n \n# # # # # # # # # #  √ 系统启动完成 耗时：" + stopWatch.getTotalTimeSeconds() + "s 运行端口：" + webServer.getPort() + " √  # # # # # # # # # #\n \n");

    }

}
