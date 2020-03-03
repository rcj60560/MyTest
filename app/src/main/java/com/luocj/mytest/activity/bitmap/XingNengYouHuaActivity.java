package com.luocj.mytest.activity.bitmap;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

import com.luocj.mytest.R;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 性能优化
 * 2020年2月11日17:55:30
 */

/**
 * adb shell ps   查看所有进程及内存占用等
 * <p>
 * adb shell dumpsys meminfo 包名 或者进程id  查看某个进程的内存占用情况
 */
public class XingNengYouHuaActivity extends AppCompatActivity {

    private static final String TAG = XingNengYouHuaActivity.class.getSimpleName();
    private TextView tv;

    private int row = 30;
    private int length = 300;
    private int[][] strMatrix = new int[row][length];

    private int row1 = 10;
    private int length1 = 4200000;
    private Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xingneng);
        tv = findViewById(R.id.tv);

        //1 监控内存
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = activityManager.getMemoryClass();
        int largeMemoryClass = activityManager.getLargeMemoryClass();
        tv.append("内存大小：" + memoryClass + "\n");

        tv.append("最大内存：" + largeMemoryClass + "\n");


    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.d(TAG, "onTrimMemory: " + level);
    }

    public void btn1(View view) {

        String result = "";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < length; j++) {
                result = result + strMatrix[i][j];
                result = result + ",";
            }

            Log.d(TAG, "btn1: " + i);
        }

        Log.d(TAG, "btn1: " + "----------------->");
    }

    public void btn2(View view) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < length; j++) {
                sb.append(strMatrix[i][j]);
                sb.append(",");
            }
            Log.d(TAG, "btn2: " + i);
        }
        Log.i(TAG, "btn2: " + "==========");
    }

    public void btn3(View view) {

        for (int i = 0; i < row1; i++) {
            String[] str = new String[length1];
            for (int j = 0; j < length1; j++) {
                str[j] = String.valueOf(random.nextDouble());
            }

            Log.i(TAG, "btn3: " + i);
        }
    }

    public void btn4(View view) {

    }

    public void btn5(View view) {
        startActivity(new Intent(XingNengYouHuaActivity.this, TestActivity.class));
    }
}
