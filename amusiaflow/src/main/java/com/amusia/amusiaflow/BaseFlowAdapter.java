package com.amusia.amusiaflow;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFlowAdapter {

    private DataSetObserver mDataSetObserver;

    public void registerObserver(DataSetObserver observer) {
        mDataSetObserver = observer;
    }

    public void unregisterObserver() {
        mDataSetObserver = null;
    }

    public void notifyDataSetChanged() {
        if (mDataSetObserver != null) mDataSetObserver.onChanged();
    }

    public void notifyDataSetInvalidate() {
        if (mDataSetObserver != null) mDataSetObserver.onInvalidated();
    }


    public abstract int getItemCount();

    public abstract Object getItem(int position);

    public abstract View onCreateView(ViewGroup parent, int position);

    public abstract void onBindView(View itemView, Object object, int position);
}
