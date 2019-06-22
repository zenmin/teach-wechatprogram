package com.teach.wecharprogram;

import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.util.IdWorker;
import com.teach.wecharprogram.util.StaticUtil;
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
        String tokenPrefix = StaticUtil.getLoginToken(1142326137901330433L) + "/";
        // 删除用户之前的token
//        redisUtil.deleteLike(CacheConstant.USER_TOKEN_CODE + tokenPrefix);
        boolean b = redisUtil.deleteLike(CacheConstant.USER_TOKEN_CODE + tokenPrefix);
        System.out.println(b);
    }

}
