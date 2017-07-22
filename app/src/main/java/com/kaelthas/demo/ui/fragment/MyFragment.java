package com.kaelthas.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaelthas.demo.R;

/**
 * Created by KaelThas.Wang on 2017/7/20.
 * Email: KaelThas.Wang0919@gmail.com
 */

public class MyFragment extends Fragment {

    TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        mTextView= (TextView) rootView.findViewById(R.id.tv_name);
        return rootView;
    }

    public void setState(String state){

            mTextView.setText(state);
    }
}
