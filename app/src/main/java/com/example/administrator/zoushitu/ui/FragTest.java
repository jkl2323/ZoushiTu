package com.example.administrator.zoushitu.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zoushitu.ChartView;
import com.example.administrator.zoushitu.HeadCustomGridView;
import com.example.administrator.zoushitu.HeaderHorizontalScrollView;
import com.example.administrator.zoushitu.LeftNumberCustomListView;
import com.example.administrator.zoushitu.LeftNumberSynchScrollView;
import com.example.administrator.zoushitu.OpenNumber;
import com.example.administrator.zoushitu.R;
import com.example.administrator.zoushitu.ScrollChangeCallback;
import com.example.administrator.zoushitu.TrendScrollViewWidget;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragTest extends android.support.v4.app.Fragment implements ScrollChangeCallback ,View.OnClickListener{
        ArrayList<OpenNumber> list=new ArrayList<>();
        ArrayList<String> qihaoList=new ArrayList<>();
        ArrayList<String> tishi=new ArrayList<>();
    private int mLeftItemH;
    private ChartView chatview;
    private TrendScrollViewWidget scroll_content;
    private LeftNumberSynchScrollView scroll_left;
    private HeaderHorizontalScrollView trend_header_scroll;
    int index=0;
    @SuppressLint("ValidFragment")
    public FragTest(int index){
    this.index=index;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_test, null);
        scroll_content = root.findViewById(R.id.scroll_content);
        scroll_left = root.findViewById(R.id.scroll_left);
        trend_header_scroll = root.findViewById(R.id.trend_header_scroll);
        scroll_content.setScrollViewListener(this);
        scroll_left.setScrollViewListener(this);
        trend_header_scroll.setScrollViewListener(this);
        initdata();
        setlistener();
        initUI(root);
        return root;
    }

    private void initUI(View view) {
        view.findViewById(R.id.btn_qian).setOnClickListener(this);
        view.findViewById(R.id.btn_hide).setOnClickListener(this);
        view.findViewById(R.id.btn_show).setOnClickListener(this);
        view.findViewById(R.id.btn_heji_hide).setOnClickListener(this);
        view.findViewById(R.id.btn_heji_show).setOnClickListener(this);
        TextView tv_page=view.findViewById(R.id.tv_page);
        Log.w(ChartView.Tag,"打印当前页面"+index);
        tv_page.setText("当前页面"+index);
        LeftNumberCustomListView lv_number=view.findViewById(R.id.lv_number);
        HeadCustomGridView grid_trend_header=view.findViewById(R.id.grid_trend_header);
        mLeftItemH = getResources().getDimensionPixelSize(R.dimen.item_wh);
        chatview = view.findViewById(R.id.chatview);
        for (int i = 0; i <120 ; i++) {
            OpenNumber number = new OpenNumber();
            if (i == 50 || i == 52 || i == 69 || i == 72) number.setOpennumber("等待开奖");
            else number.setOpennumber(chatview.getTestData()) ;
            number.setQihao(2018911 + i);
            list.add(number);
            qihaoList.add("201811" + i);
        }
        chatview.setData(list);


        MyAdapter myAdapter = new MyAdapter(R.layout.items, qihaoList, mLeftItemH);
        lv_number.setAdapter(myAdapter);
        for (int i = 0; i < 10; i++) {
            tishi.add(String.valueOf(i));
        }
        MyAdapter adapter = new MyAdapter(R.layout.gridview_item, tishi, mLeftItemH);
        int deltaDp = getResources().getDimensionPixelSize(R.dimen.item_wh);
        //下面的代码是重新定位布局参数;让gridView数据都显示在一行;
        LinearLayout.LayoutParams params =new  LinearLayout.LayoutParams(adapter.getCount() * deltaDp,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        grid_trend_header.setLayoutParams(params);
        grid_trend_header.setColumnWidth(deltaDp); //列宽
        grid_trend_header.setStretchMode(GridView.NO_STRETCH);  //伸展模式
        grid_trend_header.setNumColumns(adapter.getCount()); //共有多少列
        grid_trend_header.setAdapter(adapter);
        scroll_content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scroll_content.fullScrollV(View.FOCUS_DOWN);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.w(ChartView.Tag,index+"页面销毁中");
        chatview=null;
    }

    private void setlistener() {

    }

    private void initdata() {

    }

    @Override
    public void changeXScroll(int left) {
        scroll_content.scrollTo(left, scroll_content.getScrollY());
        trend_header_scroll.scrollTo(left, 0);
    }

    @Override
    public void changeYScoll(int top) {
        scroll_content.scrollTo(scroll_content.getScrollX(), top);
        scroll_left.scrollTo(scroll_content.getScrollX(), top);
        trend_header_scroll.scrollTo(scroll_content.getScrollX(), top);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_wan:
                chatview.setIndex(0);
                break;
            case R.id.btn_qian:
                chatview.setIndex(1);
                break;
            case R.id.btn_hide:
                chatview.setCombileLine(false);
                chatview.updateUI();
                break;
            case R.id.btn_show:
                chatview.setCombileLine(true);
                chatview.updateUI();
                break;
            case R.id.btn_heji_hide:
                chatview.setHeji(0);
                chatview.requestLayout();
                chatview.updateUI();
                break;
            case R.id.btn_heji_show:
                chatview.setHeji(3);
                chatview.requestLayout();
                chatview.updateUI();
                break;


        }
    }

    class MyAdapter extends BaseAdapter{
        int layoutID ;
        ArrayList<String> data;
        int mLeftItemH ;
        MyAdapter(int layoutID,ArrayList<String> data,int mLeftItemH ){
            this.layoutID=layoutID;
            this.data=data;
            this.mLeftItemH=mLeftItemH;
        }

        @Override
        public int getCount() {

                return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (null == convertView) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(layoutID, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.tv_content = convertView.findViewById(R.id.tv_content);
                holder.back = convertView.findViewById(R.id.back);
                if (layoutID == R.layout.items) {
                    holder.v_line1 = convertView.findViewById(R.id.v_line1);
                    holder.v_line2 = convertView.findViewById(R.id.v_line2);
                    ViewGroup.LayoutParams vP1 = holder.v_line1.getLayoutParams();
                    vP1.height = (int) (getScreenDenisty() * 0.6f / 160);
                    holder.v_line1.setLayoutParams(vP1);
                    holder.v_line2.setLayoutParams(vP1);
                    int diff = mLeftItemH - vP1.height * 2;
                    ViewGroup.LayoutParams  contentParams = holder.tv_content.getLayoutParams();
                    contentParams.height = diff;
                    holder.tv_content.setLayoutParams(contentParams);
                } else {
                    //R.layout.gridview_item
                    holder.v_line3 = convertView.findViewById(R.id.v_line3);
                    holder.v_line4 = convertView.findViewById(R.id.v_line4);
                    ViewGroup.LayoutParams vP3 = holder.v_line3.getLayoutParams();
                    vP3.width = (int) (getScreenDenisty() * 0.6f / 160);
                    holder.v_line3.setLayoutParams(vP3);
                    holder.v_line4.setLayoutParams(vP3);
                    int diff = mLeftItemH - vP3.width * 2;
                    RelativeLayout rlContent = convertView.findViewById(R.id.relative_layout);
                    ViewGroup.LayoutParams contentParams = rlContent.getLayoutParams();
                    contentParams.width = diff;
                    rlContent.setLayoutParams(contentParams);

                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_content.setText(data.get(position));
            if (layoutID == R.layout.items) {
                if (position % 2 == 0) {
                    holder.back.setBackgroundColor(Color.WHITE);
                } else {
                    holder.back.setBackgroundColor(Color.parseColor("#F3EFE2"));
                }
            }


            return convertView;
        }
        private class ViewHolder {
            TextView tv_content;
            LinearLayout back;
            View v_line1;
            View v_line2;
            View v_line3;
            View v_line4;
        }
    }
    /**
     * 获取当前屏幕的密度
     *
     * @return
     */
    public int getScreenDenisty(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

}
