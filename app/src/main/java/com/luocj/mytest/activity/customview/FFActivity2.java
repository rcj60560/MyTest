package com.luocj.mytest.activity.customview;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.luocj.mytest.R;
import com.luocj.mytest.widget.MyButton;
import com.luocj.mytest.widget.MyRelativeLayout;

/**
 * 事件分发
 */

public class FFActivity2 extends AppCompatActivity {

    private static final String TAG = FFActivity2.class.getSimpleName();
    private MyRelativeLayout rl;
    private MyButton botton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjff);

        rl = findViewById(R.id.relativeLayout);
        botton = findViewById(R.id.button3);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("TAG", "dispatchTouchEvent: " + TAG);
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("TAG", "onTouchEvent: " +TAG);
        return super.onTouchEvent(event);
    }
}
