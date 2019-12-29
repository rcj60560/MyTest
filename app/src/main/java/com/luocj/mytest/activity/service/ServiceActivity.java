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
        private String stringInfo;
        private BindService.MyBinder myBinder;
        private BindStartService.MyIBinder iBinder;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            myBinder = (BindService.MyBinder) service;
            if (service instanceof BindService.MyBinder) {
                myBinder = (BindService.MyBinder) service;
                stringInfo = myBinder.getStringInfo();
                tvResult.append("onServiceConnected" + stringInfo + "\n");
            } else if (service instanceof BindStartService.MyIBinder) {
                iBinder = (BindStartService.MyIBinder) service;
                String showInfo = iBinder.showInfo();
                Log.i(TAG, "onServiceConnected: " + showInfo);
            }
            Log.i(TAG, "onServiceConnected: " + stringInfo + ",serviceActivity:ThreadName:" + Thread.currentThread().getName() + ",ID:" + Thread.currentThread().getId() + ",name:" + name.getClassName());
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

    private int id = 0;

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
        id++;
        Intent intent = new Intent(ServiceActivity.this, MyIntentService.class);
        intent.putExtra("id", id);
        startService(intent);
    }

    /**
     * 混合模式
     * 先start service
     * 在bind service
     *
     * @param view
     */
    public void startService(View view) {
        Intent intent = new Intent(this, BindStartService.class);
        startService(intent);
    }

    public void bindService(View view) {
        Intent intent = new Intent(this, BindStartService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void stopService(View view) {
        Intent intent = new Intent(this, BindStartService.class);
        stopService(intent);
    }

    public void unBindService(View view) {
        unbindService(connection);
    }

    /**
     * 前台服务
     *
     * @param view
     */
    public void forgroundService(View view) {
        startService(new Intent(this, ForgroundService.class));
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
