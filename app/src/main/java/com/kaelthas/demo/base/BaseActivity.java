package com.kaelthas.demo.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by KaelThas.Wang on 2016/11/1.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class BaseActivity< T extends ViewDataBinding> extends AppCompatActivity {
    protected Context mContext;
    protected final String TAG=this.getClass().getName();
    protected T mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
    }

    public void showToast(final CharSequence msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setLayout(@LayoutRes int id){
        mDataBinding= DataBindingUtil.setContentView(this,id);
    }


}
