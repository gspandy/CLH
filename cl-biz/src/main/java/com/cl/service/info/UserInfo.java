package com.cl.service.info;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class UserInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String userBaseId;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 用户名登录名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实
     */
    private String realName;

    /**
     * 密码错误次数
     */
    private int pwdErrorCount;
    /**
     * 创建时间
     */
    private Date rowAddTime;
    /**
     * 更新时间
     */
    private Date rowUpdateTime;

    private int[] roles;

    private String[] roleNames;

    private String userProvince;

    private String userCity;

    private String userCounty;

    private String zip;

    private String birthday;

    private String qq;

    private String logPassword;

    private String appRegisterFrom;

    public String getUserBaseId() {
        return userBaseId;
    }

    public void setUserBaseId(String userBaseId) {
        this.userBaseId = userBaseId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getPwdErrorCount() {
        return pwdErrorCount;
    }

    public void setPwdErrorCount(int pwdErrorCount) {
        this.pwdErrorCount = pwdErrorCount;
    }

    public Date getRowAddTime() {
        return rowAddTime;
    }

    public void setRowAddTime(Date rowAddTime) {
        this.rowAddTime = rowAddTime;
    }

    public Date getRowUpdateTime() {
        return rowUpdateTime;
    }

    public void setRowUpdateTime(Date rowUpdateTime) {
        this.rowUpdateTime = rowUpdateTime;
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    public String[] getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String[] roleNames) {
        this.roleNames = roleNames;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCounty() {
        return userCounty;
    }

    public void setUserCounty(String userCounty) {
        this.userCounty = userCounty;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getLogPassword() {
        return logPassword;
    }

    public void setLogPassword(String logPassword) {
        this.logPassword = logPassword;
    }

    public String getAppRegisterFrom() {
        return appRegisterFrom;
    }

    public void setAppRegisterFrom(String appRegisterFrom) {
        this.appRegisterFrom = appRegisterFrom;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userBaseId", userBaseId)
                .append("userId", userId)
                .append("userName", userName)
                .append("nickname", nickname)
                .append("realName", realName)
                .append("pwdErrorCount", pwdErrorCount)
                .append("rowAddTime", rowAddTime)
                .append("rowUpdateTime", rowUpdateTime)
                .append("roles", roles)
                .append("roleNames", roleNames)
                .append("userProvince", userProvince)
                .append("userCity", userCity)
                .append("userCounty", userCounty)
                .append("zip", zip)
                .append("birthday", birthday)
                .append("qq", qq)
                .append("logPassword", logPassword)
                .append("appRegisterFrom", appRegisterFrom)
                .toString();
    }
}
