package com.example.administrator.mytimelogger;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mytimelogger.model.CustomSet4View;
import com.example.administrator.mytimelogger.util.Constant;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ActivitiesListAdapter extends RecyclerView.Adapter<ActivitiesListAdapter.ViewHolder> implements View.OnClickListener {

    private List<CustomSet4View> datas;

    public ActivitiesListAdapter(List<CustomSet4View> datas) {this.datas = datas;}

    private final static String TAG = "ActivitiesListAdapter";
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activities_list, parent, false);
        Log.e(TAG, "onCreateViewHolder : view's id is " + view.getId());
        view.setOnClickListener(this);
        ViewHolder vh = new ViewHolder(view);
        vh.pauseImgBtn.setOnClickListener(this);
        vh.stopImgBtn.setOnClickListener(this);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // viewHolder.tagIcon.setImageResource(.....);
        // 会导致imagebutton前景与背景不一样大小形状
        // 因为android:src的图是不会拉伸的
        viewHolder.tagIcon.setBackgroundResource(datas.get(position).getTagIcon());

        viewHolder.tagNameTv.setText(datas.get(position).getTagName());
        long duration = datas.get(position).getSet().getDuration();
        viewHolder.durationTv.setText(""+duration);
        int state = datas.get(position).getState();
        if (state != Constant.STATE_PAUSE){
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.resume_btn);
        } else {
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.pause_btn);
        }
        viewHolder.itemView.setTag(datas.get(position));
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView tagIcon;
        public TextView tagNameTv;
        public TextView durationTv;
        public ImageButton pauseImgBtn;
        public ImageButton stopImgBtn;
        public ViewHolder(View view){
            super(view);
            tagIcon = (ImageView) view.findViewById(R.id.tag_icon);
            tagNameTv = (TextView) view.findViewById(R.id.tag_name);
            durationTv = (TextView) view.findViewById(R.id.duration);
            pauseImgBtn = (ImageButton) view.findViewById(R.id.pause_img_btn);
            stopImgBtn = (ImageButton) view.findViewById(R.id.stop_img_btn);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , CustomSet4View data);
        void onStateChangeClick(View view, CustomSet4View data);
        void onEndClick(View view, CustomSet4View data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null)
        switch (view.getId()) {
            case -1:
                mOnItemClickListener.onItemClick(view, (CustomSet4View) view.getTag());
                break;
            case R.id.pause_img_btn:
                mOnItemClickListener.onStateChangeClick(view, (CustomSet4View) view.getTag());
                break;
            case R.id.stop_img_btn:
                mOnItemClickListener.onEndClick(view, (CustomSet4View) view.getTag());
                break;
        }
    }
}
