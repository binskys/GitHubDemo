package com.github.app.broadcastreceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.github.app.utils.LogUtils;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class BRUtils {
    private static IntentFilter intentFilter;
    private static MyBroadcastReceiver mbr;
    public static String NET_ACTION="com.github.app.broadcastreceiver";

    /**
     * 注册广播
     *
     * @param context
     */
    public static void register(Context context, String action) {
        intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        mbr = new MyBroadcastReceiver();
        context.registerReceiver(mbr, intentFilter);
        LogUtils.d("MyBroadcastReceiver注册成功");

    }

    public static void register(Context context, IntentFilter intentFilter, MyBroadcastReceiver receiver) {
        if (receiver != null) {
            mbr = receiver;
        } else {
            mbr = new MyBroadcastReceiver();
        }
        context.registerReceiver(mbr, intentFilter);
        LogUtils.d("MyBroadcastReceiver注册成功");

    }

    /**
     * 注销广播
     *
     * @param context
     */
    public static void unRegister(Context context) {
        if (mbr == null) return;
        if (intentFilter == null) return;
        context.unregisterReceiver(mbr);
        mbr = null;
        intentFilter = null;
        LogUtils.d("MyBroadcastReceiver注消成功");
    }

    /**
     * 发送广播
     *
     * @param context
     */
    public static void send(Context context, String msg) {
        if (mbr == null) return;
        if (intentFilter == null) return;
        Intent intent = new Intent(msg);
        context.sendBroadcast(intent);
        LogUtils.d("广播发送成功:" + System.currentTimeMillis());
    }

}
