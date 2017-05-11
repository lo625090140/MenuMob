package com.demo.mob.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.mob.activity.R;
import com.demo.mob.fragment.IData;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjt on 2017/5/2.
 */

public class mSimpleAdapter extends RecyclerView.Adapter<mViewHolder> {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;
    private List<Integer> mHeight;
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLonglistener;


    public mSimpleAdapter(Context context, List<String> list){
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
        mHeight = new ArrayList<Integer>();
        for (int i = 0 ; i < list.size();i++){
            mHeight.add((int) (100 + Math.random()*300));
        }
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_baseui_item,parent,false);
        AutoUtils.autoSize(view);
        mViewHolder holder = new mViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeight.get(position);
        holder.itemView.setLayoutParams(lp);
        holder.itemView.setOnClickListener(v -> {if (mListener != null)mListener.onItemClick(holder.itemView,holder.getLayoutPosition());});
        holder.itemView.setOnLongClickListener(v -> {if(mLonglistener != null){mLonglistener.onItemLongClick(holder.itemView,holder.getLayoutPosition());} return false;});
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addItem(int position,String data){
        mList.add(position,data);
        mHeight.add((int) (100 + Math.random()*300));
        notifyItemInserted(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public void setOnItemClickListener(OnItemLongClickListener listener){
        this.mLonglistener = listener;
    }

    public void removeItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public interface OnItemLongClickListener{
        boolean onItemLongClick(View view,int position);
    }

}

class mViewHolder extends RecyclerView.ViewHolder{
    TextView tv;
    public mViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.id_recyclerview_item_text);
    }
}
