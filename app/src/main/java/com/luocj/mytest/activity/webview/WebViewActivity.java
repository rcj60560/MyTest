package com.luocj.mytest.activity.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.luocj.mytest.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        findViewById(R.id.btnjs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //原生调用js方法
                webview.loadUrl("javascript:javaCallJs(" + "'Message From Java'" + ")");
            }
        });

        webview = findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/test.html");
//        webview.addJavascriptInterface(new MainInterface(), "main");
    }

    @JavascriptInterface
    public void jsCallJava(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
