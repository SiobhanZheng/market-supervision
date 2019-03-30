package com.siobhan.controller.exception;

import com.siobhan.proxy.MyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by siobhan.zheng on 2019/3/28
 */
@ControllerAdvice(basePackages = "com.siobhan")
public class AdviceConfigurer extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(AdviceConfigurer.class);


    @ExceptionHandler({Exception.class})
    @ResponseBody
    MyResponse<Void> handleControllerException(HttpServletRequest request, Throwable ex) {
        MyResponse response;
        if ("登录过期或者未登录".equals(ex.getMessage())) {
            logger.error("login expired  & errMsg = " + ex.getMessage());
            response = new MyResponse("000", "登录过期或者未登录", null);
        } else {
            logger.error("exception occur & status = 500 & errMsg = " + ex.getMessage(), ex);
            response = new MyResponse("500", "系统异常", null);
        }
        return response;
    }
}
