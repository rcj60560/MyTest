package com.luocj.mytest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.luocj.mytest.R;
import com.luocj.mytest.activity.recyclerview.RecyclerviewActivity;

public class FragmentB extends BaseFragment {

    private static final String TAG = FragmentB.class.getSimpleName();

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.tv_b)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.startActivity(RecyclerviewActivity.class);
                    }
                });
    }

    @Override
    protected int getLayouId() {
        return R.layout.fragment_b;
    }

    @Override
    protected void loadData() {
        Log.e(TAG, "loadData: "  );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume:----> " + getUserVisibleHint() );
    }

}
