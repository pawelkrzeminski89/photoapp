#include <jni.h>



extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_photoapp_helper_Keys_getPixabayApiKey(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("27594953-4713942dbd7c45a4720e0beea");
}