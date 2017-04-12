package com.kaelthas.demo.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityQrcodeBinding;
import com.kaelthas.demo.utils.PicassoImageLoader;
import com.kaelthas.demo.utils.QRCodeUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

public class QRCodeActivity extends BaseActivity {

    private ActivityQrcodeBinding mBinding;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("二维码(QRCode)");

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode);

        initListener();


        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private void initListener() {

        mBinding.btnCreateQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String QRCode = mBinding.edtQrCode.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = QRCodeUtil.createQRImage(QRCode, 200, 200);

                        if (bitmap != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mBinding.imgQrCode.getVisibility() == View.GONE)
                                        mBinding.imgQrCode.setVisibility(View.VISIBLE);
                                    mBinding.imgQrCode.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }
                }).start();

            }
        });


        mBinding.btnReadQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mBinding.btnScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ZXingScanActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            switch (requestCode) {
                case REQUEST_CODE:

                    if (data != null) {


                        final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

                        new AsyncTask<Void, Void, String>() {
                            @Override
                            protected String doInBackground(Void... voids) {
                                return QRCodeDecoder.syncDecodeQRCode(images.get(0).path);
                            }

                            @Override
                            protected void onPostExecute(String result) {
                                super.onPostExecute(result);

                                Toast.makeText(mContext, TextUtils.isEmpty(result) ? "无法识别该图片" : result, Toast.LENGTH_LONG).show();

                            }
                        }.execute();
                    }

                    break;
                default:
                    break;
            }
        }
    }


}
