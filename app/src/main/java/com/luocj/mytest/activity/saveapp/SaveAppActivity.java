package com.luocj.mytest.activity.saveapp;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.luocj.mytest.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 进程保活
 * 2020年3月10日17:08:15
 */
public class SaveAppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_app_test);
    }
}
