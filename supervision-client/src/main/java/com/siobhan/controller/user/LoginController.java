package com.siobhan.controller.user;

import com.siobhan.proxy.MyResponse;
import com.siobhan.proxy.dto.PageDTO;
import com.siobhan.proxy.dto.UserDTO;
import com.siobhan.service.CompanyService;
import com.siobhan.service.UserService;
import com.siobhan.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by siobhan.zheng on 2019/3/25
 */
@RestController
@RequestMapping("/supervision")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        /**
         * 设置响应头
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成验证码并存入会话session
        String verifyCode = VerifyCodeUtils.generateCodes();
        request.getSession().setAttribute("verifyCode", verifyCode);
        logger.info("verifyCode = " + verifyCode);
        try {
            VerifyCodeUtils.outputImage(response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            logger.error("outputImage occur", e);
        }
    }

    @RequestMapping("/loginCheck")
    public MyResponse loginCheck(HttpServletRequest request, @RequestBody UserDTO userDTO){
        MyResponse response = new MyResponse("0", "用户名或密码不正确!", null);
        if (userDTO != null){
            HttpSession session = request.getSession();
           String verifyCode =  session.getAttribute("verifyCode") == null ? ""
                   : session.getAttribute("verifyCode").toString();
            if (!verifyCode.equals(userDTO.getCaptcha())){
                // 验证码不正确
                response = new MyResponse("0", "验证码不正确！",null);
            } else if (userService.checkUser(userDTO)){
                // 登陆成功
                session.setAttribute("user", userDTO);
                logger.info("账号：" + userDTO.getUserCode() + "成功登陆，ip：" + request.getRemoteAddr());
//                session.setMaxInactiveInterval(60 * 30);
                response = new MyResponse("1", "登陆成功！", null);
            }
        }
        return response;
    }
    @RequestMapping("/loginOut")
    public MyResponse loginOut(HttpServletRequest request){
        request.getSession().invalidate();
        return new MyResponse("1","退出成功", null);
    }

}
