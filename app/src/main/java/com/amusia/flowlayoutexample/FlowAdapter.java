package com.amusia.flowlayoutexample;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.amusia.amusiaflow.BaseFlowAdapter;

import java.util.List;

public class FlowAdapter<T> extends BaseFlowAdapter {

    private List<T> mDataList;
    private Context mContext;
    private OnItemListener mOnItemListener;

    public FlowAdapter(Context context, List<T> dataList, OnItemListener onItemListener) {
        mContext = context;
        mDataList = dataList;
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
        return LayoutInflater.from(mContext).inflate(R.layout.item, null);
    }

    @Override
    public void onBindView(View itemView, Object object, final int position) {
        DataBean dataBean = (DataBean) object;
        TextView nameText = itemView.findViewById(R.id.name);
        ImageView logoImage = itemView.findViewById(R.id.logo);

        nameText.setText(dataBean.getName());
        logoImage.setBackgroundResource(dataBean.getLogo());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemListener != null) {
                    Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();
                    mOnItemListener.onItemClick(v, position);
                }
            }
        });
    }

    public interface OnItemListener {

        void onItemClick(View v, int position);
    }


}
