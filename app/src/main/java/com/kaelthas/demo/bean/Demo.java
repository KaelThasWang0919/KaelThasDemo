package com.kaelthas.demo.bean;

/**
 * Created by KaelThas.Wang on 2016/10/21.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class Demo {

    private String description;
    private Class targetActivity;

    public Demo() {
    }

    public Demo(String description, Class targetActivity) {
        this.description = description;
        this.targetActivity = targetActivity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(Class targetActivity) {
        this.targetActivity = targetActivity;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "description='" + description + '\'' +
                ", targetActivity=" + targetActivity +
                '}';
    }
}
