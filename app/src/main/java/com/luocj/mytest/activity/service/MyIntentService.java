package com.luocj.mytest.activity.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();
    public static final String ACTION_TYPE_THREAD = "action.type.thread";
    private LocalBroadcastManager instance;
    private boolean isRunning;
    private int count;
    private int id = -1;

    public MyIntentService() {
        super("myIntentService -------------------->>>>>>");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: ");
        try {
//            Thread.sleep(1000);
            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 5) {
                    isRunning = false;
                }
                Thread.sleep(1000);
                sendThreadStatus("线程运行中..." + ",id:" + id, count);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendThreadStatus(String status, int progress) {
        Intent intent = new Intent(MyIntentService.ACTION_TYPE_THREAD);
        intent.putExtra("status", status);
        intent.putExtra("progress", progress);
        instance.sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        instance = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: " + startId);
        id = intent.getIntExtra("id", -1);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
