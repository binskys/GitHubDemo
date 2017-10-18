package com.github.app.utils;

import android.os.Environment;

public class ConfigUtils {
    public static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DEFAULT_CACHE_DIR = SDCARD_DIR + "/PLDroidPlayer";
    public static String PAUSE_TIME="pauseTime";
}
