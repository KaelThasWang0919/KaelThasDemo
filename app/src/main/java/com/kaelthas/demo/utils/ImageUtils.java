package com.kaelthas.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by KaelThas.Wang on 2016/10/20.
 * E_mail KaelThas.Wang0919@gmail.com
 * <p/>
 * <p/>
 * 图片处理工具类
 */
public class ImageUtils {

    /**
     * @category 是否是大图片
     */
    public static boolean isBigPicture(String path) {
        return FileUtils.isBigFile(path, 1048576);
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128
                : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * @author jinghq
     * @category 保存图片
     */
    public static void saveBitmap(File file, Bitmap mBitmap) {
        saveBitmap(file, mBitmap, 30);
    }

    /**
     * @category 保存图片
     */
    public static void saveBitmap(File file, Bitmap mBitmap, int quality) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.toString();
        }

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //

    /**
     * 缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * @category 压缩图片
     */
    public static Bitmap imageZoom(Bitmap bitMap) {
        Bitmap temp = bitMap;
        // 图片允许最大空间 单位：KB
        double maxSize = 30.00;
        // 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        byte[] b = bitmap2Bytes(temp);
        // 将字节换成KB
        double mid = b.length / 1024;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            temp = zoomImage(temp, temp.getWidth() / Math.sqrt(i), temp.getHeight() / Math.sqrt(i));
        }
        return temp;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * @category 将byte[]转换成bitmap
     */
    public static Bitmap byte2Bitmap(byte[] data) {
        if (data.length > 0) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } else {
            return null;
        }
    }

    /**
     * @category bitmap转成Drawable
     */
    public static Drawable bitmap2Drawable(Context context, Bitmap bm) {
        DisplayMetrics metrics = DeviceUtils.getScreenDisplayMetrics(context);
        Resources r = new Resources(context.getAssets(), metrics, null);
        Drawable bgDrawble2 = new BitmapDrawable(r, bm);
        return bgDrawble2;
    }

    /**
     * @category bitmap转成Drawable 该 方法 从 API 级别 4 开始已经废弃
     */
    @Deprecated
    public static Drawable bitmap2Drawable(Bitmap bm) {
        // ImageView imageView
        return new BitmapDrawable(bm);
    }

    /**
     * @category bitmap转成Drawable
     */
    public static Drawable bitmap2Drawable(Activity act, Bitmap bm) {
        DisplayMetrics metrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Resources r = new Resources(act.getAssets(), metrics, null);
        Drawable bgDrawble2 = new BitmapDrawable(r, bm);
        return bgDrawble2;
    }

    /**
     * @category drawable转成bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * @category bitmap转成byte[]
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 图片二合一 返回 Bitmap型 的Bitmap
     *
     * @param context
     * @param did1
     * @param did2
     * @return
     */
    public static Bitmap TwoToOnebm(Context context, int did1, int did2) {
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), did1);
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), did2);
        if (!b1.isMutable()) {
            // 设置图片为背景为透明
            b1 = b1.copy(Bitmap.Config.ARGB_8888, true);
        }
        Paint paint = new Paint();
        Canvas canvas = new Canvas(b1);
        canvas.drawBitmap(b2, 0, 0, paint);// 叠加新图b2
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return b1;
    }

    /**
     * 图片二合一 返回 bmDrawable型 的drawable
     *
     * @param context
     * @param did1
     * @param did2
     * @return
     */
    public static Drawable TwoToOneDrawable(Context context, int did1, int did2) {
        return ImageUtils.bitmap2Drawable(TwoToOnebm(context, did1, did2));
    }

    /**
     * 图片二合一 返回 LayerDrawable型 的drawable
     *
     * @param context
     * @param did1
     * @param did2
     * @return
     */
    public static LayerDrawable TwoToOneld(Context context, int did1, int did2) {
        LayerDrawable ld = null;
        try {
            Drawable sc_map_pin = context.getResources().getDrawable(did1);
            Drawable sc_map_pin_shadow = context.getResources().getDrawable(did1);
            Drawable[] array = new Drawable[]{sc_map_pin, sc_map_pin_shadow};
            ld = new LayerDrawable(array);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return ld;
    }

    /**
     * 根据行列数切分图片
     */
    public static Vector<Bitmap> splitBitmap(Bitmap src, int wcount, int hcount) {
        Vector<Bitmap> result = new Vector<Bitmap>();

        try {
            if (src != null) {
                int x = 0, y = 0;
                int w = src.getWidth() / wcount, h = src.getHeight() / hcount;

                for (int i = 0; i < hcount; i++) {
                    y = i * h;
                    x = 0;

                    for (int j = 0; j < wcount; j++) {
                        result.add(Bitmap.createBitmap(src, x, y, w, h));
                        x += w;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @return bitmap
     * @author jinghq
     * @category 从SD卡获取文件为图片
     */
    public static Bitmap getBitmap(File file) {
        if (file == null) {
            return null;
        }

        if (file.exists()) {
            return getBitmap(file.getParent(), file.getName());
        } else {
            return null;
        }
    }

    /**
     * @category 从SD卡上获取一个图片
     */
    public static Bitmap getBitmap(String filePath, String fileName) {
        if (DeviceUtils.isSdCardExist()) {// 检查SD卡可用

            if (DeviceUtils.getSDCardPath() == null)
                return null;

            File root = new File(filePath);
            if (FileUtils.checkDir(root, true)) {
                File file = new File(root.getPath() + "/" + fileName);
                if (file.exists()) {
                    return BitmapFactory.decodeFile(file.getPath());
                } else {
                    return null;
                }

            }
        }

        return null;
    }

    /**
     * 获得圆角图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 获得_圆形图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        final float roundPX = bitmap.getWidth() / 2;
        return getRoundedCornerBitmap(bitmap, roundPX);
    }

    /**
     * 获得_带倒影的图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 黑白处理
     *
     * @param bitmap
     * @return
     */
    public static final Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }
}
