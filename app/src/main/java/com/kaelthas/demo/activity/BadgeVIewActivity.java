package com.kaelthas.demo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;

public class BadgeViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_view);

        TextView tv= (TextView) findViewById(R.id.tv);

        BadgeView badgeView=new BadgeView(mContext);
        badgeView.setTargetView(tv);
        badgeView.setText("等待");

    }
}
