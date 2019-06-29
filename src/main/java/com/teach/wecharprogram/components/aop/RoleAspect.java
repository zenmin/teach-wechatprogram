package com.teach.wecharprogram.components.aop;

import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.components.annotation.RequireRole;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.util.JSONUtil;
import com.teach.wecharprogram.util.StaticUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Describle This Class Is 全局权限验证切面
 * @Author ZengMin
 * @Date 2019/6/29 16:40
 */
@Component
@Aspect
@Slf4j
public class RoleAspect {

    @Pointcut("execution(* com.teach.wecharprogram.controller.*Controller.*(..))")
    private void pointCut() {
    }

    @Around("@annotation(requireRole) && pointCut()")
    public Object execAspect(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        this.validRole(joinPoint, request);
        return joinPoint.proceed();
    }

    private void validRole(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws IOException {
        String token = request.getHeader("token");
        Object attribute = request.getAttribute(token);
        User user = StaticUtil.objectMapper.readValue(JSONUtil.toJSONString(attribute), User.class);
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            RequireRole annotation = method.getAnnotation(RequireRole.class);
            String roleCode = annotation.value();
            if (!roleCode.equals(user.getRoleCode())) {
                // 无操作权限
                throw new CommonException(DefinedCode.NOTAUTH_OPTION, "您没有权限操作！");
            }

        } else {
            throw new IllegalArgumentException("该注解仅用于Controller实现方法上！");
        }

    }


}
