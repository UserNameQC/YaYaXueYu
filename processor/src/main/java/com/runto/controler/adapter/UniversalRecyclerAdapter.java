package com.runto.controler.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/7/31 0031.
 */

public abstract class UniversalRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    private Context mContext;
    public List<T> mDatas;
    private int mLayoutId;
    private OnItemClickListener mItemClickListener;
    private onLongItemClickListener mLongItemClickListener;
    private Handler handler;
    private int type;

    public final static int ONE_PIC_TYPE = 1;
    public final static int SECOND_PIC_TYPE = 2;
    public final static int THREE_PIC_TYPE = 3;

    public UniversalRecyclerAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
    }

    public UniversalRecyclerAdapter(Handler handler, Context context, List<T> mDatas, int mLayoutId){
        this.mContext = context;
        this.handler = handler;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
    }

    public  UniversalRecyclerAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public UniversalRecyclerAdapter(Context context, List<T> mDatas, int mLayoutId, int type){
        this.mContext = context;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
        this.type = type;
    }

    public void updateData(List<T> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getList(){
        return  mDatas;
    }

    public void addAll(List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void AddHeaderItem(List<T> items){
        mDatas.addAll(0,items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(List<T> items){
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(T t, int position){
        mDatas.add(position, t);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        view.setOnClickListener(this);
        return new BaseViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        convert(mContext, holder, mDatas.get(position), position);
        if (mItemClickListener != null) {
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position);
                }
            });
        }
        if (mLongItemClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongItemClickListener.onLongItemClick(v, position);
                    return true;
                }
            });
        }

    }

    protected abstract void convert(Context mContext, BaseViewHolder holder, T t, int position);

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null){
            mItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface onLongItemClickListener {
        void onLongItemClick(View view, int postion);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setonLongItemClickListener(onLongItemClickListener listener) {
        this.mLongItemClickListener = listener;
    }

    public final void clear(){
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void notifyList(List<T> list){
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }
}
