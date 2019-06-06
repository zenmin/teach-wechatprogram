package com.teach.wecharprogram.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/15 19:09
 */
@Controller
@ApiIgnore
public class PortalController {

    @Value("${spring.profiles.active}")
    String env;

    @RequestMapping("/")
    public String toApi(){
        if(env.indexOf("dev")!=-1){
            return "redirect:swagger-ui.html";
        }
        return "/";
    }

}
