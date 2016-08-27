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
public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.MyViewHolder> {

    private List<Tag> mDatas;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position).getName());
        holder.iv.setImageResource((int)mDatas.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return init().size();
    }

    private List<Tag> init() {
        mDatas = new ArrayList<>();
        Tag tag1 = new Tag(1, "fight", Color.YELLOW, R.drawable.pause_btn);
        Tag tag2 = new Tag(2, "hello", Color.GRAY, R.drawable.resume_btn);
        Tag tag3 = new Tag(3, "world", Color.GREEN, R.drawable.stop_btn);
        Tag tag4 = new Tag(4, "liang", Color.BLUE, R.drawable.icon);
        Tag tag5 = new Tag(5, "jie", Color.RED, R.drawable.abc_ic_search_api_mtrl_alpha);
        mDatas.add(tag1);
        mDatas.add(tag2);
        mDatas.add(tag3);
        mDatas.add(tag4);
        mDatas.add(tag5);
        Log.e("Thread", "fragment init " + Thread.currentThread().getId());
        return mDatas;
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
}
