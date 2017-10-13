package com.github.app.service;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.github.app.MainActivity;
import com.github.app.service.GitHubService;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class ServiceUtils {
    /**
     * 开启服务
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, GitHubService.class);
        context.startService(intent);
    }

    /**
     * 停止服务
     * @param context
     */
    public static void stop(Context context) {
        Intent intent = new Intent(context, GitHubService.class);
        context.stopService(intent);
    }

    /**
     * Acitivity绑定服务
     * @param context
     * @param connection
     */
    public static void bind(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, GitHubService.class);
        context.bindService(intent, connection, context.BIND_AUTO_CREATE);
    }

    /**
     * 解除绑定
     * @param context
     * @param connection
     */
    public static void unBind(Context context, ServiceConnection connection) {
        context.unbindService(connection);
    }
}
