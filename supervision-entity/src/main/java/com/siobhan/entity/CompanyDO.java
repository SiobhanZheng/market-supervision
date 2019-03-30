package com.siobhan.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by siobhan.zheng on 2019/3/27
 */
@Table(name = "t_company")
@Entity
public class CompanyDO implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String companyName;
    private String companyUrl;
    private String companyCode;
    private String companyAddress;
    private String companyPhone;
    private String companyLinkman;
    private String status;
    private String reserved;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;

    @Override
    public String toString() {
        return "CompanyDO{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyUrl='" + companyUrl + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", companyLinkman='" + companyLinkman + '\'' +
                ", status='" + status + '\'' +
                ", reserved='" + reserved + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyLinkman() {
        return companyLinkman;
    }

    public void setCompanyLinkman(String companyLinkman) {
        this.companyLinkman = companyLinkman;
    }

    public String getStatus() {
        if ("1".equals(status)){
            return "有效";
        } else {
        return "无效";
        }
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
