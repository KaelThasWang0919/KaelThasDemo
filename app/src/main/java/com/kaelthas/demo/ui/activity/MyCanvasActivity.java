package com.kaelthas.demo.ui.activity;

import android.os.Bundle;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityMyCanvasBinding;

/**
 * Created by KaelThas.Wang on 2017/10/12.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class MyCanvasActivity extends BaseActivity<ActivityMyCanvasBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_my_canvas);
    }
}
