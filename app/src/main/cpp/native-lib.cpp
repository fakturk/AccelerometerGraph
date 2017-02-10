#include <jni.h>
#include <string>

extern "C"
jstring
Java_netlab_fakturk_accelerometergraph_AccelerometerGraphActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
