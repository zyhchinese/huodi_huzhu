// IWebSocketService.aidl
package com.lx.hd;

// Declare any non-default types here with import statements

interface IWebSocketService {

   void setHost(String host);
   void sendMessage(String msg);
   void connect();
   void disconnect();
}
