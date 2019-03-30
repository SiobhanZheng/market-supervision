package com.siobhan.controller.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by siobhan.zheng on 2019/3/28
 * WebMVC 注解配置类
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器 拦截请求规则:除了登陆和获取验证码的其它接口都拦截
        registry.addInterceptor(new MyHandlerInterceptor())
                .addPathPatterns("/supervision/**")
                .excludePathPatterns("/supervision/loginCheck", "/supervision/getCaptcha");
    }
}
