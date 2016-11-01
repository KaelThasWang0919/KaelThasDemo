package com.kaelthas.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaelthas.demo.R;

import java.util.List;

/**
 * Created by KaelThas.Wang on 2016/10/21.
 * E_mail KaelThas.Wang0919@gmail.com
 */
public abstract class BaseAdapter<T,Holder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<Holder> {
    protected Context mContext;
    protected List<T> mDataList;
    protected OnClickListener mListener;


    public BaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mDataList = list;
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }


    public interface OnClickListener {
        void onItemClick(int pos);
    }
}


