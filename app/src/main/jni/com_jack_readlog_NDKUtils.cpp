//
// Created by Administrator on 2017/5/19.
//

#include "com_jack_readlog_NDKUtils.h"
#include <stdlib.h>
//#include <string>
//using namespace std;

/*
 * Class:     com_jack_readlog_NDKUtils
 * Method:    exeute
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_jack_readlog_NDKUtils_execute
  (JNIEnv *env, jclass clazz, jstring jstr){
        const char* cmdStr = env->GetStringUTFChars(jstr, 0);

        /**
         * 备用命令
         */
        const char * cmd = "cp /sdcard/A/test.txt /sdcard/B";
        const char * cmd1 = "cp /data/anr/anr_info.txt /sdcard/B";
        const char * cmd2 = "cp -r /data/anr /sdcard/B";
        const char * cmd3 = "cp -r /sdcard/A /sdcard/B";

        const char * cmd4 = "/sdcard/B/shell.sh";//运行.sh文件，没有权限，怎么办？

        const char * cmd5 = "cp -r /data/data /sdcard/B";
        system(cmdStr);

        const char *result = "成功1";

        return env->NewStringUTF(cmdStr);
  }


