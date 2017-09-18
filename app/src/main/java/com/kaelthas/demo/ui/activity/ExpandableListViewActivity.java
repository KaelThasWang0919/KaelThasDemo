package com.kaelthas.demo.ui.activity;

import android.os.Bundle;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityExpandableListViewBinding;

/**
 * Created by KaelThas.Wang on 2017/8/20.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class ExpandableListViewActivity extends BaseActivity<ActivityExpandableListViewBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_expandable_list_view);
    }
}
