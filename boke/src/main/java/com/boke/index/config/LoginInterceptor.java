package com.boke.index.config;


import com.boke.index.utils.TokenUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component   //在容器中进行注册
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("token");
        if(TokenUtil.verify(token)){
            return true;
        }

        request.setAttribute("msg","登录出错");
        request.getRemoteHost();
        request.getRequestDispatcher("/login.html").forward(request,response);
        return false;
    }
}