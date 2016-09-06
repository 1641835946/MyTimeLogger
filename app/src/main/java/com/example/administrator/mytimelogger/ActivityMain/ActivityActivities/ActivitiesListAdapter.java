package com.example.administrator.mytimelogger.ActivityMain.ActivityActivities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.mytimelogger.R;
import com.example.administrator.mytimelogger.db.DB;
import com.example.administrator.mytimelogger.model.ActivityItem4List;
import com.example.administrator.mytimelogger.model.ActivityItem4View;
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
    private List<ActivityItem4View> setList;
    private List<ActivityItem4List> mList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    for (int i = 0; i < mList.size(); i++) {
                        if (setList.get(i).getState() == Constant.STATE_PLAY) {
                            //计算未结束的activities与之前所有同一群组的activities的总时长
                            MyTime begin = setList.get(i).getSet().getBeginTime();
                            MyTime end = SmallUtil.gainTime();
                            int lastTime = setList.get(i).getSet().getDuration();
                            int allTime = lastTime + SmallUtil.gainIntDuration(begin, end);
                            String duration = SmallUtil.gainSetDuration(allTime);
                            mList.get(i).getDuration().setText(duration);
                        }
                    }
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
                Message msg = new Message();
                msg.what = UPDATE_TEXT;
                handler.sendMessage(msg);
                try {
                    mThread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    //获取数据的数量
    @Override
    public int getItemCount() {
        return setList.size();
    }

    public ActivitiesListAdapter(List<ActivityItem4View> setList) {
        this.setList = setList;
    }

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
//        viewHolder.tagIcon.setBackgroundResource(setList.get(position).getTag().getIcon());
        SmallUtil.changeColor(viewHolder.tagIcon, setList.get(position).getTag());
        viewHolder.tagNameTv.setText(setList.get(position).getTag().getName());
        int duration = setList.get(position).getSet().getDuration();
        if (duration == 0) {
            viewHolder.durationTv.setText("00:00:00");
        } else {
            viewHolder.durationTv.setText(SmallUtil.gainSetDuration(duration));
        }
        int state = setList.get(position).getState();
        if (state == Constant.STATE_PLAY){
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.pause_btn);
        } else {
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.resume_btn);
        }

        ActivityItem4List customSet4ListItem = new ActivityItem4List(viewHolder.durationTv,
                viewHolder.pauseImgBtn);
        mList.add(customSet4ListItem);
        startThread();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onItemClick(v, setList.get(pos));
            }
        });
        viewHolder.pauseImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onStateChangeClick(v, setList.get(pos));
            }
        });
        viewHolder.stopImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onEndClick(v, setList.get(pos));
            }
        });
    }


    private boolean keepDoing = true;
    public void setKeepDoing(boolean keepDoing) {
        this.keepDoing = keepDoing;
    }
//    public void isAlive() {
//        if (mThread.isAlive()) {
//            Log.e("isAlive", "yes");
//        }
//        Log.e("isAlive", "log");
//    }
    public void startThread() {
        if (mThread.isAlive()) {
        }
        else {
            mThread.start();
//            try {
//                mThread.start();
//            } catch (IllegalThreadStateException e) {
//                mThread.notify();
//            }
        }
    }

    public void  resumeThread() {
        if (mThread.isAlive()) {
            synchronized(mThread) {
                mThread.notify(); // 恢复线程
            }
        }
    }
    public void pauseThread() {
//            try {
//                synchronized (mThread) {
//                    mThread.wait();
//                }
//            } catch(InterruptedException e) {
//                e.printStackTrace();
//            }
        Log.e("pauseThread", "first");
        synchronized(mThread) {
            try {
                Log.e("currentThread id is ", "" + Thread.currentThread().getId());

                Log.e("pauseThread", "second");
                mThread.wait(); // 暂停线程
                Log.e("pauseThread", "third");
            }catch(InterruptedException e) {
                Log.e("pauseThread", "forth");
                e.printStackTrace();
            }
        }
    }


    public void notifyChanged() {
        mList = new ArrayList<>();
        notifyDataSetChanged();
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
        void onItemClick(View view , ActivityItem4View data);
        void onStateChangeClick(View view, ActivityItem4View data);
        void onEndClick(View view, ActivityItem4View data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
