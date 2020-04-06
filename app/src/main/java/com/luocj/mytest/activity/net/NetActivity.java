package com.luocj.mytest.activity.net;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.updatelib.update.UpdateService;
import com.luocj.mytest.R;
import com.luocj.mytest.model.ArticleModel;
import com.luocj.mytest.utils.NotificationHelper;
import com.luocj.mytest.widget.MyAsyncTask;
import com.luocj.net.Net;
import com.luocj.net.call.StringCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetActivity extends AppCompatActivity {
    private static final String TAG = NetActivity.class.getSimpleName();
    private static final int CODE = 0x01;
//    public static final String API_URL = "https://wanandroid.com";
    public static final String API_URL = "http://123.56.232.18:8080/serverdemo/";

    String apkurl = "http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk";
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        iv = findViewById(R.id.iv);

        checkSDCardPermission();
    }

    private void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE);
        }
    }

    public void doGet(View view) {
//        Net.<String>get("http://www.baidu.com")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        Log.i(TAG, "onSuccess: " + response);
//                    }
//                });

        ExecutorService executorService = Executors.newCachedThreadPool();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .get()
                .url("http://www.baidu.com")
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: ");
            }
        });
    }

    public void doDuanDian(View view) {
        String url = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/00814b5dad9b54cc804466369c8cb18f23e23823f";
//        DownloadTask downloadTask = new DownloadTask();
//        downloadTask.execute("http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/00814b5dad9b54cc804466369c8cb18f23e23823f");
    }

    public void doUpdate(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setCancelable(false)
                .setMessage("发现新版本")
                .setNegativeButton("去更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String folder = Environment.getExternalStorageDirectory() + File.separator + "aaaaaa" + File.separator;
                        String fileName = "test.apk";

                        NotificationHelper.getInstance().init(NetActivity.this);
                        OkGo.<File>get(apkurl)
                                .execute(new FileCallback(folder, fileName) {
                                    @Override
                                    public void onStart(Request<File, ? extends Request> request) {
                                        super.onStart(request);
                                        NotificationHelper.getInstance()
                                                .showUpDateNotification("开始下载", "11", 0);
                                    }

                                    @Override
                                    public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                                        Log.i(TAG, "onSuccess: ");
                                    }

                                    @Override
                                    public void downloadProgress(Progress progress) {
                                        super.downloadProgress(progress);
                                        int numer = (int) (progress.currentSize * 1.0f / progress.totalSize * 100);
                                        Log.i(TAG, "downloadProgress: Total size : " + progress.totalSize +
                                                " \n ,, current size :" + progress.currentSize + " ---> numver :" + numer);
                                        NotificationHelper.getInstance().showUpDateNotification("正在下载", "xiazia hzong ", numer);
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();
                                    }

                                    @Override
                                    public void onError(com.lzy.okgo.model.Response<File> response) {
                                        super.onError(response);
                                        Log.i(TAG, "onError: ");
                                    }
                                });
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                ToastUtils.showShort("未获取权限");
            }
        }
    }

    public void doAsyncTask(View view) {
        MyAsyncTask myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(null, null, null);
//        myAsyncTask.executeOnExecutor(null, null, null,null);
    }

    public void doGlide(View view) {
        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
//        String url = "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s160-c/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);


    }

    public void doInstall(View view) {
        ActivityUtils.startActivity(InstallActivity.class);
    }

    public void doUpdate1(View view) {
        UpdateService.startService(NetActivity.this);
    }

    public void doRetrofit(View view) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                Log.d(TAG, "intercept: " + request.toString());
                //打印服务端响应数据
                Response response = chain.proceed(request);
                String content = response.body().string();
                MediaType mediaType = response.body().contentType();
                Log.d(TAG, "intercept: response" + content);
                Log.d(TAG, "intercept: mediaType" + mediaType.toString());

                return response
                        .newBuilder()
                        .body(ResponseBody.create(mediaType, content))
                        .build();
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        API api = retrofit.create(API.class);

//        retrofit2.Call<ArticleModel> call = api.getWXarticle();
//        call.enqueue(new retrofit2.Callback<ArticleModel>() {
//            @Override
//            public void onResponse(retrofit2.Call<ArticleModel> call, retrofit2.Response<ArticleModel> response) {
//                Log.d(TAG, "onResponse: ");
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<ArticleModel> call, Throwable t) {
//                Log.d(TAG, "onFailure: ");
//            }
//        });

        Observable<ArticleModel> observable = api.getWXarticleOb();
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(ArticleModel articleModel) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: " + Thread.currentThread().getName());
                    }
                });

//        Observable<ArticleModel> observable = api.getWXarticleOb();
//        observable.subscribeOn(Schedulers.io())
//                .subscribe(new Observer<ArticleModel>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "onCompleted: " + Thread.currentThread().getName());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + Thread.currentThread().getName());
//                    }
//
//                    @Override
//                    public void onNext(ArticleModel articleModel) {
//                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
//                    }
//                });
    }

    public void doGetImages(View view) {
//        String imageUrl = "http://127.0.0.1:8080/file/image/1.jpg";
//        String imageUrl = "http://192.168.1.4:8080/file/image/1.jpg";
//        Glide.with(this)
//                .load(imageUrl)
//                .error(R.mipmap.ic_launcher)
//                .into(iv);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                Log.d(TAG, "intercept: " + request.toString());
                //打印服务端响应数据
                Response response = chain.proceed(request);
                String content = response.body().string();
                MediaType mediaType = response.body().contentType();
                Log.d(TAG, "intercept: response" + content);
                Log.d(TAG, "intercept: mediaType" + mediaType.toString());
                return response
                        .newBuilder()
                        .body(ResponseBody.create(mediaType, content))
                        .build();
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        API api = retrofit.create(API.class);

        api.getSave().enqueue(new retrofit2.Callback<Object>() {
            @Override
            public void onResponse(retrofit2.Call<Object> call, retrofit2.Response<Object> response) {
                Log.i(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(retrofit2.Call<Object> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
        
    }
}
