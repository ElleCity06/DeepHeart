package com.deepheart.ellecity06.deepheart.bean;

import cn.bmob.v3.BmobUser;

/**
 * @author ellecity06
 * @time 2018/5/29 17:04
 * @project DeepHeart
 * @packge name：com.deepheart.ellecity06.deepheart.bean
 * @des 用户表
 */

public class MyUser extends BmobUser {

    // 用户的个性签名
    private String personalizedSignature;
    // 用户头像
    private String userIcon;
    // 用户昵称
    private String nickName;
    //年龄
    private int age;
    // 性别
    private int sex;
    // 来自哪里
    private String formCity;

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getFormCity() {
        return formCity;
    }

    public void setFormCity(String formCity) {
        this.formCity = formCity;
    }
}
