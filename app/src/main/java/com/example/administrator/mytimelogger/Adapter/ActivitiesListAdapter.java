package com.example.administrator.mytimelogger.Adapter;

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

import com.example.administrator.mytimelogger.Activity.MainActivity;
import com.example.administrator.mytimelogger.R;
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
    private List<ActivityItem4List> mList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    for (int i = 0; i < mList.size(); i++) {
                        try {
                            if (MainActivity.setList.get(i).getState() == Constant.STATE_PLAY) {
                                //计算未结束的activities与之前所有同一群组的activities的总时长
                                String duration = getStrDuration(i);
                                mList.get(i).getDuration().setText(duration);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                default:
                    break;
            }
        }
    };

    private String getStrDuration(int position) {
        MyTime begin = MainActivity.setList.get(position).getSet().getBeginTime();
        MyTime end = SmallUtil.gainTime();
        int lastTime = MainActivity.setList.get(position).getSet().getDuration();
        int allTime = lastTime + SmallUtil.gainIntDuration(begin, end);
        return SmallUtil.gainSetDuration(allTime);
    }
    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(!stopThread) {
                if (pauseDoing) {
                    pauseThread();
                }
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
        return MainActivity.setList.size();
    }

    public ActivitiesListAdapter() {
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
        SmallUtil.changeColor(viewHolder.tagIcon, MainActivity.setList.get(position).getTag());
        viewHolder.tagNameTv.setText(MainActivity.setList.get(position).getTag().getName());
        int duration = MainActivity.setList.get(position).getSet().getDuration();
        if (duration == 0) {
            Log.e("onBindViewHolder", "duration :" + duration);
            viewHolder.durationTv.setText("00:00:00");
        } else {
            viewHolder.durationTv.setText(getStrDuration(position));
        }
        int state = MainActivity.setList.get(position).getState();
        if (state == Constant.STATE_PLAY){
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.pause_btn);
        } else {
            viewHolder.pauseImgBtn.setBackgroundResource(R.drawable.resume_btn);
        }

        ActivityItem4List customSet4ListItem = new ActivityItem4List(viewHolder.durationTv,
                viewHolder.pauseImgBtn);
        mList.add(customSet4ListItem);
//        startThread();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onItemClick(v, MainActivity.setList.get(pos));
            }
        });
        viewHolder.pauseImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onStateChangeClick(v, MainActivity.setList.get(pos));
            }
        });
        viewHolder.stopImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getLayoutPosition();
                mOnItemClickListener.onEndClick(v, MainActivity.setList.get(pos));
            }
        });
    }


    private boolean stopThread = false;
    private boolean pauseDoing = false;
    public void setStopThread() {
        stopThread = true;
    }
    public void controlThread(boolean keep) {
        if (keep) {
            resumeThread();
        } else {
            pauseDoing = true;
        }
    }
    //方法不了解不要乱用，很浪费时间找原因的
    //首先：知道用这个方法的效果是怎样的，别不知道效果就用，毕竟谁知道那是Bug还是特色呢？
    //其次：从demo学习怎么使用它。
    public boolean isAlive() {
        if (mThread.isAlive()) {
            return true;
        }
        return false;
    }
    public void startThread() {
        mThread.start();
    }

    public void  resumeThread() {
        if (mThread.isAlive()) {
            synchronized(mThread) {
                mThread.notify(); // 恢复线程
            }
        } else {
            mThread.start();
        }
    }
    public void pauseThread() {
        synchronized(mThread) {
            try {
                mThread.wait(); // 暂停线程
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        pauseDoing = false;
    }

    public void notifyChanged() {
        mList = new ArrayList<>();
        Log.e("ActivitiesListAdapter", "size is " + MainActivity.setList.size());
        notifyDataSetChanged();
        Log.e("ActivitiesListAdapter", "notifyChanged");
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
