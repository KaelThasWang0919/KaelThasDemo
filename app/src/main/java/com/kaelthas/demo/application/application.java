package com.kaelthas.demo.application;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by KaelThas.Wang on 2017/4/7.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class application extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        Config.DEBUG=true;
    }

    {
        PlatformConfig.setQQZone("1106087834","UMwn70SVJXU2oQnj");
    }

}
