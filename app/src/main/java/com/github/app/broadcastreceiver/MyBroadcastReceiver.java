package com.github.app.broadcastreceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.app.utils.ToastUtils;

/**
 * 广播
 * Created by benny
 * on 2017/10/13.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    /**
     * 接收广播
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (BRUtils.NET_ACTION.equals(intent.getAction())) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                ToastUtils.toast("network is available=网络可用");

            } else {
                ToastUtils.toast("network is unavailable=网络不可用");
            }
        }
    }
}
