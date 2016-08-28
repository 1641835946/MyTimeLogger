package com.example.administrator.mytimelogger;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mytimelogger.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.MyViewHolder> implements View.OnClickListener {

    private List<Tag> mDatas;
    private OnRecyclerViewItemClickListener listener;
    private boolean isGrid = false;

    public TagListAdapter(List<Tag> mDatas, boolean isGrid) {
        this.mDatas = mDatas;
        this.isGrid = isGrid;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MyViewHolder holder;
        if (isGrid) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_grid, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_list, parent, false);
        }
        view.setOnClickListener(this);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position).getName());
        holder.iv.setImageResource((int)mDatas.get(position).getIcon());
        holder.itemView.setTag(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null) {
            listener.onItemClick(view, (Tag)view.getTag());
        }
    }

    public interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, Tag tag);
    }

}
