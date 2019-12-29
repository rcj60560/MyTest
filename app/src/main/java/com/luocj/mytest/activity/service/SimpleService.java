package com.luocj.mytest.activity.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class SimpleService extends Service {

    public static final String TAG = SimpleService.class.getSimpleName();
    private LocalBroadcastManager instance;
    private int count = 0;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1:
                    if (count < 20) {
                        count++;
                        Message mes = Message.obtain();
                        mes.what = 1;
                        handler.sendMessageDelayed(mes, 2000);
                        Log.i(TAG, "handleMessage:----------> " + count);
                        sendIntentInfo("handleMessage:----------> \" + count:" + count);
                    } else {
                        Log.i(TAG, "handleMessage: " + " 时间到 ");
                        sendIntentInfo("handleMessage: " + " 时间到 ");
                        count= 0;
                    }
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        instance = LocalBroadcastManager.getInstance(this);
        sendIntentInfo("onCreate:");
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: 当前线程名称:" + Thread.currentThread().getName());
        sendIntentInfo("onStart: 当前线程名称:" + Thread.currentThread().getName());
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessageDelayed(message, 2000);
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        Log.i(TAG, "startForegroundService: ");
        sendIntentInfo("startForegroundService: ");
        return super.startForegroundService(service);
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.i(TAG, "startService: ");
        sendIntentInfo("startService:  ");
        return super.startService(service);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: " + " ,flags :" + flags + " ,startid :" + startId);
        sendIntentInfo("onStartCommand:\" + \", flags :\" + flags + \", startid :\" + startId");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        handler.removeCallbacksAndMessages(null);
        sendIntentInfo("service 被暂停了");
    }


    private void sendIntentInfo(String str2) {
        Intent intent = new Intent(ServiceActivity.ACTION_SIMPLESERVICE);
        intent.putExtra("info", str2 + "\n");
        instance.sendBroadcast(intent);
    }
}
