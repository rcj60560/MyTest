package com.luocj.mytest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.luocj.mytest.activity.asynctask.AsyncTaskActivity;
import com.luocj.mytest.activity.bitmap.YouHuaActivity;
import com.luocj.mytest.activity.customview.CustomViewActivity;
import com.luocj.mytest.activity.net.NetActivity;
import com.luocj.mytest.activity.other.LazyLoadActivity;
import com.luocj.mytest.activity.project.ProjectActivity;
import com.luocj.mytest.activity.recyclerview.RecyclerviewActivity;
import com.luocj.mytest.activity.saveapp.SaveAppActivity;
import com.luocj.mytest.activity.service.ServiceActivity;
import com.luocj.mytest.activity.webview.WebViewActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //test
    //mac commit
    //我是dev1 分支
    //de1 第二次提交
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onDenied(data -> Log.i(TAG, "onAction: " + "denied"))
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onAction: " + " granted ");
                    }
                })
                .start();

    }

    public void doRecyclerview(View view) {
        ActivityUtils.startActivity(RecyclerviewActivity.class);
    }

    public void doNet(View view) {
        ActivityUtils.startActivity(NetActivity.class);
    }

    public void doLazyLoad(View view) {
        ActivityUtils.startActivity(LazyLoadActivity.class);
    }

    public void doService(View view) {
        ActivityUtils.startActivity(ServiceActivity.class);
    }

    public void doView(View view) {
        ActivityUtils.startActivity(CustomViewActivity.class);
    }

    public void doYouHua(View view) {
        ActivityUtils.startActivity(YouHuaActivity.class);
    }

    public void doProject(View view) {
        ActivityUtils.startActivity(ProjectActivity.class);
    }

    public void doWebView(View view) {
        ActivityUtils.startActivity(WebViewActivity.class);
    }

    public void asynctaskActivity(View view) {
        ActivityUtils.startActivity(AsyncTaskActivity.class);
    }

    //进程保活
    public void doSaveApp(View view) {
        ActivityUtils.startActivity(SaveAppActivity.class);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.i(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        Log.i(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }
}
