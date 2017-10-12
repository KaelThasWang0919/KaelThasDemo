//
// Created by 王晓明 on 2017/10/11.
//
#include "com_kaelthas_demo_ndk_test_AddNum.h"

JNIEXPORT jint JNICALL Java_com_kaelthas_demo_ndk_test_AddNum_add
  (JNIEnv *env, jobject thiz, jint a, jint b){
        return a+b;
  }
