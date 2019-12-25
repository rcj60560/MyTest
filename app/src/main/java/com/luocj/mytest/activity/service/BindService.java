package com.luocj.mytest.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class BindService extends Service {

    private static final String TAG = BindService.class.getSimpleName();
    private LocalBroadcastManager instance;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        sendIntentInfo("onBind:");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        instance = LocalBroadcastManager.getInstance(this);
        sendIntentInfo("onCreate: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
        sendIntentInfo("onStart: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        sendIntentInfo("onStartCommand:  " + ",flags:" + flags + ",startId:" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        sendIntentInfo("onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendIntentInfo("onDestroy:");
        Log.i(TAG, "onDestroy: ");
    }

    public class MyBinder extends Binder {
        public String getStringInfo() {
            return "调用了这个方法";
        }
    }

    private void sendIntentInfo(String info) {
        Intent intent = new Intent(ServiceActivity.ACTION_BIND_SERVICE);
        intent.putExtra("info", info + "\n");
        instance.sendBroadcast(intent);
    }

}
