package com.cretin.data.api.model;

/**
 * Created by cretin on 2017/8/22.
 */

public class RegisterModel {

    /**
     * isOk : false
     * isFail : true
     * message : 手机号已被注册，如果忘记密码，可以使用密码找回功能
     */

    private boolean isOk;
    private boolean isFail;
    private String message;

    public boolean isIsOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

    public boolean isIsFail() {
        return isFail;
    }

    public void setIsFail(boolean isFail) {
        this.isFail = isFail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
