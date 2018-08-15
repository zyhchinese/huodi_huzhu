package com.lx.hd.webscket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.lx.hd.IWebSocketService;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
/*
 * Created by JiangJie on 2017/2/14.
 */

public class WebSocketClientService extends Service {

    public static final String MESSAGE_ACTION = "android.socket.message_action";
    public static final String SOCKET_STATE_CONNECTED = "android.socket.connected_action";
    public static final String SOCKET_STATE_DISCONNECTED = "android.socket.disconnected_action";
    private static String HOST;
    private WebSocketConnection mConnect = new WebSocketConnection();
    private IWebSocketService.Stub service = new IWebSocketService.Stub() {
        @Override
        public void setHost(String host) throws RemoteException {
            HOST = host;
        }

        @Override
        public void sendMessage(String msg) throws RemoteException {
            sendMsg(msg);
        }

        @Override
        public void connect() throws RemoteException {
            createConnect();
        }

        @Override
        public void disconnect() throws RemoteException {
            socketDisConnect();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return service;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socketDisConnect();
    }

    private void createConnect() {
        if (mConnect == null) {
            mConnect = new WebSocketConnection();
        }
        try {
            mConnect.connect(HOST, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    sendAction(SOCKET_STATE_CONNECTED);
                }

                @Override
                public void onClose(int code, String reason) {
                    sendAction(SOCKET_STATE_DISCONNECTED);
                }

                @Override
                public void onTextMessage(String payload) {
                    sendMessageAction(MESSAGE_ACTION, payload);
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    private void socketDisConnect() {
        if (mConnect != null && mConnect.isConnected()) {
            mConnect.disconnect();
            mConnect = null;
        }
    }

    private void sendMsg(String msg) {
        if (mConnect != null) {
            mConnect.sendTextMessage(msg);
        }
    }

    private void sendAction(String action) {
        sendBroadcast(new Intent(action));
    }

    private void sendMessageAction(String action, String msg) {
        Intent intent = new Intent(action);
        intent.putExtra("message", msg);
        sendBroadcast(intent);
    }
}
