package com.luocj.mytest.activity.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luocj.mytest.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class RecyclerviewActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_test);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                startActivity(new Intent(this, LoadingItemActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, EmptyViewActivity.class));
                break;

            case R.id.btn3:
                startActivity(new Intent(this, SuspensionActivity.class));
                break;


        }
    }
}
