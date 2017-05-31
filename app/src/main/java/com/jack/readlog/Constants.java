package com.jack.readlog;

/**
 * Created by Administrator on 2017/5/19.
 * 全局参数
 */

public class Constants {
    public static final String DEFAULT_OUT_PUT_NAME = "output";//输出文件夹名
    public static final String[] DEFAULT_PATHS = new String[]{
            "/sdcard/mtklog"//抓出sdcard/mtklog
            , "/data/anr"//抓出trace
            , "/data/aee_exp"//抓出data aee db
            , "/data/mobilelog"//抓出data mobilelog
            , "/data/core"//抓出NE core
            , "/data/tombstones"//抓出tombstones
            , "/data/rtt_dump*"//抓sf rtt
            , "/data/anr/sf_rtt"//
            , "/data/anr/anr_info.txt"//测试
            , "/data/anr/traces.txt"//测试
    };

    public static final String EB_TAG_REFRESH_MAIN_ACTIVITY = "EB_TAG_REFRESH_MAIN_ACTIVITY";
}
