package com.kaelthas.demo.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaelthas.demo.R;
import com.kaelthas.demo.bean.Demo;

import java.util.List;

/**
 * Created by KaelThas.Wang on 16/9/6.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public class DemoAdapter extends BaseAdapter<Demo, DemoAdapter.DemoHolder> {
    public DemoAdapter(Context context, List<Demo> list) {
        super(context, list);
    }

    @Override
    public DemoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_demo, parent,false);
        return new DemoHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoHolder holder, final int position) {
        holder.tvDescription.setText(mDataList.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext,mDataList.get(position).getTargetActivity()));
            }
        });
    }


    public class DemoHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;

        public DemoHolder(View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
