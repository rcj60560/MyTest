package com.luocj.mytest.activity.impl;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class AndoridtoJs extends Object {
    private static final String TAG = AndoridtoJs.class.getSimpleName();

    @JavascriptInterface
    public void hello(String msg) {
        Log.i(TAG, "hello: " + msg);
    }
}
