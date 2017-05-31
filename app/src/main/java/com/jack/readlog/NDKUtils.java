package com.jack.readlog;

/**
 * Created by Administrator on 2017/5/19.
 */

public class NDKUtils {
    static {
        System.loadLibrary("ndkUtils");
    }

    public static native String execute(String command);




}
