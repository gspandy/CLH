package com.cl.service.session;


import com.cl.service.info.UserInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionLocal implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
//	/**
//	 * 权限列表
//	 */
//	private List<PermissionInfo> permissions = null;
    /**
     * 用户ID
     */
    private Long userId = null;

    private String userName = null;

    private String nickname = null;

    private String userBaseId = null;

    private String rolesName;

    private String realName = null;

    private String remoteAddr = null;

    private Date lastDate = null;

    private String lastRemoteAddr;

    private Map<String, Object> attibuteMap = new HashMap<String, Object>();

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void addAttibute(String key, Object object) {
        attibuteMap.put(key, object);
    }

    public Object getAttibute(String key) {
        return attibuteMap.get(key);
    }

    public Object removeObject(String key) {
        return attibuteMap.remove(key);
    }

    public void setAttibuteMap(Map<String, Object> attibuteMap) {
        this.attibuteMap = attibuteMap;
    }

    public Map<String, Object> getAttibuteMap() {
        return this.attibuteMap;
    }


    public SessionLocal() {
    }

    public SessionLocal(Long userId, String userBaseId,
                        String accountId, String accountName, String realName, String userName,
                        String rolesName, UserInfo userInfo) {
        this.userId = userId;
        this.userBaseId = userBaseId;
        this.realName = realName;
        this.userName = userName;
        this.rolesName = rolesName;
        this.userInfo = userInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserBaseId() {
        return userBaseId;
    }

    public void setUserBaseId(String userBaseId) {
        this.userBaseId = userBaseId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRolesName() {
        return rolesName;
    }

    public void setRolesName(String rolesName) {
        this.rolesName = rolesName;
    }

    public Date getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getLastRemoteAddr() {
        return this.lastRemoteAddr;
    }

    public void setLastRemoteAddr(String lastRemoteAddr) {
        this.lastRemoteAddr = lastRemoteAddr;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SessionLocal [permissions=");
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", nickname=");
        builder.append(nickname);
        builder.append(", userBaseId=");
        builder.append(userBaseId);
        builder.append(", rolesName=");
        builder.append(rolesName);
        builder.append(", realName=");
        builder.append(realName);
        builder.append(", remoteAddr=");
        builder.append(remoteAddr);
        builder.append(", currentApproveState=");
        builder.append(", lastDate=");
        builder.append(lastDate);
        builder.append(", lastRemoteAddr=");
        builder.append(lastRemoteAddr);
        builder.append(", sessionMobileSend=");
        builder.append(", attibuteMap=");
        builder.append(attibuteMap);
        builder.append(", userTypeEnum=");
        builder.append(", userBizType=");
        builder.append("]");
        return builder.toString();
    }
}
