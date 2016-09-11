package com.example.administrator.mytimelogger.Adapter;


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mytimelogger.Activity.MainActivity;
import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.model.Tag;
import com.example.administrator.mytimelogger.util.SmallUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.MyViewHolder> implements View.OnClickListener {

    private OnRecyclerViewItemClickListener listener;
    private boolean isGrid = false;
    private Context context;

    public TagListAdapter(Context context, boolean isGrid) {
        this.isGrid = isGrid;
        this.context = context;
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
        holder.tv.setText(MainActivity.tagList.get(position).getName());
        SmallUtil.changeColor(holder.iv, MainActivity.tagList.get(position));
//        holder.iv.setBackground(icon);
        holder.itemView.setTag(MainActivity.tagList.get(position));
    }

    @Override
    public int getItemCount() {
        return MainActivity.tagList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView iv;
        TextView tv;
        public MyViewHolder(View view) {
            super(view);
            iv = (AppCompatImageView) view.findViewById(R.id.iv);
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
