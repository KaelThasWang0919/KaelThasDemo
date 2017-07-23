package com.kaelthas.demo.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityDataBindingBinding;
import com.kaelthas.demo.databinding.ActivityDrawerLayoutBinding;

/**
 * Created by KaelThas.Wang on 2017/7/23.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class DrawerLayoutActivity extends BaseActivity<ActivityDrawerLayoutBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_drawer_layout);

        mDataBinding.btnToast.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toast:

                showToast("我是侧栏的按钮点击出来的");
                break;

            default:
                break;
        }
    }
}
