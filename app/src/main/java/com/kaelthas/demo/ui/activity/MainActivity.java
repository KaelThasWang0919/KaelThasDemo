package com.kaelthas.demo.ui.activity;

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


        LinearLayoutManager manager = new LinearLayoutManager(mContext);
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
        mDemoList.add(new Demo("二维码扫描`生成`识别\n(ZXing)", QRCodeActivity.class));
        mDemoList.add(new Demo("友盟三方分享`登录\n(qq,微信,新浪微博)", UmengSocialActivity.class));
        mDemoList.add(new Demo("原生相机\n(Camera1)", CameraActivity.class));
        mDemoList.add(new Demo("第三方WebView\n(Crosswalk)", CrosswalkActivity.class));
        mDemoList.add(new Demo("两种启动Service发方法\n(Service)", ServiceActivity.class));
        mDemoList.add(new Demo("ViewPager\n(ViewPager)", ViewpagerActivity.class));
        mDemoList.add(new Demo("抽屉\n(DrawerLayout)", DrawerLayoutActivity.class));
        mDemoList.add(new Demo("web高德地图\n(amap)", GaoDeMapActivity.class));
        mDemoList.add(new Demo("可展开列表组件\n(ExpandableListView)", ExpandableListViewActivity.class));
        mDemoList.add(new Demo("低功耗蓝牙\n(BLE)", BLEActivity.class));
    }
}
