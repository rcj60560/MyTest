package com.luocj.mytest.activity.other;

import android.app.LauncherActivity;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.luocj.mytest.R;
import com.luocj.mytest.fragment.FragmentA;
import com.luocj.mytest.fragment.FragmentB;
import com.luocj.mytest.fragment.FragmentC;
import com.luocj.mytest.fragment.FragmentD;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class LazyLoadActivity extends AppCompatActivity {

    private static final String TAG = LauncherActivity.class.getSimpleName();
    private ArrayList<Fragment> fragments;
    private ArrayList<Fragment> fragments1;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_load);
        initData();
        initView();
    }

    private void initData() {
        strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        fragments = new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());

        fragments1 = new ArrayList<>();
        fragments1.add(new FragmentC());
        fragments1.add(new FragmentD());
    }

    private void initView() {
        TabLayout tablayout1 = findViewById(R.id.tablayout1);

        ViewPager vp = findViewById(R.id.vp1);
        MyAdapter adpater = new MyAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adpater);
        tablayout1.setupWithViewPager(vp);


//        TabLayout tablayout = findViewById(R.id.tbl);
//        ViewPager vp2 = findViewById(R.id.vp2);
//        MyAdapter1 myAdapter1 = new MyAdapter1(getSupportFragmentManager(), fragments1);
//        vp2.setAdapter(myAdapter1);
//        tablayout.setupWithViewPager(vp2);
//        vp2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                Log.e(TAG, "onPageSelected:当前选中----------->" + i);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });

    }

    private class MyAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> mdata;

        public MyAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
            super(fragmentManager);
            this.mdata = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mdata.get(i);
        }

        @Override
        public int getCount() {
            return mdata.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }
    }

    private class MyAdapter1 extends FragmentStatePagerAdapter {
        public MyAdapter1(FragmentManager supportFragmentManager, ArrayList<Fragment> fragments1) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments1.get(i);
        }

        @Override
        public int getCount() {
            return fragments1.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }
    }
}
