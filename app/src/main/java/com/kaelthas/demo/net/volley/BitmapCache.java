package com.kaelthas.demo.net.volley;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.kaelthas.demo.base.AppConfig;
import com.kaelthas.demo.utils.FileUtils;
import com.kaelthas.demo.utils.ImageUtils;
import com.kaelthas.demo.utils.TextUtils;


import java.io.File;

public class BitmapCache implements ImageCache {

    private LruCache<String, Bitmap> mCache;
    private String sdcardCachePath = "";
    @SuppressWarnings("unused")
    private String cacheFileName = "";

    public BitmapCache() {

        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {

        return mCache.get(url);
    }

    /**
     * @category 获取本地图像
     */
    public Bitmap getSdCardBitmap(String key, String path) {

        Bitmap bitmap = null;
        sdcardCachePath = getSDCardRoot() + AppConfig.CACHE_PATH;



        if (FileUtils.isFileExist(new File(sdcardCachePath))) {
            bitmap = ImageUtils.getBitmap(new File(sdcardCachePath));

            if (bitmap != null) {
//                HLog.i("fileloader:", "文件已经存在");
//                HLog.d("fileloader", "isBigPicture" + HImage.isBigPicture(sdcardCachePath));
                // putImage(key, bitmap);
            } else {
//                HLog.w("fileloader:", "文件不存在,从内存拿不到图片");
            }

        } else {
//            HLog.w("fileloader:", "Sd卡中无缓存文件!!!");
        }

        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if (TextUtils.isEmpty(url)) {
            cacheFileName = FileUtils.getMd5UrlName(url);
        }

        mCache.put(url, bitmap);
    }




    /**
     * 得到当前外部存储设备的目录
     */
    private static String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    /**
     * @return 当前外部存储设备的目录
     */
    public static String getSDCardRoot() {
        return SDCardRoot;
    }
}
