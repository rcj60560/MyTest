package com.luocj.mytest.activity.bitmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.luocj.mytest.R;

public class YouHuaActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhua);
    }

    @Override
    public void onClick(View v) {

    }

    public void btn1(View view) {
        ActivityUtils.startActivity(BitmapYouHuaActivity.class);
    }

    public void btn2(View view) {
        ActivityUtils.startActivity(ListBitmapActivity.class);
    }

    public void btn3(View view) {
        ActivityUtils.startActivity(BitmapCompressActivity.class);
    }

    public void btn4(View view) {
        ActivityUtils.startActivity(ThirdCompressActivity.class);
    }

    public void btn5(View view) {
        ActivityUtils.startActivity(BitmapYouHuaActivity5.class);
    }
}
