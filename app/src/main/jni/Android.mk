LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE    := libadd
LOCAL_SRC_FILES := AddNum.c

include $(BUILD_SHARED_LIBRARY)




