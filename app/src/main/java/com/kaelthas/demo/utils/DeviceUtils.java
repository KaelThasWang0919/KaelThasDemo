package com.kaelthas.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by KaelThas.Wang on 2016/10/20.
 * E_mail KaelThas.Wang0919@gmail.com
 *
 *
 * 获取设备信息工具类
 */
public class DeviceUtils {

    /**
     * @param context 上下文
     * @author jinghq
     * @category 获得IMSI
     */
    public static String getIMSI(Context context) {
        if (context == null)
            return "";

        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        if (imsi == null) {
            imsi = "";
        }

        return imsi;
    }

    /**
     * @param context 上下文
     * @author jinghq
     * @category 获得IMSI
     */
    public static String getIMEI(Context context) {
        if (context == null)
            return "";

        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        if (imei == null) {
            imei = "";
        }

        return imei;
    }

    /**
     * @param context 上下文
     * @author jinghq
     * @category dip比例参数
     */
    public static float GetScaleFloat(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * @param context  上下文
     * @param dipValue dip值
     * @author jinghq
     * @category dip转px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @param context 上下文
     * @author jinghq
     * @category 屏幕宽度px
     */
    public static int GetScreenWidth(Context context) {
        return (int) context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * @param context 上下文
     * @author jinghq
     * @category 屏幕高度px
     */
    public static int GetScreenHeight(Context context) {
        return (int) context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 测量显示屏幕数据 metric.widthPixels; // 屏幕宽度（像素） metric.heightPixels; // 屏幕高度（像素）
     * metric.density; // 屏幕密度（0.75 / 1.0 / 1.5） metric.densityDpi; //
     * 屏幕密度DPI（120 / 160 / 240）
     * <p/>
     * 需要注意的是，在一个低密度的小屏手机上 例如，一部240x320像素的低密度手机，如果运行上述代码， 获取到的屏幕尺寸是320x427。
     * Android系统会将240x320的低密度（120）尺寸转换为中等密度（160）对应的尺寸，因而
     * 需要在工程的AndroidManifest.xml文件中， 加入supports-screens节点，具体的内容如下：
     * <supports-screens android:smallScreens="true" android:normalScreens=
     * "true" android:largeScreens="true" android:resizeable="true"
     * android:anyDensity="true" />
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getScreenDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric;

    }

    /**
     * 返回设备SDCard路径,如果不存在则返回SDCardNotFoundException异常
     *
     * @return
     */
    public static String getSDCardPath() {
        String sdDir = null;
        if (isSdCardExist()) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取跟目录
        }
        return sdDir;

    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean isSdCardExist() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void call(Context context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent phoneIntent = new Intent("android.intent.action.CALL", uri);
        context.startActivity(phoneIntent);
    }
}
