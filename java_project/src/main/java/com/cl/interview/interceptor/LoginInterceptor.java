package com.cl.interview.interceptor;

import com.cl.interview.common.Constant;
import com.cl.interview.common.HttpResp;
import com.cl.interview.common.IoTErrorCode;
import com.cl.interview.config.annotation.NoneAuth;
import com.cl.interview.config.token.RedisTokenImpl;
import com.cl.interview.config.token.TokenModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private RedisTokenImpl redisToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("已进入拦截器");
        //如果不是映射到方法则直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(NoneAuth.class) != null){
            return true;
        }
        String access_token = request.getHeader(Constant.AUTHORIZATION);
        TokenModel tokenModel = redisToken.get(access_token);
        if (redisToken.check(tokenModel)){
            request.setAttribute(Constant.CURRENT_USER_ID, tokenModel.getUserId());
            return true;
        }
        //验证未通过
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(HttpResp.fail(IoTErrorCode.JWT_TOKEN_EXPIRED.getErrorCode(),"没有该权限，请登录后再访问").toString());
        return super.preHandle(request, response, handler);
    }
}
