package com.teach.wecharprogram;

import com.teach.wecharprogram.components.business.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.util.UriEncoder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatprogramApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        redisUtil.set("USER:TOKEN:652aea43935749bbb499c2a919dfe340/1561110214526","{\"id\":1135722579370184706,\"createTime\":\"2019-06-04 09:38:58\",\"username\":null,\"password\":null,\"lastLoginTime\":1561110214526,\"lastLoginIp\":\"118.112.57.175\",\"status\":1,\"inviteCode\":null,\"phone\":null,\"city\":\"Mianyang\",\"country\":\"China\",\"gender\":1,\"language\":\"zh_CN\",\"nickName\":\"%E6%9B%BE%E6%95%8F\",\"province\":\"Sichuan\",\"openid\":\"oX7C84gNJRLixwfnsqDgRI0yBrWY\",\"roleName\":null,\"roleCode\":null,\"nowLoginIp\":\"118.112.57.175\",\"sessionKey\":null}");
    }

}
