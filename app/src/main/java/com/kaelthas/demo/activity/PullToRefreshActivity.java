package com.kaelthas.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kaelthas.demo.R;

/**
 * Created by KaelThas.Wang on 16/9/6.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class PullToRefreshActivity extends AppCompatActivity {


    private SimpleDraweeView mSimpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);

        getActionBar().setTitle("Facebook Fresco演示");


        mSimpleDraweeView= (SimpleDraweeView) findViewById(R.id.sd_baidu);
        mSimpleDraweeView.setImageURI("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png");

    }






}
