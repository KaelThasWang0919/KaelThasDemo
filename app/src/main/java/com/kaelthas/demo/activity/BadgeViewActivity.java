package com.kaelthas.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;

public class BadgeViewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_view);
        setTitle("添角标示例");

        TextView tv = (TextView) findViewById(R.id.tv);

        BadgeView badgeView = new BadgeView(mContext);
        badgeView.setTargetView(tv);

        badgeView.setBadgeMargin(-3);
        badgeView.setBackgroundColor(Color.parseColor("#ff0000"));//设置背景颜色
        badgeView.setTextColor(Color.parseColor("#000000"));//设置文字颜色
        badgeView.setShadowLayer(0.5f, 1, 1, R.color.bule);//设置字体的阴影
        badgeView.setBadgeGravity(Gravity.BOTTOM | Gravity.RIGHT);//设置徽章的位置
        badgeView.setBadgeCount(2);//基础的数字徽章
        badgeView.setText("等待");//文字徽章
        badgeView.setBackgroundResource(R.drawable.bg_radius_blue);//设置背景  这里用圆角图片
        //badgeView.setTypeface();  设置字体
        badgeView.setBackground(12,R.color.bule);//设置内容及背景颜色
//        badgeView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));

    }
}
