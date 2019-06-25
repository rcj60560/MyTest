package com.luocj.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.luocj.mytest.R;
import com.luocj.net.Net;
import com.luocj.net.call.Callback;
import com.luocj.net.request.Request;
import com.luocj.net.response.Response;

public class NetActivity extends AppCompatActivity {
    private static final String TAG = NetActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
    }

    public void doGet(View view) {
        Net.<String>get("http://www.baidu.com")
                .execute(new Callback<String>() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        Log.i(TAG, "onStart: ");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onFinish() {
                        Log.i(TAG, "onFinish: ");
                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        return null;
                    }
                });
    }
}
