package com.hao.project.aop;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hao.project.annotation.AuthCheck;
import com.hao.project.common.ErrorCode;
import com.hao.project.exception.BusinessException;
import com.hao.project.service.UserService;
import com.hao.haoapicommon.service.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 权限校验 AOP
 *
 * @author hao
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 切入点
     */
    @Pointcut(value = "@annotation(com.hao.project.annotation.AuthCheck)")
    public void authCheckPointCut(){}


    @Around(value = "authCheckPointCut()")
    public Object doInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        // 反射获取被拦截方法上的注解对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AuthCheck authCheck = signature.getMethod().getAnnotation(AuthCheck.class);
        // 获取注解对象上的值
        List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User user = userService.getLoginUser(request);
        // 拥有任意权限即通过
        if (CollectionUtils.isNotEmpty(anyRole)) {
            String userRole = user.getUserRole();
            if (!anyRole.contains(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        // 必须有所有权限才通过
        if (StringUtils.isNotBlank(mustRole)) {
            String userRole = user.getUserRole();
            if (!mustRole.equals(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
//    @Around("@annotation(authCheck)")
//    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
//        List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//        String mustRole = authCheck.mustRole();
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        // 当前登录用户
//        User user = userService.getLoginUser(request);
//        // 拥有任意权限即通过
//        if (CollectionUtils.isNotEmpty(anyRole)) {
//            String userRole = user.getUserRole();
//            if (!anyRole.contains(userRole)) {
//                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//            }
//        }
//        // 必须有所有权限才通过
//        if (StringUtils.isNotBlank(mustRole)) {
//            String userRole = user.getUserRole();
//            if (!mustRole.equals(userRole)) {
//                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//            }
//        }
//        // 通过权限校验，放行
//        return joinPoint.proceed();
//    }
}

