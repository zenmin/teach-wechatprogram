package com.teach.wecharprogram.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.util.DateUtil;
import com.teach.wecharprogram.util.HttpClientUtil;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/3/15 19:09
 */
@RestController
@ApiIgnore
public class XmController {

    static JSONObject map = new JSONObject();

    static JSONObject detailMap = new JSONObject();

    static Map<String, Object> header = Maps.newHashMap();

    static {
        map.put("sorttype", "300");
        map.put("turnzj", "800,100,1000,200,1100,300,1200");
        header.put("devicetype", "android");
        header.put("User-Agent", " okhttp/3.3.1");
        header.put("access_token", "");
        header.put("Accept-Encoding", "gzip");
        detailMap.put("position", "project_preview");
    }

    @GetMapping("/getProject/{num}/{size}")
    public ResponseEntity getProject(@PathVariable int num, @PathVariable int size) {
        map.put("currentPage", String.valueOf(num));
        map.put("pageSize", String.valueOf(size));
        map.put("newtime", DateUtil.millisToDateTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss.S"));
        String json = HttpClientUtil.sendPostJson("http://www.pemarket.com.cn/api/projectlib/search", map, header);
        return ResponseEntity.success(JSONObject.parseObject(json));
    }

    @GetMapping("/getInfo/{id}")
    public ResponseEntity getInfo(@PathVariable String id) {
        detailMap.put("zj", id);
        String json = HttpClientUtil.sendPostJson("http://www.pemarket.com.cn/api/projectlib/detail_v600", detailMap, header);
        return ResponseEntity.success(JSONObject.parseObject(json));
    }

}
