package com.teach.wecharprogram;

import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.util.StaticUtil;
import org.junit.Test;


/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/6/22 16:04
 */
public class Test1 {


    @Test
    public void test1() {
        String tokenPrefix = StaticUtil.getLoginToken(1142326137901330433L) + "/";
        // 删除用户之前的token
//        redisUtil.deleteLike(CacheConstant.USER_TOKEN_CODE + tokenPrefix);
        System.out.println(CacheConstant.USER_TOKEN_CODE + tokenPrefix);
    }
}
