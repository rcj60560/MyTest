package com.luocj.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.luocj.mytest.activity.net.NetActivity;
import com.luocj.mytest.activity.recyclerview.RecyclerviewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doRecyclerview(View view) {
        ActivityUtils.startActivity(RecyclerviewActivity.class);
    }

    public void doNet(View view) {
        ActivityUtils.startActivity(NetActivity.class);
    }
}
