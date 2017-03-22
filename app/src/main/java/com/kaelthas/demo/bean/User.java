package com.kaelthas.demo.bean;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by KaelThas.Wang on 2017/3/20.
 * Email: KaelThas.Wang0919@gmail.com
 *
 * 数据双向绑定的Bean
 */

public class User extends BaseBean {

    public String userName;
    public String passWord;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
        notifyPropertyChanged(BR.passWord);
    }
}
