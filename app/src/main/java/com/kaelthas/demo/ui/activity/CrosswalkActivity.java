package com.kaelthas.demo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

import com.kaelthas.demo.R;

public class CrosswalkActivity extends AppCompatActivity {


    private XWalkView xWalkWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crosswalk);
        // turn on debugging
        xWalkWebView= (XWalkView) findViewById(R.id.xwalkWebView);

        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        xWalkWebView.load("http://192.168.1.30:8069", null);
        // load local html file
        // xWalkWebView.load("localfile.html", null);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkWebView != null) {
            xWalkWebView.pauseTimers();
            xWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkWebView != null) {
            xWalkWebView.resumeTimers();
            xWalkWebView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkWebView != null) {
            xWalkWebView.onDestroy();
        }
    }
}
