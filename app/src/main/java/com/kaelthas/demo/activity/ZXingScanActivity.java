package com.kaelthas.demo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityZxingScanBinding;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZXingScanActivity extends BaseActivity implements ZXingView.Delegate {

    private ActivityZxingScanBinding mBinding;
    private QRCodeView mQRCodeView;
    private String mFlashlight = "开灯";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_zxing_scan);
        mQRCodeView = mBinding.ZXingView;
        mQRCodeView.setDelegate(this);

        mBinding.btnFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("开灯".equals(mBinding.btnFlashLight.getText().toString())) {
                    mQRCodeView.openFlashlight();
                    mBinding.btnFlashLight.setText("关灯");
                } else {
                    mQRCodeView.closeFlashlight();
                    mBinding.btnFlashLight.setText("开灯");
                }

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();

        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {

        Log.e("success----", result);
        Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
        mQRCodeView.startSpot();

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "调起相机发生错误");
    }
}
