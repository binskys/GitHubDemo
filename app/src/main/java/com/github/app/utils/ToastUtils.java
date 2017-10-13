package com.github.app.utils;

import android.widget.Toast;

import com.github.app.MyApp;

/**
 * Toast统一管理类
 * Created by chenjunshan on 2016/7/5.
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 短时间Toast
     *
     * @param msg
     */
    public static void toast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 长时间Toast
     *
     * @param msg
     */
    public static void toastlong(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
