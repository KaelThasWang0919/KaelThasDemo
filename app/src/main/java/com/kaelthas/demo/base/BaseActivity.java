package com.kaelthas.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by KaelThas.Wang on 2016/11/1.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected final String TAG=this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
    }
}
