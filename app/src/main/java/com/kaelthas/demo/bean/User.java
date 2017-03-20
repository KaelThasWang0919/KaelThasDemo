package com.kaelthas.demo.bean;

/**
 * Created by KaelThas.Wang on 2017/3/20.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class User {

    public String userName;
    public String passWord;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
