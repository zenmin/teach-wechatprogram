package com.teach.wecharprogram.service;


import com.teach.wecharprogram.entity.vo.AdminUserVo;
import com.teach.wecharprogram.entity.vo.WxUserInfoVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/4/8 12:07
 */
public interface LoginService {

    boolean checkLogin(String token, HttpServletRequest request);

    String loginByPhone(String phone, String code, String ipAddr);

    boolean sendCode(String phone);

    Object loginByWx(WxUserInfoVo wxUserInfoVo, String ipAddr);

    AdminUserVo loginByGeneral(String username, String password, String ipAddr);

    void logOut(String token);

}
