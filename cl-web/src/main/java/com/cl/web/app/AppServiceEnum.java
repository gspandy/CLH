package com.cl.web.app;

/**
 * @FileName AppServiceEnum.java
 * @Description
 * @Version 1.0
 * @Author liugy.
 * @Email liu5156@126.com
 * @History <br>
 * <li>Author: liugy</li>
 * <li>Date: 2017-04-01</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public enum  AppServiceEnum {
    
    
    login("login",false,true,"登录"),
    logout("logout",true,true,"登出");
    
    private final String service;
    
    private final boolean needLogin;

    private final boolean needResultLog;

    private final String message;

    /**
     * 
     * @param service 枚举值
     * @param needLogin 是否需要登录
     * @param needResultLog 是否需要打印日志
     * @param message 描述
     */
    AppServiceEnum(String service, boolean needLogin, boolean needResultLog, String message) {
        this.service = service;
        this.needLogin = needLogin;
        this.needResultLog = needResultLog;
        this.message = message;
    }

    /**
     * 检查方法是否存在
     * @param service
     * @return
     */
    public static boolean isExist(String service){
        if(service == null){
            return false;
        }
        for(AppServiceEnum appServiceEnum : values()){
            if(appServiceEnum.getService().equals(service)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取描述
     * @param service
     * @return
     */
    public static String getMsgByService(String service){
        if(service == null){
            return null;
        }
        AppServiceEnum appServiceEnum = getByService(service);
        if (appServiceEnum == null) {
            return null;
        }
        return appServiceEnum.getMessage();
    }

    /**
     * 该方法是否需要登录
     * @param service
     * @return
     */
    public static boolean isNeedLogin(String service){
        if(service == null){
            return false;
        }
        AppServiceEnum appServiceEnum = getByService(service);
        if(appServiceEnum == null){
            return false;
        }
        return appServiceEnum.isNeedLogin();
    }

    /**
     * 该方法是否需要打印日志
     * @param service
     * @return
     */
    public static boolean printResultLog(String service){
        AppServiceEnum appServiceEnum = getByService(service);
        if(appServiceEnum == null){
            return false;
        }
        return appServiceEnum.isNeedResultLog();
    }

    /**
     * 获取方法的枚举类
     * @param service
     * @return
     */
    public static AppServiceEnum getByService(String service){
        if(service == null){
            return null;
        }
        for(AppServiceEnum appServiceEnum : values()){
            if(appServiceEnum.getService().equals(service)){
                return appServiceEnum;
            }
        }
        return null;
    }
    
    public String getService() {
        return service;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public boolean isNeedResultLog() {
        return needResultLog;
    }

    public String getMessage() {
        return message;
    }
}
