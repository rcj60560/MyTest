package com.luocj.mytest.activity.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luocj.mytest.R;

import java.util.ArrayList;
import java.util.List;

public class SuspensionActivity extends AppCompatActivity {

    private LinearLayout llsuspensior;
    private ImageView iv1;
    private TextView content;
    private int height;
    private int currentPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspension);
        initView();
    }

    private void initView() {

        llsuspensior = findViewById(R.id.ll_suspensior);
        iv1 = findViewById(R.id.iv1);
        content = findViewById(R.id.content);

        RecyclerView rv = findViewById(R.id.rv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        SuspensionAdatper suspensionAdatper = new SuspensionAdatper();
        rv.setAdapter(suspensionAdatper);
        suspensionAdatper.setNewData(getData());

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                height = llsuspensior.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = linearLayoutManager.findViewByPosition(currentPos + 1);

                if (view != null) {
                    if (view.getTop() <= height) {
                        llsuspensior.setY(-(height - view.getTop()));
                    } else {
                        llsuspensior.setY(0);
                    }
                }

                if (currentPos != linearLayoutManager.findFirstVisibleItemPosition()) {
                    currentPos = linearLayoutManager.findFirstVisibleItemPosition();
                    updateHeight();
                }
            }
        });
    }

    private void updateHeight() {

    }

    private List<String> getData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("item :" + i);
        }
        return strings;
    }

    private class SuspensionAdatper extends BaseQuickAdapter<String, BaseViewHolder> {
        SuspensionAdatper() {
            super(R.layout.item_suspenior);
        }

        @Override
        protected void convert(BaseViewHolder holder, String item) {
            holder.setText(R.id.tv, item);
        }
    }
}
