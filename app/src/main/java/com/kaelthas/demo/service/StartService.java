package com.kaelthas.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by KaelThas.Wang on 2017/7/19.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class StartService extends Service {
    public static final String TAG = StartService.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"-->onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG,"-->onStart");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"-->onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"-->onBind");
        return new StartBinder();
    }


    public class StartBinder extends Binder {
        public StartBinder() {
        }
    }

}
