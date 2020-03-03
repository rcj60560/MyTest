package com.luocj.mytest.activity.recyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luocj.mytest.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoadingItemActivity extends AppCompatActivity {

    private static final String TAG = LoadingItemActivity.class.getSimpleName();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1001:
                    Log.i(TAG, "handleMessage: ");
                    adapter.setNewData(getNewData());
                    adapter.startLoading(false);
                    break;

                default:

                    break;
            }
        }

        private List<String> getNewData() {
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                strings.add("item :" + i);
            }
            return strings;
        }
    };
    private LoadingItenAdapter adapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_item);
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LoadingItenAdapter(R.layout.item_loading);
        rv.setAdapter(adapter);

        findViewById(R.id.btn_change_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessageDelayed(1001, 2000);
                adapter.setNewData(getListData());
                adapter.startLoading(true);
            }

            private List<String> getListData() {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    strings.add("Item  :" + i);
                }
                return strings;
            }
        });
    }

    private class LoadingItenAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private ArrayList<Boolean> showLoading;

        public LoadingItenAdapter(int layoutId) {
            super(layoutId);
            showLoading = new ArrayList<>();
        }

        @Override
        protected void convert(BaseViewHolder holder, String item) {
            int position = holder.getLayoutPosition();
            if (showLoading.size() > 0) {
                holder.setVisible(R.id.ll_content1, !showLoading.get(position));
                holder.setVisible(R.id.rl_content2, showLoading.get(position));
            }

        }

        public ArrayList<Boolean> getShowLoading() {
            return showLoading;
        }

        public void setShowLoading(ArrayList<Boolean> showLoading) {
            this.showLoading = showLoading;
        }

        public void startLoading(boolean b) {
            int size = this.getData().size();
            if (size <= 0) return;
            ArrayList<Boolean> showLoading = this.getShowLoading();
            showLoading.clear();
            for (int i = 0; i < size; i++) {
                showLoading.add(i, b);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
