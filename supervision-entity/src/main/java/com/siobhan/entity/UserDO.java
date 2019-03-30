package com.siobhan.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by siobhan.zheng on 2019/3/25
 */
@Table(name = "t_user")
@Entity
public class UserDO implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String userCode;
    private String password;
    private String userName;
    private String status;
    private String userPhone;
    private String reserved;
    private String createUser;
    private Date createTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "userCode='" + userCode + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", status='" + status + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", reserved='" + reserved + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
