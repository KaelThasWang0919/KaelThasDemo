package com.kaelthas.demo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.kaelthas.demo.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KaelThas.Wang on 16/9/6.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class PullToRefreshActivity extends Activity {


    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        mImage= (ImageView) findViewById(R.id.image);
        mImage.setImageBitmap(getHttpBitmap("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png"));

    }





    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;

        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }

}
