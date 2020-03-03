package com.luocj.mytest;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;

import androidx.multidex.MultiDex;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);

        Utils.init(this);

        MultiDex.install(this);
    }
}
