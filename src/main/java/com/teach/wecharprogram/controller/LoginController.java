package com.teach.wecharprogram.controller;

import com.google.common.collect.ImmutableMap;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.RequestConstant;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.AdminUserVo;
import com.teach.wecharprogram.entity.vo.WxUserInfoVo;
import com.teach.wecharprogram.service.LoginService;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.IpHelper;
import com.teach.wecharprogram.util.StaticUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/4/8 12:05
 */
@Api(tags = "用户登陆")
@RequestMapping("/api/login")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;


    /**
     * @return 通过手机号登陆
     */
    @ApiOperation(value = "通过用户名密码登录", response = ResponseEntity.class)
    @PostMapping("/login_general")
    @ApiImplicitParams({@ApiImplicitParam(value = "用户名", name = "username", required = true),
            @ApiImplicitParam(value = "密码", name = "password", required = true)})
    public ResponseEntity loginByGeneral(String username, String password, HttpServletRequest request) {
        String ipAddr = IpHelper.getRequestIpAddr(request);
        return ResponseEntity.success(loginService.loginByGeneral(username, password, ipAddr));
    }

    /**
     * @param code
     * @return 通过手机号登陆
     */
    @ApiOperation(value = "通过手机号登陆", response = ResponseEntity.class)
    @PostMapping("/login_phone")
    @ApiImplicitParams({@ApiImplicitParam(value = "手机号", name = "phone", required = true), @ApiImplicitParam(value = "验证码", name = "code", required = true)})
    public ResponseEntity loginByPhone(String phone, String code, HttpServletRequest request) {
        String ipAddr = IpHelper.getRequestIpAddr(request);
        return ResponseEntity.success(ImmutableMap.of(RequestConstant.TOKEN, loginService.loginByPhone(phone, code, ipAddr)));
    }

    /**
     * 登录流程：小程序调auth.code2Session API 获取jsCode和用户信息 -> 后端接收到jsCode获取openid -> 通过openid查询用户是否存在
     * 存在执行登录 不存在新增用户
     *
     * @param wxUserInfoVo
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "通过微信登陆", response = ResponseEntity.class)
    @PostMapping("/login_wx")
    public ResponseEntity loginByPhone(WxUserInfoVo wxUserInfoVo, HttpServletRequest request) throws UnsupportedEncodingException {
        // 校验字段
        StaticUtil.validateField(wxUserInfoVo.getCode());
        String ipAddr = IpHelper.getRequestIpAddr(request);
        wxUserInfoVo.setNickName(URLEncoder.encode(wxUserInfoVo.getNickName(), "UTF-8"));
        return ResponseEntity.success(ImmutableMap.of(RequestConstant.TOKEN, loginService.loginByWx(wxUserInfoVo, ipAddr)));
    }

    /**
     * @return 发送验证码
     */
    @ApiOperation(value = "发送验证码", response = ResponseEntity.class)
    @ApiImplicitParam(value = "手机号", name = "phone", required = true)
    @PostMapping("/login_send")
    public ResponseEntity sendCode(String phone) {
        return ResponseEntity.success(loginService.sendCode(phone));
    }

    /**
     * @return 管理员信息
     */
    @ApiOperation(value = "管理员信息", response = ResponseEntity.class)
    @PostMapping("/userInfo")
    public AdminUserVo userInfo(@RequestHeader String token) {
        User user = userService.getLoginUser(token);
        return new AdminUserVo(user.getId().toString(), user.getRealName(), token, user.getStatus(), user.getRoleCode().equals(CommonConstant.ROLE_ADMIN));
    }
}
