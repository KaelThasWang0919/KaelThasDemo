package com.kaelthas.demo.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.kaelthas.demo.R;
import com.kaelthas.demo.adapter.MyViewPagerAdapter;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityViewpagerBinding;
import com.kaelthas.demo.ui.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KaelThas.Wang on 2017/7/20.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class ViewpagerActivity extends BaseActivity {

    private ActivityViewpagerBinding mBinding;
    private MyViewPagerAdapter mAdapter;
    private List<Fragment> mList = new ArrayList<>();
    private MyFragment mCurrentFragment;
    private MyFragment mOldFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_viewpager);

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mList);


        for (int i = 0; i < 5; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("data", "我是第" + i + "个Fragment");
            MyFragment fragment = new MyFragment();
            fragment.setArguments(bundle);
            mList.add(fragment);

        }

        ViewPager viewPager = mBinding.viewPager;
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(5);
        mOldFragment = (MyFragment) mList.get(4);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("onPageScrolled", "position==" + position + "positionOffset===" + positionOffset + "positionOffsetPixels====" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mOldFragment.setState("我是原来的状态");

                mCurrentFragment = (MyFragment) mList.get(position);
                mCurrentFragment.setState("我是新的状态");
                mOldFragment = mCurrentFragment;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
