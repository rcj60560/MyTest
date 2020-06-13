package com.luocj.mytest.activity.douya;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.luocj.mytest.R;

/**
 * 豆芽项目 相关效果
 *
 */
public class DouyaActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douya);
    }

    public void page1(View view) {
        startActivity(new Intent(DouyaActivity.this,Page1Activity.class));
    }
}
