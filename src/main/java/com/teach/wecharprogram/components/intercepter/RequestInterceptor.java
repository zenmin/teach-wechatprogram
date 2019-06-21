package com.teach.wecharprogram.components.intercepter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.common.constant.RequestConstant;
import com.teach.wecharprogram.entity.Logs;
import com.teach.wecharprogram.service.LoginService;
import com.teach.wecharprogram.service.LogsService;
import com.teach.wecharprogram.util.IpHelper;
import com.teach.wecharprogram.util.StaticUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Describle This Class Is 验证所有请求权限
 * @Author ZengMin
 * @Date 2019/1/3 19:18
 */
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    LogsService commonLogService;

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new CommonException(DefinedCode.NOTAUTH, "登陆超时，请重新登录！");
        }

        if (!token.equals(RequestConstant.MAPS_SERVER_TOKEN) && !loginService.checkLogin(token)) {
            throw new CommonException(DefinedCode.NOTAUTH, "登陆超时，请重新登录！");
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        String params = StaticUtil.objectMapper.writeValueAsString(parameterMap);
        log.info("客户端ip:[{}]请求URL:[{}] ,请求params:[{}]", IpHelper.getRequestIpAddr(request), request.getRequestURL(), params);
        commonLogService.save(new Logs(IpHelper.getRequestIpAddr(request), request.getRequestURL().toString(), params));
        return true;
    }

}
