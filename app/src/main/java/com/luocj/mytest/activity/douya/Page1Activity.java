package com.luocj.mytest.activity.douya;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luocj.mytest.R;
import com.luocj.mytest.widget.PullLoadMoreView;

/**
 * 首页下拉效果
 */
public class Page1Activity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter1());

        PullLoadMoreView pullloadmoerview = findViewById(R.id.pullloadmoreview);
        LinearLayout toplayoutview = (LinearLayout) pullloadmoerview.addHeadView(R.layout.top_layout);
        RecyclerView rvHor = toplayoutview.findViewById(R.id.rv_hor);
        rvHor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvHor.setAdapter(new MyAdapter());

        pullloadmoerview.setViewStateListener(new PullLoadMoreView.ViewStateListener() {
            @Override
            public void onViewState(PullLoadMoreView.VIewState viewState) {
                Log.i("TAG", "c " + viewState);
            }
        });

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.Hodler> {
        @NonNull
        @Override
        public Hodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imageview, parent, false);
            Hodler hodler = new Hodler(inflate);
            return hodler;
        }

        @Override
        public void onBindViewHolder(@NonNull Hodler holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class Hodler extends RecyclerView.ViewHolder {
            private final ImageView imageview;

            public Hodler(@NonNull View itemView) {
                super(itemView);
                imageview = itemView.findViewById(R.id.iv);
            }
        }
    }

    private class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.Holder> {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
            Holder holder = new Holder(inflate);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.tvContent.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        public class Holder extends RecyclerView.ViewHolder {
            private final TextView tvContent;

            public Holder(@NonNull View itemView) {
                super(itemView);
                tvContent = itemView.findViewById(R.id.tv_content);
            }
        }
    }

}
