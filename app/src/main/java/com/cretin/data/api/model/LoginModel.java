package com.cretin.data.api.model;

/**
 * Created by cretin on 2017/8/22.
 */

public class LoginModel {

    /**
     * loginAccount : {"password":"4169eccfb970cc679e56546e0b75c25789a2ff7652f97a7939ce6a4421709245","salt":"TlfOA9nsIy18YReccTrU4s9g1K-fl85o","nickName":"","ip":"202.106.10.250","imPassword":"4169eccfb970cc679e56546e0b75c257","likeCount":0,"id":10,"avatar":"x.jpg","userName":"18612800916","createAt":"2017-08-16 16:49:36","status":1}
     * isOk : true
     * isFail : false
     * message : 登录成功
     */

    private LoginAccountBean loginAccount;
    private boolean isOk;
    private boolean isFail;
    private String message;

    public LoginAccountBean getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(LoginAccountBean loginAccount) {
        this.loginAccount = loginAccount;
    }

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

    public static class LoginAccountBean {
        /**
         * password : 4169eccfb970cc679e56546e0b75c25789a2ff7652f97a7939ce6a4421709245
         * salt : TlfOA9nsIy18YReccTrU4s9g1K-fl85o
         * nickName :
         * ip : 202.106.10.250
         * imPassword : 4169eccfb970cc679e56546e0b75c257
         * likeCount : 0
         * id : 10
         * avatar : x.jpg
         * userName : 18612800916
         * createAt : 2017-08-16 16:49:36
         * status : 1
         */

        private String password;
        private String salt;
        private String nickName;
        private String ip;
        private String imPassword;
        private int likeCount;
        private int id;
        private String avatar;
        private String userName;
        private String createAt;
        private int status;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getImPassword() {
            return imPassword;
        }

        public void setImPassword(String imPassword) {
            this.imPassword = imPassword;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
