package com.lx.hd.webscket;


import com.lx.hd.IWebSocketService;
import com.lx.hd.webscket.OnWebSocketListener;
import com.lx.hd.webscket.SocketCallBack;
import com.lx.hd.webscket.WebSocketClientService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import java.net.Socket;

import static android.content.Context.BIND_AUTO_CREATE;

/*
 * Created by JiangJie on 2017/2/14.
 */

public class WebSocketClient {
    SocketCallBack callBack;
    private Context context;
    private String host;
    private int restConnectTime = 10;//重新连接时间间隔（秒）
    private int restConnectNum = 3;//重试次数
    private boolean isRestConnect = true;
    private OnWebSocketListener onWebSocketListener;
    private IWebSocketService socket;
    private Handler mHandler = new Handler();
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            socket = IWebSocketService.Stub.asInterface(service);
            callBack.sussess();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            socket = null;
        }
    };
    private Runnable restRun = new Runnable() {
        @Override
        public void run() {
            connect();
        }
    };

    public WebSocketClient(Context context, String host,SocketCallBack callBack) {
        this.callBack=callBack;
        this.context = context;
        this.host = host;
        context.bindService(new Intent(context, WebSocketClientService.class), conn, BIND_AUTO_CREATE);
    }

    public void setRestConnectTime(int second) {
        restConnectTime = second;
    }

    public void setRrestNum(int num) {
        restConnectNum = num;
    }

    public void connect() {
        registerReceiver();
        if (socket != null) {
            try {
                socket.setHost(host);
                socket.connect();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            context.bindService(new Intent(context, WebSocketClientService.class), conn, BIND_AUTO_CREATE);
            try {
                socket.setHost(host);
                socket.connect();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (socket != null) {
            try {
                isRestConnect = false;
                unRegisterReceiver();
                socket.disconnect();

                try {
                    context.unbindService(conn);
                } catch (Exception e) {
                }

                mHandler.removeCallbacks(restRun);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            socket.sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setOnWebSocketListener(OnWebSocketListener onWebSocketListener) {
        this.onWebSocketListener = onWebSocketListener;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WebSocketClientService.MESSAGE_ACTION);
        intentFilter.addAction(WebSocketClientService.SOCKET_STATE_CONNECTED);
        intentFilter.addAction(WebSocketClientService.SOCKET_STATE_DISCONNECTED);
        context.registerReceiver(mReceiver, intentFilter);
    }

    private void unRegisterReceiver() {
        context.unregisterReceiver(mReceiver);
        // 注销服务
        if (conn != null) {
            context.unbindService(conn);
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WebSocketClientService.MESSAGE_ACTION)) {
                String stringExtra = intent.getStringExtra("message");
                if (onWebSocketListener != null) {
                    onWebSocketListener.onResponse(stringExtra);
                }
            }
            if (action.equals(WebSocketClientService.SOCKET_STATE_CONNECTED)) {
                if (onWebSocketListener != null) {
                    onWebSocketListener.onConnected();
                }
            }
            if (action.equals(WebSocketClientService.SOCKET_STATE_DISCONNECTED)) {
                if (onWebSocketListener != null) {
                    if (isRestConnect) {
                        if (restConnectNum >= 0) {
                            mHandler.postDelayed(restRun, restConnectTime * 1000);
                        }
                        restConnectNum--;
                    }
                    onWebSocketListener.onDisConnected();
                }
            }
        }
    };
}
