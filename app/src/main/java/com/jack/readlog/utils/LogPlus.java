package com.jack.readlog.utils;

import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by John on 2015/6/6.
 */
public class LogPlus {

    //级别低于设置的则不会打印
    private static final int CURRENT_LOG_LEVEL = Log.VERBOSE;
    private static final String PREFIX = "Jack_";

    /**
     * 注意，如果tag是"IMS"开头的（暂时只知道这一个），log会不打印
     * @param tag
     * @param msg
     * @param tr
     */
    public static void v(@Nullable String tag, String msg, @Nullable Throwable tr) {
        log(Log.VERBOSE, tag, msg, tr);
    }
    
    public static void v(String msg) {
        log(Log.VERBOSE, null, msg, null);
    }

    /**
     * 注意，如果tag是"IMS"开头的（暂时只知道这一个），log会不打印
     * @param tag
     * @param msg
     * @param tr
     */
    public static void d(@Nullable String tag, String msg, @Nullable Throwable tr) {
        log(Log.DEBUG, tag, msg, tr);
    }
    
    public static void d(String msg) {
        log(Log.DEBUG, null, msg, null);
    }

    /**
     * 注意，如果tag是"IMS"开头的（暂时只知道这一个），log会不打印
     * @param tag
     * @param msg
     * @param tr
     */
    public static void i(@Nullable String tag, String msg, @Nullable Throwable tr) {
        log(Log.INFO, tag, msg, tr);
    }

    public static void i(String msg) {
        log(Log.INFO, null, msg, null);
    }

    /**
     * 注意，如果tag是"IMS"开头的（暂时只知道这一个），log会不打印
     * @param tag
     * @param msg
     * @param tr
     */
    public static void w(@Nullable String tag, String msg, @Nullable Throwable tr) {
        log(Log.WARN, tag, msg, tr);
    }

    public static void w(String msg) {
        log(Log.WARN, null, msg, null);
    }

    /**
     * 注意，如果tag是"IMS"开头的（暂时只知道这一个），log会不打印
     * @param tag
     * @param msg
     * @param tr
     */
    public static void e(@Nullable String tag, String msg, @Nullable Throwable tr) {
        log(Log.ERROR, tag, msg, tr);
    }

    public static void e(String msg) {
        log(Log.ERROR, null, msg, null);
    }

    private static void log(int logLevel, String tag, String msg, Throwable tr) {
        if (logLevel < CURRENT_LOG_LEVEL) {
            return;
        }
        
        StackTraceElement e = Thread.currentThread().getStackTrace()[4];
        String methodName = e.getMethodName();
        String fileName = e.getFileName();
        int lineNum = e.getLineNumber();
        msg = methodName + '(' + fileName + ':' + lineNum + ')' + msg;

        // 注意，如果tag是"IMS"开头的（暂时只知道这一个），log会不打印，所以加个前缀"_"
        // 参考这里 http://stackoverflow.com/a/36469141/5324526
        if (tag == null || tag.length() == 0 || tag.trim().length() == 0) {
            String className = e.getClassName();
            int index = className.lastIndexOf('.') + 1;
            className = className.substring(index);
            tag = PREFIX + className;
        } else {
            tag = PREFIX + tag;
        }

        //这里不对tr做判空处理，交给Log处理
        switch (logLevel) {
            case Log.VERBOSE:
                Log.v(tag, msg, tr);
                break;
            case Log.DEBUG:
                Log.d(tag, msg, tr);
                break;
            case Log.INFO:
                Log.i(tag, msg, tr);
                break;
            case Log.WARN:
                Log.w(tag, msg, tr);
                break;
            case Log.ERROR:
                Log.e(tag, msg, tr);
                break;
        }
    }
}
