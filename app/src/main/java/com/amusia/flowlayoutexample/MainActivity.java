package com.amusia.flowlayoutexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amusia.amusiaflow.FlowView;
import com.amusia.amusiaflow.SimpleFlowAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlowView flowView;
    FlowAdapter<DataBean> flowAdapter;
    SimpleFlowAdapter<DataBean> simpleFlowAdapter;
    List<String> testData = new ArrayList<>();
    TextView tv_reset;


    private List<DataBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                if (flowAdapter != null)
                    flowAdapter.notifyDataSetChanged();
                if (simpleFlowAdapter != null)
                    simpleFlowAdapter.notifyDataSetChanged();
            }
        });

//        flowAdapter = new FlowAdapter<>(this, datas, new FlowAdapter.OnItemListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                datas.remove(position);
//                flowAdapter.notifyDataSetChanged();
//            }
//        });
//
//        flowView = findViewById(R.id.flow_view);
//        flowView.setAdapter(flowAdapter);

        flowView = findViewById(R.id.flow_view);
        simpleFlowAdapter = new SimpleFlowAdapter<DataBean>(this, R.layout.item, datas) {
            @Override
            public void convert(View itemView, Object object, int position) {
                DataBean dataBean = (DataBean) object;
                TextView nameText = itemView.findViewById(R.id.name);
                ImageView logoImage = itemView.findViewById(R.id.logo);
                nameText.setText(dataBean.getName());
                logoImage.setBackgroundResource(dataBean.getLogo());

            }
        };
        simpleFlowAdapter.setOnItemListener(new SimpleFlowAdapter.OnItemListener() {
            @Override
            public void onItemClick(View v, int position) {
                datas.remove(position);
                simpleFlowAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "长按事件：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        flowView.setAdapter(simpleFlowAdapter);


    }

    private void initData() {
        testData.clear();
        datas.clear();
        testData.add("非著名cv工程师bug搬运工");
        testData.add("单击删除item");
        testData.add("水果味孕妇奶粉");
        testData.add("儿童洗衣机");
        testData.add("洗衣机全自动");
        testData.add("小度");
        testData.add("儿童汽车可坐人");
        testData.add("抽真空收纳袋");
        testData.add("儿童滑板车");
        testData.add("稳压器 电容");
        testData.add("羊奶粉");
        testData.add("奶粉1段");
        testData.add("图书勋章日");
        for (int i = 0; i < testData.size(); i++) {
            datas.add(new DataBean(R.drawable.ic_launcher_foreground, testData.get(i)));
        }
    }

}
