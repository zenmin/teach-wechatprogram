package com.teach.wecharprogram.common.constant;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/4/2 17:33
 */
public class CacheConstant {

    public static final String INVITE_PREFIX = "INVITE:CODE:";  // 邀请码缓存前缀

    public static final String USER_TOKEN_CODE = "USER:TOKEN:";   // 用户登陆前缀

    public static final String AIR_ZONES_PREFIX = "AIR:ZONE::"; // 地点缓存

    public static final String AIR_ZONES_NUM_PREFIX = "AIR:ZONE:NUM:";  // 地点搜索次数

    public static final String LOCK_SCHEDULING_MONTH = "LOCK:SCHEDULING:MONTH"; //每月定时任务锁

    public static final String LOCK_SCHEDULING_HOURS = "LOCK:SCHEDULING:HOURS"; //每小时定时任务锁

    public static final String LOCK_SCHEDULING_DAY = "LOCK:SCHEDULING:DAY"; //每天定时任务锁

    public static final String LOCK_SCHEDULING_WEEK = "LOCK:SCHEDULING:WEEK";   //每周定时任务锁

    public static final Long EXPIRE_LOGON_TIME = 60 * 60 * 24 * 7L;           // 登录失效时间 秒

    public static final String LOGIN_PHONE_CODE = "USER:LOGIN:PHONE:";          // 验证码储存

    public static final Long EXPIRE_SMS_CODE = 65L;                      // 验证码有效期 秒

    public static final String WX_ACCESSTOKEN = "WX:ACCESSTOKEN";       // 微信accessToken

    public static final Long WX_ACCESSTOKEN_EXPIRE = 60 * 60 * 2 - 60L;    // accessToken超时时间  微信那边默认2小时

    public static final String WX_USER_OPENID = "USER:WX:OPENID:";    // accessToken超时时间  微信那边默认2小时

    public static final String PRODUCT_HOT_NAMES = "PRODUCT:HOTNAMES:";    // 产品库热门搜索



    /**
     * 以下为@Cacheable缓存的数据 三天有效期
     */
    public static final String HOTEL_AUTO = "HOTEL";        // 酒店房型 查询缓存

    public static final String PRODUCT_CHILD_AUTO = "PRODUCT:CHILD";    // 子产品查缓存

    public static final String PRODUCT_PROPERTIES_AUTO = "PRODUCT:PROPERTIES";    // 属性库查询缓存

    public static final String QX_PRODUCT_AUTO = "QX:PRODUCT";          // 齐欣产品

    public static final String QX_PRODUCT_INTERFACE = "QX:INTERFACE";          // 齐欣接口数据

}
