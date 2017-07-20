package com.kaelthas.demo.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityServiceBinding;
import com.kaelthas.demo.service.BindService;
import com.kaelthas.demo.service.StartService;

/**
 * Created by KaelThas.Wang on 2017/7/19.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class ServiceActivity extends BaseActivity implements View.OnClickListener {

    private MyServiceConn mServiceConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityServiceBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_service);

        binding.btnStart.setOnClickListener(this);
        binding.btnBind.setOnClickListener(this);

        mServiceConn = new MyServiceConn();
    }

    /*
    解除绑定后生命周期
07-20 00:10:53.863 24574-24574/wang.kaelthas.demo I/StartService: -->onCreate
07-20 00:10:53.864 24574-24574/wang.kaelthas.demo I/StartService: -->onStart
07-20 00:10:54.204 24574-24574/wang.kaelthas.demo D/BindService: -->onCreate()
07-20 00:10:54.208 24574-24574/wang.kaelthas.demo I/BindService: -->onBind
07-20 00:10:57.542 24574-24574/wang.kaelthas.demo I/BindService: -->onUnbind
07-20 00:10:57.542 24574-24574/wang.kaelthas.demo D/BindService: -->onDestroy()
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bind:
                bindService(new Intent(mContext, BindService.class),mServiceConn,BIND_AUTO_CREATE);
                break;

            case R.id.btn_start:
                startService(new Intent(mContext, StartService.class));
                break;

            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //z这里如果不 进行解除绑定会抛出异常 虽然不会导致程序崩溃
        unbindService(mServiceConn);
    }

    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
}
/*

不解除绑定的生命周期，也会调取onUnbind方法 但是会抛出警告

07-20 00:14:07.385 27723-27723/wang.kaelthas.demo I/StartService: -->onCreate
07-20 00:14:07.385 27723-27723/wang.kaelthas.demo I/StartService: -->onStart
07-20 00:14:07.636 27723-27723/wang.kaelthas.demo D/BindService: -->onCreate()
07-20 00:14:07.637 27723-27723/wang.kaelthas.demo I/BindService: -->onBind
07-20 00:14:07.948 27723-27723/wang.kaelthas.demo I/System: Daemon delayGCRequest, sDelayGCRequest=true, delay=false, sPendingGCRequest=false
07-20 00:14:09.728 27723-27723/wang.kaelthas.demo I/IgnoreTouchEvent: ignoreTouchEvent onWindowDetached
07-20 00:14:09.753 27723-27723/wang.kaelthas.demo E/ActivityThread: Activity com.kaelthas.demo.ui.activity.ServiceActivity has leaked ServiceConnection com.kaelthas.demo.ui.activity.ServiceActivity$MyServiceConn@158bac3 that was originally bound here
                                                                    android.app.ServiceConnectionLeaked: Activity com.kaelthas.demo.ui.activity.ServiceActivity has leaked ServiceConnection com.kaelthas.demo.ui.activity.ServiceActivity$MyServiceConn@158bac3 that was originally bound here
                                                                        at android.app.LoadedApk$ServiceDispatcher.<init>(LoadedApk.java:1090)
                                                                        at android.app.LoadedApk.getServiceDispatcher(LoadedApk.java:984)
                                                                        at android.app.ContextImpl.bindServiceCommon(ContextImpl.java:1318)
                                                                        at android.app.ContextImpl.bindService(ContextImpl.java:1301)
                                                                        at android.content.ContextWrapper.bindService(ContextWrapper.java:606)
                                                                        at com.kaelthas.demo.ui.activity.ServiceActivity.onClick(ServiceActivity.java:52)
                                                                        at android.view.View.performClick(View.java:5218)
                                                                        at android.view.View$PerformClick.run(View.java:21279)
                                                                        at android.os.Handler.handleCallback(Handler.java:739)
                                                                        at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                        at android.os.Looper.loop(Looper.java:148)
                                                                        at android.app.ActivityThread.main(ActivityThread.java:5547)
                                                                        at java.lang.reflect.Method.invoke(Native Method)
                                                                        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:935)
                                                                        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:726)
07-20 00:14:09.754 27723-27723/wang.kaelthas.demo I/BindService: -->onUnbind
07-20 00:14:09.755 27723-27723/wang.kaelthas.demo D/BindService: -->onDestroy()

 */