package com.kaelthas.demo.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by KaelThas.Wang on 2017/8/20.
 * Email: KaelThas.Wang0919@gmail.com
 */

public abstract class BaseActivity2 <T extends ViewDataBinding >extends AppCompatActivity {
    protected Context mContext;
    protected final String TAG=this.getClass().getName();
    protected T mDataBinding;


    protected void onCreate(Bundle savedInstanceState,int layoutId) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        mDataBinding= DataBindingUtil.setContentView(this,layoutId);
    }

    public void showToast(CharSequence msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

}
