package com.kaelthas.demo.ui.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityCamera1Binding;
import com.kaelthas.demo.databinding.ActivityCameraBinding;

import java.io.IOException;


/**
 * 谷歌官方相机控件
 */


public class Camera1Activity extends BaseActivity {

    private ActivityCamera1Binding mBinding;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private int viewWidth, viewHeight;//mSurfaceView的宽和高
    private int cameraPosition = CameraInfo.CAMERA_FACING_BACK;

    private String mFlashModes[] = {Parameters.FLASH_MODE_AUTO, Parameters.FLASH_MODE_ON, Parameters.FLASH_MODE_OFF};

    private String mFlashModeText[] = {"自动", "开", "关"};
    private int mFlashMode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Camera1");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera1);
        initView();
        initListener();
    }

    private void initView() {
        mBinding.tvFlashMode.setText(mFlashModeText[mFlashMode]);
        mSurfaceView = mBinding.sfvCamera;
        mSurfaceHolder = mSurfaceView.getHolder();
        // mSurfaceView 不需要自己的缓冲区
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // mSurfaceView添加回调
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                // 释放Camera资源
                if (mCamera != null) {
//                    Camera.Parameters p = mCamera.getParameters();
//                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                    mCamera.setParameters(p);
                    mCamera.stopPreview();
                    mCamera.release();
                }
            }
        });
    }

    private void initListener() {

        //拍照
        mBinding.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.autoFocus(autoFocusCallback);
            }
        });


        //切换  摄像头模式
        mBinding.ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cameraCount;
//                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();
                if (cameraCount > 1) {
                    if (cameraPosition == CameraInfo.CAMERA_FACING_FRONT) {
                        cameraPosition = CameraInfo.CAMERA_FACING_BACK;
                    } else {
                        cameraPosition = CameraInfo.CAMERA_FACING_FRONT;
                    }

                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(cameraPosition);//打开当前选中的摄像头
                    mCamera.setDisplayOrientation(90);//摄像头进行旋转90°
                    try {
                        mCamera.setPreviewDisplay(mSurfaceHolder);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Parameters p = mCamera.getParameters();
                    p.setFlashMode(mFlashModes[mFlashMode]);
                    mCamera.setParameters(p);

                    mCamera.startPreview();//开始预览

                }

            }
        });

        //切换  闪关灯模式
        mBinding.tvFlashMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCamera = Camera.open();
                Parameters p = mCamera.getParameters();
                mFlashMode = (mFlashMode + 1) % 3;
                p.setFlashMode(mFlashModes[mFlashMode]);
                mCamera.setParameters(p);
                mCamera.startPreview();
                mBinding.tvFlashMode.setText(mFlashModeText[mFlashMode]);
            }
        });
    }

    /**
     * 自动对焦 对焦成功后 就进行拍照
     */
    AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {//对焦成功

                camera.takePicture(new ShutterCallback() {//按下快门
                    @Override
                    public void onShutter() {
                        //按下快门瞬间的操作
                    }
                }, new PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {//是否保存原始图片的信息

                    }
                }, pictureCallback);
            } else if (cameraPosition == CameraInfo.CAMERA_FACING_FRONT) {
                camera.takePicture(new ShutterCallback() {//按下快门
                    @Override
                    public void onShutter() {
                        //按下快门瞬间的操作
                    }
                }, new PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {//是否保存原始图片的信息

                    }
                }, pictureCallback);
            }
        }
    };

    /**
     * 获取图片
     */
    PictureCallback pictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            final Bitmap resource = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (resource == null) {
                Toast.makeText(Camera1Activity.this, "拍照失败", Toast.LENGTH_SHORT).show();
            }
            final Matrix matrix = new Matrix();
            matrix.setRotate(cameraPosition == CameraInfo.CAMERA_FACING_BACK ? 90 : -90);
            final Bitmap bitmap = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight(), matrix, true);
            final ImageView iv_show = mBinding.ivCameraResult;
            if (bitmap != null && iv_show.getVisibility() == View.GONE) {
                mCamera.stopPreview();
                iv_show.setVisibility(View.VISIBLE);
                mBinding.sfvCamera.setVisibility(View.GONE);

                iv_show.setImageBitmap(bitmap);


                Toast.makeText(Camera1Activity.this, "拍照成功", Toast.LENGTH_SHORT).show();
            }

        }
    };

    /**
     * SurfaceHolder 回调接口方法
     */
    private void initCamera() {
        mCamera = Camera.open(cameraPosition);//默认开启后置
        mCamera.setDisplayOrientation(90);//摄像头进行旋转90°
        if (mCamera != null) {
            try {
                Parameters parameters = mCamera.getParameters();
                //设置预览照片的大小
                parameters.setPreviewFpsRange(viewWidth, viewHeight);
                //设置相机预览照片帧数
                parameters.setPreviewFpsRange(4, 10);
                //设置图片格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                //设置图片的质量
                parameters.set("jpeg-quality", 90);
                //设置照片的大小
                parameters.setPictureSize(viewWidth, viewHeight);
                //通过SurfaceView显示预览
                mCamera.setPreviewDisplay(mSurfaceHolder);
                //开始预览
                Parameters p = mCamera.getParameters();
                p.setFlashMode(mFlashModes[mFlashMode]);
                mCamera.setParameters(p);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mSurfaceView != null) {
            viewWidth = mSurfaceView.getWidth();
            viewHeight = mSurfaceView.getHeight();
        }
    }
}
