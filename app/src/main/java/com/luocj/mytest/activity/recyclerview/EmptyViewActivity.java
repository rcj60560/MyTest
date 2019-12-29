package com.luocj.mytest.activity.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luocj.mytest.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class EmptyViewActivity extends AppCompatActivity {
    private static final String TAG = EmptyViewActivity.class.getSimpleName();
    private SmartRefreshLayout sfl;
    private RecyclerView rv;
    private EmptyAdapter adapter;
    private int page = 1;
    private View emptyview;
    private ProgressBar pg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emptyview);
        initView();
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        sfl = findViewById(R.id.sfl);
        pg = findViewById(R.id.pg);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmptyAdapter(R.layout.item_text);
        rv.setAdapter(adapter);
//        emptyview = LayoutInflater.from(this).inflate(R.layout.item_loading1, (ViewGroup) rv.getParent(), false);
//        adapter.setEmptyView(emptyview);

        sfl.autoRefresh();
        sfl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        refresh(false);
                    }
                });
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        refresh(true);
                    }
                });
            }
        });
    }

    private void refresh(boolean b) {
        if (page == 5) {
            adapter.setNewData(null);
            sfl.finishLoadMore();
            rv.setVisibility(View.GONE);
            pg.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.VISIBLE);
            pg.setVisibility(View.GONE);
        }

        if (b) {
            page = 1;
            adapter.setNewData(getData(page));
            sfl.finishRefresh();
        } else {
            ++page;
            adapter.replaceData(getData(page));
            sfl.finishLoadMore();
        }
    }

    private List<String> getData(int page) {
        ArrayList<String> strings = new ArrayList<>();
        int num = page * 10;
        for (int i = 0; i < num; i++) {
            strings.add("item :" + i);
        }
        Log.i(TAG, "getData: " + strings.size());
        return strings;
    }

    private class EmptyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public EmptyAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder holder, String item) {
            holder.setText(R.id.tv_content, item);
        }
    }
}
