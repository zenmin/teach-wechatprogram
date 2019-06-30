package com.teach.wecharprogram.common.constant;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/4/2 17:33
 */
public class CacheConstant {

    public static final String USER_TOKEN_CODE = "USER:TOKEN:";   // 用户登陆前缀

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
    public static final String ROLE_CACHE = "ROLE";    // 权限


}
