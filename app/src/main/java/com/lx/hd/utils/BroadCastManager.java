package com.lx.hd.utils;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 广播管理类：注册广播、注销广播、发送广播
 *
 * @author weizh_000
 * @date 2016-8-29
 */
public class BroadCastManager {
    private boolean mReceiverTag = false; //广播接受者标识位
    private static BroadCastManager broadCastManager = new BroadCastManager();

    public static BroadCastManager getInstance() {
        return broadCastManager;
    }

    //注册广播接收者
    public void registerReceiver(Activity activity,
                                 BroadcastReceiver receiver, IntentFilter filter) {
        if (!mReceiverTag) {
            mReceiverTag = true;//设置广播标识位为true
            activity.registerReceiver(receiver, filter);
        }

    }

    //注销广播接收者
    public void unregisterReceiver(Activity activity,
                                   BroadcastReceiver receiver) {
        if (mReceiverTag) {
            if (receiver != null) {
                try {
                    mReceiverTag = false;//设置广播标识位为false
                    activity.unregisterReceiver(receiver);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("Receiver not registered")) {
                        // Ignore this exception. This is exactly what is desired
                    } else {
                        // unexpected, re-throw
                        throw e;
                    }
                }

            }
        }
    }

    //发送广播
    public void sendBroadCast(Activity activity, Intent intent) {
        activity.sendBroadcast(intent);
    }

}