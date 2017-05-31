package com.jack.readlog.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jack.readlog.Constants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/21.
 * 管理本地.xml文件中的值
 */
public class SharePreUtils {
    private static final String SHARE_PRE_FILENAME = "setting";
    private static final String SRC_PATHS = "paths";
    private static final String OUTPUT_PATH = "output_path";


    /**
     * 获取路径
     *
     * @param context
     * @return .xml文件中有值时，直接返回，否则返回默认值
     */
    public static Set<String> getPaths(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PRE_FILENAME, Context.MODE_PRIVATE);
        Set<String> paths = sharedPreferences.getStringSet(SRC_PATHS, null);
        if (paths == null || paths.size() <= 0) {
            paths = new HashSet<>();
            for (String defaultPath : Constants.DEFAULT_PATHS) {
                paths.add(defaultPath);
            }
            setPaths(context, paths);
        }
        return paths;
    }

    /**
     * 保存路径
     *
     * @param context
     * @param paths
     */
    public static void setPaths(Context context, Set<String> paths) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PRE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putStringSet(SRC_PATHS, paths);
        edit.commit();
    }

    /**
     * 获取输出路径
     *
     * @param context
     * @return
     */
    public static String getOutputPath(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PRE_FILENAME, Context.MODE_PRIVATE);
        String outputPath = sharedPreferences.getString(OUTPUT_PATH, null);
        if (outputPath == null || "".equals(outputPath)) {
            outputPath = Constants.DEFAULT_OUT_PUT_NAME;
            setOutputPath(context, outputPath);
        }
        return outputPath;
    }

    /**
     * 保存输出路径
     *
     * @param context
     * @param outputPath
     */
    public static void setOutputPath(Context context, String outputPath) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PRE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(OUTPUT_PATH, outputPath);
        edit.commit();
    }


}
