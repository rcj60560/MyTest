package com.luocj.mytest.activity.impl;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class MainInterface {

    @JavascriptInterface
    public void jsCallJava(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
