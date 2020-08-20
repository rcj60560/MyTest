package com.luocj.mytest.activity.recyclerview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.luocj.mytest.R;

public class XiFuActivity extends AppCompatActivity {

    private static final String TAG = XiFuActivity.class.getSimpleName();
    private ScrollView scrollview;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xifu);
        scrollview = findViewById(R.id.scrollview);
        rl1 = findViewById(R.id.rl1);
        rl2 = findViewById(R.id.rl2);
        rl3 = findViewById(R.id.rl3);


        scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int scrollY1 = scrollview.getScrollY();
                int bottom = scrollview.getBottom();
                int top = scrollview.getTop();

//                RelativeLayout rl1 = ((ViewGroup) v).findViewById(R.id.rl1);
//                RelativeLayout rl2 = ((ViewGroup) v).findViewById(R.id.rl2);
//                RelativeLayout rl3 = ((ViewGroup) v).findViewById(R.id.rl3);
                LinearLayout linearLayout = (LinearLayout) ((ViewGroup) v).getChildAt(0);
                RelativeLayout child1 = (RelativeLayout) linearLayout.getChildAt(0);
                LinearLayout topbar = child1.findViewById(R.id.topbar);

                float rate = px2dip(XiFuActivity.this, scrollY1) * 100.f / px2dip(XiFuActivity.this, child1.getHeight());
                topbar.setAlpha(1 - rate);
                Log.i(TAG, "onScrollChange: " + px2dip(XiFuActivity.this, scrollY1)
                        + ", bottom :" + bottom
                        + ", top :" + top + ",top-----> :" + px2dip(XiFuActivity.this, child1.getHeight()) + ",rate :" + rate);

            }
        });

    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
