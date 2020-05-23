package com.luocj.mytest.activity.customview;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.luocj.mytest.R;
import com.luocj.mytest.activity.addview.AddviewActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    public void do2(View view) {
        ActivityUtils.startActivity(FFActivity2.class);
    }

    public void circleview(View view) {
        ActivityUtils.startActivity(CircleviewActivity.class);
    }
}
