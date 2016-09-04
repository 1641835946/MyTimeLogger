package com.example.administrator.mytimelogger.ActivityMain.ActivityActivities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.model.CustomSet4List;
import com.example.administrator.mytimelogger.model.CustomSet4View;
import com.example.administrator.mytimelogger.model.MyTime;
import com.example.administrator.mytimelogger.util.Constant;
import com.example.administrator.mytimelogger.util.SmallUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ActivitiesListAdapter extends RecyclerView.Adapter<ActivitiesListAdapter.ViewHolder>{

    private static final int UPDATE_TEXT = 1;
    private List<CustomSet4View> datas;
    private List<CustomSet4List> mList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    MyTime begin = datas.get(msg.arg1).getSet().getBeginTime();
                    MyTime end = SmallUtil.gainTime();
                    int durationInt = datas.get(msg.arg1).getSet().getDuration();
                    durationInt = durationInt + SmallUtil.gainIntDuration(begin, end);
                    String duration = SmallUtil.gainStringDuration(durationInt);
                    mList.get(msg.arg1).getDuration().setText(duration);
                    break;
                default:
                    break;
            }
        }
    };

    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(keepDoing) {
                synchronized (this) {
                    for (int i = 0; i < mList.size(); i++) {
                        if (datas.get(i).getState() == Constant.STATE_PLAY) {
                            Message msg = new Message();
                            msg.what = UPDATE_TEXT;
                            msg.arg1 = i;
                            handler.sendMessage(msg);
                        }
                    }
                }
                try {
                    mThread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public ActivitiesListAdapter(List<CustomSet4View> datas) {this.datas = datas;}

    private final static String TAG = "ActivitiesListAdapter";
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activities_list, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // viewHolder.tagIcon.setImageResource(.....);
        // 会导致imagebutton前景与背景不一样大小形状
        // 因为android:src的图是不会拉伸的
//        viewHolder.tagIcon.setBackgroundResource(datas.get(position).getTag().getIcon());
        SmallUtil.changeColor(viewHolder.tagIcon, datas.get(position).getTag());
        viewHolder.tagNameTv.setText(datas.get(position).getTag().getName());
        int duration = datas.get(position).getSet().getDuration();
        if (duration == 0) {
            viewHolder.durationTv.setText("00:00");
        } else {
            viewHolder.durationTv.setText(SmallUtil.gainStringDuration(duration));
        }
        int state = datas.get(position).getState();
        if (state == Constant.STATE_PLAY){
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.pause_btn);
        } else {
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.resume_btn);
        }

        //监听器会不会？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        CustomSet4List customSet4ListItem = new CustomSet4List(viewHolder.durationTv,
                viewHolder.pauseImgBtn);
        mList.add(customSet4ListItem);
//            mList.set(position, customSet4ListItem);
        startThread();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onItemClick(v, datas.get(pos));
            }
        });
        viewHolder.pauseImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onStateChangeClick(v, datas.get(pos));
            }
        });
        viewHolder.stopImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onEndClick(v, datas.get(pos));
            }
        });
    }

    private void startThread() {
        if (mThread.isAlive()) {}
        else {
            mThread.start();
        }
    }
    private boolean keepDoing = true;
    public void stopThread() {
        if (mThread.isAlive()) {
            keepDoing = false;
        }
    }
    public void suspendThread() {
        if (mThread.isAlive()) {
            mThread.suspend();
        }
    }
    public void resumeThread() {
        if (!mThread.isAlive()) {
            mThread.resume();
        }
    }

    public void notifyChanged() {
        synchronized (this) {
            mList = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView tagIcon;
        public TextView tagNameTv;
        public TextView durationTv;
        public ImageButton pauseImgBtn;
        public ImageButton stopImgBtn;
        public ViewHolder(View view){
            super(view);
            tagIcon = (AppCompatImageView) view.findViewById(R.id.tag_icon);
            tagNameTv = (TextView) view.findViewById(R.id.tag_name);
            durationTv = (TextView) view.findViewById(R.id.duration);
            pauseImgBtn = (ImageButton) view.findViewById(R.id.pause_img_btn);
            stopImgBtn = (ImageButton) view.findViewById(R.id.stop_img_btn);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , CustomSet4View data);
        void onStateChangeClick(View view, CustomSet4View data);
        void onEndClick(View view, CustomSet4View data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
