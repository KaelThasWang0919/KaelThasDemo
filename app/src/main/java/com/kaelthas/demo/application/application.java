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
        Config.DEBUG=false;
    }

    {
        PlatformConfig.setQQZone("1106087834","UMwn70SVJXU2oQnj");
        PlatformConfig.setWeixin("wx4f3f81274e8b5dd9","ed759d6ae0eb9dc5e41d54f41b6aba46");
    }

}
