package com.luocj.mytest.activity.asynctask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.luocj.mytest.R;
import com.luocj.mytest.widget.MyAsyncTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public class AsyncTaskActivity extends AppCompatActivity {

    private static final String TAG = AsyncTaskActivity.class.getSimpleName();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " + msg.obj);
        }
    };

    private SonHandler sonHandler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        //test

        initData();


    }

    private void initData() {
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message obtain = Message.obtain();
                        obtain.obj = "子线程给主线程发消息";
                        mHandler.sendMessage(obtain);
                    }
                }).start();

            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
//                        Looper.prepare();
//                        sonHandler = new SonHandler(Looper.getMainLooper());
//                        Message obtain = Message.obtain();
//                        obtain.obj = "子线程的handler";
//                        sonHandler.sendMessage(obtain);
//                        Looper.loop();

                        Message obtain = Message.obtain();
                        obtain.obj = "----------------.";
                        //
                        Handler handler = new Handler(Looper.getMainLooper()){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                Log.e(TAG, "handleMessage: " + msg.obj );
                            }
                        };
                        handler.sendMessage(obtain);
                    }
                }).start();
            }
        });
    }

    public void asynctask(View view) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                Message obtain = Message.obtain();
                obtain.what = 1;
                mHandler.sendMessage(obtain);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private class SonHandler extends Handler {

        public SonHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " +msg.obj);
        }
    }
}
