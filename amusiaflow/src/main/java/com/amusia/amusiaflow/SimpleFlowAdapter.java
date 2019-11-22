package com.amusia.amusiaflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class SimpleFlowAdapter<T> extends BaseFlowAdapter {


    private List<T> mDataList;
    private Context mContext;
    private int mLayoutId;
    private OnItemListener mOnItemListener;


    public SimpleFlowAdapter(Context context, int layoutId, List<T> dataList) {
        mContext = context;
        mLayoutId = layoutId;
        mDataList = dataList;
    }

    public SimpleFlowAdapter(Context context, int layoutId, List<T> dataList, OnItemListener onItemListener) {
        mContext = context;
        mDataList = dataList;
        mLayoutId = layoutId;
        mOnItemListener = onItemListener;
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public View onCreateView(ViewGroup parent, final int position) {
        return LayoutInflater.from(mContext).inflate(mLayoutId, null);
    }

    @Override
    public void onBindView(View itemView, Object object, final int position) {
        setListener(itemView, object, position);
        convert(itemView, object, position);
    }

    public interface OnItemListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }

    public abstract void convert(View itemView, Object object, final int position);

    private void setListener(View itemView, Object object, final int position) {
        if (mOnItemListener != null) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemListener.onItemClick(v, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemListener.onItemLongClick(v, position);
                    return true;
                }
            });


        }
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

}
