package com.kaelthas.demo.ndk.test;

/**
 * Created by KaelThas.Wang on 2017/10/11.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class  AddNum{
    public native int add(int a,int b);
    static {
        System.loadLibrary("add");
    }
}
