package com.teach.wecharprogram.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CacheConstant;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.components.business.RedisUtil;
import com.teach.wecharprogram.components.business.SmsUtil;
import com.teach.wecharprogram.components.business.WxUtil;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.WxUserInfoVo;
import com.teach.wecharprogram.service.LoginService;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/4/8 12:07
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    RedisUtil redisUtil;


    @Autowired
    UserService userService;

    @Autowired
    SmsUtil smsUtil;

    @Value("${spring.profiles.active}")
    String env;

    @Autowired
    WxUtil wxUtil;


    @Override
    public boolean checkLogin(String token, HttpServletRequest request) {
        String json = redisUtil.get(CacheConstant.USER_TOKEN_CODE + token);
        if (StringUtils.isBlank(json)) {
            return false;
        }
        User user = JSONObject.parseObject(json, User.class);
        request.setAttribute(token, user);
        return true;
    }

    /**
     * @param phone
     * @param code
     * @param ipAddr
     * @return 手机号登录
     */
    @Override
    public String loginByPhone(String phone, String code, String ipAddr) {
        // 验证码验证
        String key = CacheConstant.LOGIN_PHONE_CODE + phone;
        String sendCode = redisUtil.get(key);
        if (!code.equals(sendCode))
            throw new CommonException(DefinedCode.LOGIN_ERROR, "验证码错误！");

        // 检查用户是否存在
        User user = userService.findByPhone(phone);
        if (Objects.isNull(user)) {
            // 为空 生成一个用户
            user = userService.save(new User(null, StaticUtil.md5Hex(CommonConstant.INIT_PASSWORD), CommonConstant.STATUS_OK, phone,
                    "用户" + phone.substring(phone.length() - 4, phone.length()), ipAddr, new Date()));
        } else {
            user.setLastLoginIp(ipAddr);
            user.setLastLoginTime(new Date());
            userService.updateLoginTime(user);
        }
        return this.addToken(user, ipAddr);
    }

    /**
     * @param phone
     * @return 发送验证码
     */
    @Override
    public boolean sendCode(String phone) {
        // 判断此手机号是否已经发送过验证码
        String key = CacheConstant.LOGIN_PHONE_CODE + phone;
        String sendCode = redisUtil.get(key);
        if (StringUtils.isNotBlank(sendCode))
            throw new CommonException(DefinedCode.LOGIN_ERROR, "已发送过短信，请稍后再试！");

        String code = StaticUtil.genSmsCode(6);
        if (!env.equals("dev"))
            smsUtil.sendSms(phone, ImmutableMap.of("code", code));
        else
            code = "123456";
        redisUtil.set(key, Integer.parseInt(code), CacheConstant.EXPIRE_SMS_CODE);
        return true;
    }

    /**
     * @param wxUserInfoVo
     * @param ipAddr
     * @return 微信登录
     */
    @Override
    public String loginByWx(WxUserInfoVo wxUserInfoVo, String ipAddr) {
        // 先去微信获取openid
        Object o = wxUtil.authCode2Session(wxUserInfoVo.getCode());
        Map<String, Object> map = (Map<String, Object>) o;
        String openid = map.get("openid").toString();
//        String sessionKey = map.get("session_key").toString();

        // 判断此用户是否存在
        User user = userService.findByOpenId(openid);
        if (Objects.nonNull(user)) {
            // 存在  执行登录
            user.setLastLoginIp(ipAddr);
            user.setLastLoginTime(new Date());
            userService.updateLoginTime(user);
        } else {
            // 不存在  新增用户
            // 已过滤特殊字符
            user = userService.save(new User(wxUserInfoVo, ipAddr, StaticUtil.md5Hex(CommonConstant.INIT_PASSWORD), CommonConstant.STATUS_ERROR, openid));
        }
        return this.addToken(user, ipAddr);
    }

    @Override
    public String loginByGeneral(String username, String password, String ipAddr) {

        return null;
    }

    /**
     * 执行登录逻辑
     *
     * @param user
     * @param ipAddr
     * @return
     */
    private String addToken(User user, String ipAddr) {
        // 生成token
        String tokenPrefix = StaticUtil.getLoginToken(user.getId()) + "/";
        String token = tokenPrefix + System.currentTimeMillis();
        // 执行登录
        user.setPassword(null);
        user.setNowLoginIp(ipAddr);
        // 删除用户之前的token
        redisUtil.deleteLike(CacheConstant.USER_TOKEN_CODE + tokenPrefix);
        // 加token
        // 失效时间7天 , 60 * 60 * 24 * 7
        redisUtil.set(CacheConstant.USER_TOKEN_CODE + token, user, CacheConstant.EXPIRE_LOGON_TIME);
        return token;
    }


}
