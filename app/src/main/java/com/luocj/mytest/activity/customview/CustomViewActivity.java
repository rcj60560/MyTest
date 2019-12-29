package com.luocj.mytest.activity.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.luocj.mytest.R;
import com.luocj.mytest.activity.addview.AddviewActivity;

public class CustomViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
    }

    /**
     * 事件分发
     *
     * @param view
     */
    public void do1(View view) {
        ActivityUtils.startActivity(FFActivity.class);
    }


    /**
     * 添加view
     *
     * @param view
     */
    public void doAddView(View view) {
        ActivityUtils.startActivity(AddviewActivity.class);
    }
}
