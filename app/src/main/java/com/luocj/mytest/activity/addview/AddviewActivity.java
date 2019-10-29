package com.luocj.mytest.activity.addview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luocj.mytest.R;

public class AddviewActivity extends AppCompatActivity {

    private LinearLayout ll1;
    private LinearLayout ll2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addview);
        initView();
    }

    private void initView() {
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView(v.getContext());
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void addView(Context context) {
        TextView textView = new TextView(context);
        textView.setWidth(200);
        textView.setHeight(200);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20,20,20,20);
        textView.setLayoutParams(lp);
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setText("ahahaha");

        ll1.addView(textView);
    }
}
