package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.util.IpHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping({"/", "/swagger-ui.html", "/v2"})
    public String toApi() {
        if (env.indexOf("dev") != -1) {
            return "doc";
        }
        return "/";
    }

    @RequestMapping("/nowIp")
    @ResponseBody
    public String nowIp() {
        return IpHelper.getLocalIpAddr();
    }

}
