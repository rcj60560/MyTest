package com.luocj.mytest.activity.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.luocj.mytest.R;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = ServiceActivity.class.getSimpleName();
    public static final String ACTION_SIMPLESERVICE = "SIMPLE_SERVICE";
    public static final String ACTION_BIND_SERVICE = "ACTION_BIND_SERVICE";
    private ServiceConnection connection = new ServiceConnection() {
        private BindService.MyBinder myBinder;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (BindService.MyBinder) service;
            String stringInfo = myBinder.getStringInfo();
            tvResult.append("onServiceConnected" + stringInfo + "\n");
            Log.i(TAG, "onServiceConnected: " + stringInfo);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
            tvResult.append("onServiceDisconnected" + "\n");
            myBinder = null;
        }
    };
    private LocalBroadcastManager localBroadcastManager;
    private MyBroadcastReceive myBroadcastReceive;
    private TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myBroadcastReceive = new MyBroadcastReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyIntentService.ACTION_TYPE_THREAD);
        intentFilter.addAction(ServiceActivity.ACTION_SIMPLESERVICE);
        intentFilter.addAction(ServiceActivity.ACTION_BIND_SERVICE);
        localBroadcastManager.registerReceiver(myBroadcastReceive, intentFilter);

        tvResult = findViewById(R.id.tv_result);


    }

    public void doStartService(View view) {
        Intent intent = new Intent(this, SimpleService.class);
        startService(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//            Log.i(TAG, "doStartService: " + "前台服务");
//        } else {
//            stopService(intent);
//            Log.i(TAG, "doStartService: " + "启动了服务");
//        }
    }

    public void doStop(View view) {
        Intent intent = new Intent(this, SimpleService.class);
        stopService(intent);
    }

    public void doBindService(View view) {
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void doUnBindService(View view) {
        unbindService(connection);
    }

    public void doIntentService(View view) {
        Intent intent = new Intent(ServiceActivity.this, MyIntentService.class);
        stopService(intent);
    }

    private class MyBroadcastReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String info = intent.getStringExtra("info");
            switch (action) {
                case MyIntentService.ACTION_TYPE_THREAD:
                    Log.i(TAG, "onReceive: " + intent.getStringExtra("status"));
                    break;

                case ServiceActivity.ACTION_SIMPLESERVICE:
                    tvResult.append(info);
                    break;

                case ServiceActivity.ACTION_BIND_SERVICE:
                    tvResult.append(info);
                    Log.i(TAG, "onReceive: " + info);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(myBroadcastReceive);
    }
}
