package com.kaelthas.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kaelthas.demo.R;
import com.kaelthas.demo.adapter.DemoAdapter;
import com.kaelthas.demo.bean.Demo;
import com.kaelthas.demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mCatalog;
    private DemoAdapter mAdapter;
    private List<Demo> mDemoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCatalog = (RecyclerView) findViewById(R.id.catalog);


        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mCatalog.setLayoutManager(manager);

        addDemo();
        mAdapter = new DemoAdapter(mContext, mDemoList);
        mCatalog.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
    }

    private void addDemo() {
        mDemoList.clear();
        mDemoList.add(new Demo("FaceBook图片处理工具\n(SimpleDraweeView)", SimpleDraweeViewActivity.class));
        mDemoList.add(new Demo("View添加角标\n(BadgeView)", BadgeViewActivity.class));
        mDemoList.add(new Demo("数据双向绑定\n(DataBinding)", DataBindingActivity.class));
    }
}
