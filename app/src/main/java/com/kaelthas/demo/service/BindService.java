package com.kaelthas.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by KaelThas.Wang on 2017/7/19.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class BindService extends Service {

    private static final String TAG=BindService.class.getSimpleName();

    public BindService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"-->onBind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"-->onUnbind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public MyBinder() {
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"-->onCreate()");

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG,"-->onStart()");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG,"-->onRebind()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"-->onDestroy()");
    }
}
