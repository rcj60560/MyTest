package com.luocj.net;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.luocj.net.model.HttpParams;
import com.luocj.net.request.GetRequest;
import com.luocj.net.utils.HttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Net {
    public static final long DEFAULT_MILLISECONDS = 60 * 1000;
    public Application context;
    private Handler mDelivery;
    private static OkHttpClient mOkHttpClient;

    private HttpParams mCommonParams;

    private Net() {
        mDelivery = new Handler(Looper.getMainLooper());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Net.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.readTimeout(Net.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(Net.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        mOkHttpClient = builder.build();

    }

    public static Net getInstance() {
        return NetHolder.holder;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public HttpParams getCommonsParams() {
        return mCommonParams;
    }


    private static class NetHolder {
        private static Net holder = new Net();
    }

    public Net init(Application app) {
        context = app;
        return this;
    }

    public Context getContext() {
        HttpUtils.checkNotNull(context, " 在app 里面初始化 ");
        return context;
    }

    /**
     * get请求
     */

    public static <T> GetRequest<T,R> get(String url) {
        return new GetRequest<T,R>(url);
    }
}
