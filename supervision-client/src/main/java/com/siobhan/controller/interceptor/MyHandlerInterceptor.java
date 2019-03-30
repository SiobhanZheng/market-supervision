package com.siobhan.controller.interceptor;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by siobhan.zheng on 2019/3/28
 * spring boot 自定义拦截器
 */
public class MyHandlerInterceptor implements HandlerInterceptor{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyHandlerInterceptor.class);

    /**
     * 接口请求前调用
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if(null == request.getSession().getAttribute("user")){
            // 登录过期或者未登录
            logger.error("login expired  & url = " + request.getRequestURL());
            throw new Exception("登录过期或者未登录");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
