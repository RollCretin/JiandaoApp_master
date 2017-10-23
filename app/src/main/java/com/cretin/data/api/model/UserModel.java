package com.cretin.data.api.model;

/**
 * Created by cretin on 2017/10/21.
 */

public class UserModel {

    /**
     * userId : cc8a4a5c25cd4669a5378ed8c293d95c
     * username : cretin
     * avatar : null
     * nickname : 好莱坞弟弟
     * age : null
     * sex : null
     */

    private String userId;
    private String username;
    private String avatar;
    private String nickname;
    private int age;
    private int sex;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
