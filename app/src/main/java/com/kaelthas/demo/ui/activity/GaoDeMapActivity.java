package com.kaelthas.demo.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityGaodemapBinding;

/**
 * 高德地图web
 * <p>
 * Created by KaelThas.Wang on 2017/8/14.
 * Email: KaelThas.Wang0919@gmail.com
 */


public class GaoDeMapActivity extends BaseActivity<ActivityGaodemapBinding> {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final double shopLng = 120.41242;
        final double shopLat = 36.076349;

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_gaodemap);

        mWebView = mDataBinding.wvGaode;

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl("file:///android_asset/amap.html");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //super.onPageFinished(view, url);
                mWebView.loadUrl("javascript:addMarker(" + shopLng + "," + shopLat + ")");
            }
        });


    }
}
