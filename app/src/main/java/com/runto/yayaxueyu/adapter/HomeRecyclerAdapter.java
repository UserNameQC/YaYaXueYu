package com.runto.yayaxueyu.adapter;

import android.content.Context;
import android.view.View;

import com.runto.controler.adapter.BaseViewHolder;
import com.runto.controler.adapter.UniversalRecyclerAdapter;
import com.runto.controler.bean.HomeSchoolBean;
import com.runto.yayaxueyu.R;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerAdapter extends UniversalRecyclerAdapter<HomeSchoolBean> {

    public List<HomeSchoolBean> mDataList = new ArrayList<>();
    public OnClickListener onClickListener;
    public HomeRecyclerAdapter(Context context, List<HomeSchoolBean> mDatas) {
        super(context, mDatas, R.layout.percent_collectin_item_layout);
    }

    @Override
    protected void convert(Context mContext, BaseViewHolder holder, HomeSchoolBean homeSchoolBean, final int position) {
        holder.setOnClickListener(R.id.item_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     if (onClickListener!= null){
                         onClickListener.onClick(v, position);
                     }
            }
        });
    }

    public interface OnClickListener{
        void onClick(View view, int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
