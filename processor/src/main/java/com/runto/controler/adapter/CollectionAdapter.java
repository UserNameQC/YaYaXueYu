package com.runto.controler.adapter;

import android.content.Context;
import android.view.View;

import com.runto.controler.R;
import com.runto.controler.adapter.BaseViewHolder;
import com.runto.controler.adapter.UniversalRecyclerAdapter;
import com.runto.controler.bean.HomeSchoolBean;

import java.util.List;

public class CollectionAdapter extends UniversalRecyclerAdapter<HomeSchoolBean> {

    public onClickListen onClickListen;
    public CollectionAdapter(Context mContext, List<HomeSchoolBean> mDatas) {
        super(mContext, mDatas, R.layout.percent_collectin_item_layout);
    }

    @Override
    protected void convert(Context mContext, BaseViewHolder holder, final HomeSchoolBean homeSchoolBean, final int position) {
        holder.setText(R.id.item_title, homeSchoolBean.getTitle());
        holder.setText(R.id.item_location_msg, homeSchoolBean.getAddress());
        holder.setOnClickListener(R.id.item_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListen != null){
                    onClickListen.setOnClickListen(v, position, homeSchoolBean);
                }
            }
        });
    }

    public interface onClickListen{
        void setOnClickListen(View v, int position, HomeSchoolBean homeSchoolBean);
    }

    public void setOnClickListen(onClickListen onClickListen){
        this.onClickListen = onClickListen;
    }
}
