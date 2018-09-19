package com.stephen.myblog.controller.manager;


import com.stephen.myblog.exception.BisnessException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Aspect
@Component
public class BlogManagerAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.stephen.myblog.controller.manager.*.*(..))")
    public void pointcut(){}

/*
    @Before("pointcut()")
    public void  doBefore(JoinPoint joinPoint) throws Throwable {
            System.out.println("执行前");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session=request.getSession();
        String username=(String) session.getAttribute("username");
        if(!StringUtils.isNotEmpty(username)){
            logger.info("请先登录==============");
        }
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "pointcut()",returning = "returnVal")
    public void AfterReturning(Object returnVal) throws Throwable {
        System.out.println("后置通知...."+returnVal);
    }


    @After("pointcut()")
    public  void after() throws Throwable {
        System.out.println("执行后");
    }*/


    /**
     * 环绕通知
     * @param joinPoint 可用于执行切点的类
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕通知前....");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session=request.getSession();
        logger.info("会话超时时间为"+session.getMaxInactiveInterval());
        String username=(String) session.getAttribute("username");
        if(StringUtils.isEmpty(username)){
            logger.info("请先登录==============");
            throw new BisnessException("未登录状态，不可访问！");
        }
        Object obj= (Object) joinPoint.proceed();
        System.out.println("环绕通知后....");
        return obj;
    }

}
