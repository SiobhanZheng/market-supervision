package com.siobhan.proxy.dto;

/**
 * Created by siobhan.zheng on 2019/3/27
 */
public class UserDTO {
    private String userCode;
    private String userPw;
    private String captcha;

    public UserDTO() {}

    public UserDTO(String userCode, String userPw, String captcha) {
        this.userCode = userCode;
        this.userPw = userPw;
        this.captcha = captcha;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
