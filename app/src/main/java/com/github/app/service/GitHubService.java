package com.github.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.github.app.MainActivity;
import com.github.app.R;
import com.github.app.utils.LogUtils;

/**
 * Created by benny
 * on 2017/10/13.
 * 服务
 */

public class GitHubService extends Service {
    private MyBinder mBinder = new MyBinder();

    /**
     * 未创建就调onCreate 否则直接 onStartCommand
     */
    @Override
    public void onCreate() {
        super.onCreate();
        try {
      //      Thread.sleep(60000);//触发ANR问题
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initNotice();//这里加上就会显示在前台
        LogUtils.d("onCreate");

    }

    private void initNotice() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)//设置小图标
                .setContentTitle("这是通知的标题")
                .setContentText("这是通知的内容")
                .setContentIntent(pendingIntent)//跳转
                .setAutoCancel(true)//点击后清除
                .build();
        notificationManager.notify(5, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开始执行后台任务
                LogUtils.d("开始执行后台任务");
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
    }
    /**
     * onBind 用于和Activity建立关联
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder{
        public void startDownload() {
            LogUtils.d("startDownload() executed");
            // 执行具体的下载任务
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 执行具体的下载任务
                    LogUtils.d("执行具体的下载任务");
                }
            }).start();
        }
    }
}
