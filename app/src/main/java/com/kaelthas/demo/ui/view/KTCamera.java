package com.kaelthas.demo.ui.view;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceView;

/**
 * Created by KaelThas.Wang on 2017/4/10.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class KTCamera extends SurfaceView {
    private Camera mCamera;

    public KTCamera(Context context) {
        super(context);
        initCamera();
    }

    private void initCamera() {

        mCamera= android.hardware.Camera.open();
    }


}
