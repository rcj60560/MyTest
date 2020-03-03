package com.luocj.mytest.activity.bitmap;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luocj.mytest.R;
import com.luocj.mytest.model.MeiZhiModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListBitmapActivity extends AppCompatActivity {

    private static final String TAG = ListBitmapActivity.class.getSimpleName();
    private RecyclerView rv;
    private ListBitmapAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_bitmap);
        initView();
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListBitmapAdapter();
        rv.setAdapter(adapter);
    }

    private class ListBitmapAdapter extends BaseQuickAdapter<MeiZhiModel.ResultsBean, BaseViewHolder> {
        public ListBitmapAdapter() {
            super(R.layout.item_bitmap);
        }

        @Override
        protected void convert(BaseViewHolder holder, MeiZhiModel.ResultsBean item) {
            ImageView iv = holder.getView(R.id.iv);

            Glide.with(mContext)
                    .load(item.getUrl())
                    .asBitmap()
                    .into(iv);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkGo.<String>get("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/1000/1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        MeiZhiModel model = JSONObject.parseObject(response.body(), MeiZhiModel.class);
                        List<MeiZhiModel.ResultsBean> results = model.getResults();
                        adapter.setNewData(results);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: ");
                    }
                });
    }
}
