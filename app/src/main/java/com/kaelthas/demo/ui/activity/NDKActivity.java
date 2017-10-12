package com.kaelthas.demo.ui.activity;

import android.os.Bundle;

import com.kaelthas.demo.R;
import com.kaelthas.demo.base.BaseActivity;
import com.kaelthas.demo.databinding.ActivityNdkBinding;
import com.kaelthas.demo.ndk.test.AddNum;

/**
 * Created by KaelThas.Wang on 2017/10/11.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class NDKActivity extends BaseActivity<ActivityNdkBinding> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_ndk);


        mDataBinding.tvResult.setText(add(2, 4)+"");
    }

    public int add(int x, int y) {
        return new AddNum().add(x, y);
    }


}
