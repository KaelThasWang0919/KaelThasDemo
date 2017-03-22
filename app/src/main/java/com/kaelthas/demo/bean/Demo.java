package com.kaelthas.demo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.kaelthas.demo.BR;

/**
 * Created by KaelThas.Wang on 2016/10/21.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class Demo extends BaseBean {

    private static final long serialVersionUID = -8676244995506034838L;
    private String description;
    private Class targetActivity;


    public Demo(String description, Class targetActivity) {
        this.description = description;
        this.targetActivity = targetActivity;
    }




    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Class getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(Class targetActivity) {
        this.targetActivity = targetActivity;
        notifyPropertyChanged(BR.targetActivity);
    }
}
