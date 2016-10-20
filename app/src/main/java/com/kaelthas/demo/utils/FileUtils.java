package com.kaelthas.demo.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * Created by KaelThas.Wang on 2016/10/20.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class FileUtils {
    public interface FileListener {
        public void onFileWriteEnd(String tag, File file);
    }

    public static void saveFile(Bitmap bm, String tag, FileListener listener) {
        File file = null;
        try {
            String path = Environment.getExternalStorageDirectory() + "/winekar/cache/";
            String name = tag + ".jpg";

            isPathExistWithCreate(path);

            file = new File(path, name);
            FileOutputStream fos = new FileOutputStream(file);
            String info = "I am a chinanese!";
            fos.write(info.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listener != null) {
            if (file != null) {
                if (file.exists()) {
                    listener.onFileWriteEnd(tag, file);
                } else {
                    listener.onFileWriteEnd(tag, null);
                }
            } else {
                listener.onFileWriteEnd(tag, null);
            }
        }

    }

    /**
     * 将Bitmap转换成文件 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    @SuppressWarnings("resource")
    public static boolean isBigFile(String path, long size) {
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(path);
            long len = fis.available();
            if (len > size) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }

    }

    /**
     * @category 保存图片
     */
    public static boolean saveBitmap(File file, Bitmap mBitmap) {
        boolean isSuccess = false;
        FileOutputStream fOut = null;
        try {
            file.createNewFile();
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            isSuccess = false;
            e.printStackTrace();

        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            isSuccess = mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

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
        return isSuccess;
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

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File createFileInSDCard(String fileName, String dir) throws IOException {
        File file = new File(SDCardRoot + dir + File.separator + fileName);
        System.out.println("file---->" + file);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dir
     */
    public static File creatSDDir(String dir) {
        File dirFile = new File(SDCardRoot + dir + File.separator);
        System.out.println(dirFile.mkdirs());
        return dirFile;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String fileName, String path) {
        File file = new File(SDCardRoot + path + File.separator + fileName);
//        HLog.d("MFileUtils isFileExist", "path:" + file.getPath() + "\n name:" + file.getName());
        return file.exists();
    }

    /**
     * 获取文件大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS, String whennull) {// 转换文件大小

        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS <= 0) {
            fileSizeString = whennull;
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static boolean RecursionDeleteFile(File file) {
        boolean isSuccess = false;
        if (file.isFile()) {
            file.delete();
            isSuccess = true;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                isSuccess = true;
            }
            for (File f : childFile) {
                isSuccess = RecursionDeleteFile(f);
            }
            isSuccess = file.delete();
        }
        return isSuccess;
    }

    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     * @param path
     * @return 不存在或者已删除则返回true
     */
    public static boolean DeleteFileExist(String fileName, String path) {
        if (!isFileExist(fileName, path)) {
            return true;
        } else {
            File file = new File(SDCardRoot + path + File.separator + fileName);
            return file.delete();
        }

    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param path
     * @param fileName
     * @param input
     * @param isdeleteold 如果文件已存在是否删除
     * @return
     */
    public static File write2SDFromInput(String path, String fileName, InputStream input, boolean isdeleteold) {

        File file = null;
        OutputStream output = null;
        if (isdeleteold) {
            DeleteFileExist(fileName, path);
        }
        try {
            creatSDDir(path);
            file = createFileInSDCard(fileName, path);

            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int temp;
            while ((temp = input.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param path
     * @param fileName
     * @param input
     * @return
     */
    public static File write2SDFromInput(String path, String fileName, InputStream input) {
        return write2SDFromInput(path, fileName, input, false);
    }

    /**
     * 将一个String里面的数据写入到SD卡中
     *
     * @param path       路径
     * @param fileName   文件名
     * @param saveString 要保存的String
     * @return
     */
    public static File write2SDFromString(String path, String fileName, String saveString, boolean isdeleteold) {

        File file = null;
        OutputStream output = null;
        if (isdeleteold) {
            DeleteFileExist(fileName, path);
        }
        try {
            creatSDDir(path);
            file = createFileInSDCard(fileName, path);
            output = new FileOutputStream(file);
            output.write(saveString.getBytes());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 将一个String里面的数据写入到SD卡中
     *
     * @param path
     * @param fileName
     * @param saveString
     * @return
     */
    public static File write2SDFromString(String path, String fileName, String saveString) {
        return write2SDFromString(path, fileName, saveString, false);
    }

    /**
     * * 读取文件内容 *
     *
     * @param filePath
     * @return 文件内容 有异常则返回空
     */
    public static String readFile(String filePath, String fileName) {
        String str = "";
        FileInputStream inStream = null;
        ByteArrayOutputStream stream = null;
        try {
            File readFile = new File(SDCardRoot + filePath + File.separator + fileName);
            if (!readFile.exists()) {
                return null;
            }
            inStream = new FileInputStream(readFile);
            stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            str = stream.toString();
            stream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                } else {
                    return null;
                }
                if (inStream != null) {
                    inStream.close();
                } else {
                    return null;
                }

            } catch (IOException e) {
                return null;
            }

        }
        return str;
    }

    /**
     * @param file 要判断的文件
     * @return true文件存在, false文件不存在
     * @author jinghq
     * @category 判断文件是否存在
     */
    public static boolean isFileExist(File file) {
        if (file != null) {
            return file.exists();
        } else {
            return false;
        }
    }

    /**
     * @category 判断目录路径是否存在，不存在着则创建
     */
    public static boolean isPathExistWithCreate(String path) {
        return isPathExist(path, true);
    }

    public static boolean isPathExist(String path) {
        return isPathExist(path, false);
    }

    /**
     * @param path 目录路径
     * @author jinghq
     * @category 判断目录是否存在
     * @isCreate 目录不存在是否创建
     */
    public static boolean isPathExist(String path, boolean isCreate) {
        if (TextUtils.notEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                return true;
            } else {
                if (isCreate) {
                    file.mkdirs();// 创建目录
                    if (!isPathExist(path)) {
                        mkdirs(path);
                    }
                    return isPathExist(path);
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * @category mkdirs自己的实现
     */
    public static void mkdirs(String path) {

        String[] paths = path.split("/");
        String realPath = "";
        for (String p : paths) {
            if (TextUtils.notEmpty(p)) {
                realPath += "/" + p;
                checkDir(new File(realPath), true);
            }
        }

    }

    /**
     * @category 检查文件夹是否存在
     */
    public static boolean checkDir(File file, boolean isCreate) {
        if (file.exists() && file.isDirectory()) {
            return true;
        } else {
            if (isCreate) {
                return file.mkdir();
            } else {
                return false;
            }
        }
    }

    public static String getCurrentTimeFileName() {
        return System.currentTimeMillis() + "";
    }

    public static String getMd5UrlName(String url) {
        return CryptoUtils.md5(url);
    }

    public static String addPathName(String path, String name) {

        String place = "";
        if (path.endsWith("/")) {
            place = "";
        } else {
            place = "/";
        }

        String result = path + place + name;
        result = result.replace("\\", "/");
        result = result.replace("\\/", "/");
        result = result.replace("//", "/");

        return result;
    }
}
