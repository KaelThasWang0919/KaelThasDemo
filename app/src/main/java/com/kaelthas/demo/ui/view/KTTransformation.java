package com.kaelthas.demo.ui.view;

import android.content.Context;
import android.graphics.Camera;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by KaelThas.Wang on 2017/7/27.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class KTTransformation implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View page, float position) {

        Log.d("KTTransformation", "pos===" + position);
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 1) {
// pos>=-1&& pos <=0属于Viewpager的可见部分
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position == -1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if (position < 0) {

                page.setTranslationX(horzMargin - vertMargin / 2);

            } else if (position == 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
            page.setAlpha(1);
        } else {
            page.setAlpha(0);
        }
    }

}
