package com.kaelthas.demo.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.kaelthas.demo.R;
import com.kaelthas.demo.bean.User;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityDataBindingBinding;

/**
 * Created by KaelThas.Wang on 2017/3/20.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class DataBindingActivity extends BaseActivity {

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("数据双向绑定");
//        setContentView(R.layout.activity_databinding);

        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_data_binding);
        mUser = new User("张三", "100001");
        binding.setUser(mUser);

       // binding.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mUser.setUserName("李四");
//            }
//        });

        binding.setClick(new TestClick());


    }

    public class TestClick {
        public void btnClick(String text) {
            showToast("点击成功");
            mUser.setUserName("李四"+text);
        }
        public void btnClick(View view) {
            // showToast("点击成功");
            mUser.setUserName("李四");
        }
    }
}
