package com.luocj.mytest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.luocj.mytest.R;
import com.luocj.mytest.activity.recyclerview.RecyclerviewActivity;

public class FragmentD extends Fragment {

    private static final String TAG = FragmentD.class.getSimpleName();
    private View inflate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_d, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        inflate.findViewById(R.id.btn4)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.startActivity(RecyclerviewActivity.class);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " + getUserVisibleHint());
    }
}
