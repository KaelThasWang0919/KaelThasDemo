package com.kaelthas.demo.App;

import android.app.Application;

/**
 * Created by KaelThas.Wang on 2016/10/20.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class AppConfig extends Application {

    /**
     * Volley 超时时间
     */
    public static int HTTP_TIMEOUT=1000*20;


    /**
     * @category 缓存路径
     */
    public final static String CACHE_PATH = "/winekar/cache/";
}
