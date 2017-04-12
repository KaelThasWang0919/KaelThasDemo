package com.kaelthas.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityCameraBinding;


/**
 * 谷歌官方相机控件
 */


public class CameraActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCameraBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setTitle("原生相机");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera);

        mBinding.btnCamera1.setOnClickListener(this);
        mBinding.btnCamera2.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera1:
                startActivity(new Intent(CameraActivity.this,Camera1Activity.class));

                break;
            case R.id.btn_camera2:
                startActivity(new Intent(CameraActivity.this,Camera2Activity.class));
                break;
            default:
                break;

        }
    }
}
