package com.amusia.amusiaflow;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowView extends ViewGroup {
    //记录每一行中的所有View
    private List<View> lineViews = new ArrayList<>();

    //记录总共有多少行
    private List<List<View>> allViews;
    //记录行高
    private List<Integer> heights;
    //垂直方向间隔
    private int verticalSpace = 20;
    //水平方向间隔
    private int horizontalSpace = 20;
    //容器宽高
    private int selfWidth;
    private int selfHeight;
    //适配器
    BaseFlowAdapter mAdapter;


    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置适配器
    public void setAdapter(BaseFlowAdapter adapter) {
        //移除所有view
        removeAllViews();
        //设置适配器
        mAdapter = adapter;
        //注册数据监听
        mAdapter.registerObserver(mObserver);
        //循环添加view
        View child;
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            //创建一个view
            child = mAdapter.onCreateView(this, i);
            //给view适配数据
            mAdapter.onBindView(child, mAdapter.getItem(i), i);
            //添加到viewGroup
            addView(child);
        }
    }

    //初始化全局参数
    private void initParams() {
        allViews = new ArrayList<>();
        heights = new ArrayList<>();

    }

    //实例化数据监听
    private DataSetObserver mObserver = new DataSetObserver() {
        //数据发生改变时调用onChange
        public void onChanged() {
            onChange();
        }

        //数据失效时清空所有View
        public void onInvalidated() {
            removeAllViews();
        }
    };

    //数据改变时调用
    private void onChange() {
        int childCount = getChildCount(); //获取到当前viewGroup中有多少view
        int itemCount = mAdapter.getItemCount();//获取到当前适配器中有多少数据
        // 如果数据变少了，则移出多余的view
        if (childCount > itemCount) {
            removeViews(itemCount, childCount - itemCount);
        }

        // 现在view的个数 <= 数据的个数,说明数据变多了
        View child;
        //循环添加view
        for (int i = 0; i < itemCount; i++) {
            if (i < childCount) { //i<childCount 说明该view已经存在，不需要重新创建
                child = getChildAt(i); //获取到已存在的view
                mAdapter.onBindView(child, mAdapter.getItem(i), i); // 重新进行数据适配
            } else { // 是新增的view
                child = mAdapter.onCreateView(this, i);//需要重新Create一个View
                mAdapter.onBindView(child, mAdapter.getItem(i), i);//对这个view进行数据适配
                addView(child);//把创建的view添加到viewGroup中
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //在onMeasure中初始化全局参数，有的时候onMeasure回执行多次，会出现一些奇奇怪怪的问题
        initParams();
        //当前行高
        int lineHeight = 0;
        //当前行已用完的宽度
        int lineUsedWidth = 0;

        //FlowView的父控件给FlowView的宽高
        selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        //FlowView计算出来的所需要的宽高
        int parentNeedWidth = 0;
        int parentNeedHeight = 0;

        //测量子view
        int childCount = getChildCount();
        int leftPadding = getPaddingLeft();
        int rightPadding = getPaddingRight();
        int topPadding = getPaddingTop();
        int bottomPadding = getPaddingBottom();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            int childWidth = child.getLayoutParams().width;
            int childHeight = child.getLayoutParams().height;
            int childMeasureWidth = getChildMeasureSpec(widthMeasureSpec, leftPadding + rightPadding, childWidth);
            int childMeasureHeight = getChildMeasureSpec(heightMeasureSpec, topPadding + bottomPadding, childHeight);
            child.measure(childMeasureWidth, childMeasureHeight);
            /**
             *  如果已经使用的宽度 + 将要添加进这行的view的宽度 + 间隔 大于 flowView自身的宽度，就换行
             *  第一次循环的时候是不会进入这个if的，因为lineUsedWidth的宽度为0
             *  只有在当前行有一定个数的view之后才有可能进入这个if
             */
            if (lineUsedWidth + child.getMeasuredWidth() + horizontalSpace > selfWidth) {
                //保存当前这一行
                allViews.add(lineViews);
                //保存当前的行高
                heights.add(lineHeight);
                //重新初始化一个行容器
                lineViews = new ArrayList<>();
                //计算出最宽的一行，作为FlowView实际的宽度
                parentNeedWidth = Math.max(parentNeedWidth, lineUsedWidth);
                //累加所有行高和间隔，作为FlowView实际的高度度
                parentNeedHeight += lineHeight + verticalSpace;
                //清空已使用宽度和高度
                lineUsedWidth = 0;
                lineHeight = 0;
            }
            //将当前的view添加到当前行
            lineViews.add(child);
            //计算已使用的宽度
            lineUsedWidth = lineUsedWidth + child.getMeasuredWidth() + horizontalSpace;
            //将该行中最高的一个view的高度作为行高
            lineHeight = Math.max(lineHeight, child.getMeasuredHeight());

            //添加最后一行的数据
            if (i == childCount - 1) {
                allViews.add(lineViews);
                heights.add(lineHeight);
                lineViews = new ArrayList<>();
                parentNeedWidth = Math.max(parentNeedWidth, lineUsedWidth);
                parentNeedHeight += lineHeight + verticalSpace;
                lineUsedWidth = 0;
                lineHeight = 0;
            }
        }


        //测量自身
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = widthMode == MeasureSpec.EXACTLY ? selfWidth : parentNeedWidth;
        int realHeight = heightMode == MeasureSpec.EXACTLY ? selfHeight : parentNeedHeight;

        int selfWidthMeasureSpec = MeasureSpec.makeMeasureSpec(realWidth, widthMode);
        int selfHeightMeasureSpec = MeasureSpec.makeMeasureSpec(realHeight, heightMode);

        //可以封装成MeasureSpec调用super，也可以直接用setMeasuredDimension
        super.onMeasure(selfWidthMeasureSpec, selfHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //获取所有行
        int lineCount = allViews.size();
        Log.e("lineCount", "" + lineCount);
        //初始的left为0
        int currentL = 0;
        //初始的top为0
        int currentT = 0;
        //view移出for循环，防止大量创建变量，造成内存抖动
        View view;
        //遍历所有行
        for (int i = 0; i < lineCount; i++) {
            //取到当前行
            List<View> views = allViews.get(i);
            //取到当前行高
            int lineHeight = heights.get(i);
            //遍历当前行的所有列
            for (int j = 0; j < views.size(); j++) {
                view = views.get(j);
                //计算left top right bottom四个点
                int left = currentL;
                int right = left + view.getMeasuredWidth();
                int top = currentT;
                int bottom = top + view.getMeasuredHeight();
                //调用子view的layout进行布局
                view.layout(left, top, right, bottom);
                //重新计算left的位置，因为要加上已经布局好的view的宽度
                currentL = currentL + view.getWidth() + horizontalSpace;
            }
            //遍历完一行之后，左边顶点距离清空
            currentL = 0;
            //上顶点距离要加上当前行高+垂直间距
            currentT = currentT + lineHeight + verticalSpace;

        }
    }

    public int getVerticalSpace() {
        return verticalSpace;
    }

    public FlowView setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
        return this;
    }

    public int getHorizontalSpace() {
        return horizontalSpace;
    }

    public FlowView setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
        return this;
    }
}
