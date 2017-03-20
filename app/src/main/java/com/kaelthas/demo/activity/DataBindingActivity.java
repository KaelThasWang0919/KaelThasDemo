package com.kaelthas.demo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kaelthas.demo.R;
import com.kaelthas.demo.bean.User;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityDataBindingBinding;

/**
 * Created by KaelThas.Wang on 2017/3/20.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class DataBindingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("数据双向绑定");
//        setContentView(R.layout.activity_databinding);

        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_data_binding);
        User user = new User("张三", "100001");
        binding.setUser(user);


    }
}
