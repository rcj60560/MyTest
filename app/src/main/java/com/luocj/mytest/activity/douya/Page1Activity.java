package com.luocj.mytest.activity.douya;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.luocj.mytest.R;

/**
 * 首页下拉效果
 */
public class Page1Activity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ToastUtils.showShort("豆芽首页下拉效果");
        setContentView(R.layout.activity_page1);
    }
}
