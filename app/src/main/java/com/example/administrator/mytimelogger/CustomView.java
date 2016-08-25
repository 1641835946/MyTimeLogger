package com.example.administrator.mytimelogger;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.mytimelogger.util.DurationView;

/**
 * Created by Administrator on 2016/8/24.
 */
public class CustomView extends RelativeLayout {

    private float nameSize = 10;
    private TextView tagNameTv;
    private float durationSize = 10;
    private DurationView durationTv;
    private String duration = "00:00:00";
    private int playPauseIcon = R.drawable.resume_btn;
    private Button playBtn;
    private int stopIcon = R.drawable.stop_btn;
    private Button stopBtn;
    private ImageView tagIconIv;
    private LayoutParams tagParams;
    private LayoutParams playParams;
    private LayoutParams stopParams;
    private LayoutParams nameParams;
    private LayoutParams durationParams;
    private controlClick mListener;
    private int iconSize = 90;
    private int margin = 10;
    private boolean doing = false;

    //index是用于子控件的setId();是指第几个组合控件。
    public CustomView(Context context,
                      int index,
                      String tagName,
                      int tagIcon) {
        super(context);
        init(context, index, tagName, tagIcon);
        //动态添加，所以不能事先在XML中编辑属性。
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
//        nameSize = ta.getDimension(R.styleable.CustomView_nameSize, 10);
//        tagName = ta.getString(R.styleable.CustomView_tagName);
//        durationSize = ta.getDimension(R.styleable.CustomView_durationSize, 10);
//        playPauseIcon = ta.getDrawable(R.styleable.CustomView_playPauseIcon);
//        stopIcon = ta.getDrawable(R.styleable.CustomView_stopIcon);
//        tagIcon = ta.getDrawable(R.styleable.CustomView_tagIcon);
//        ta.recycle();
    }

    private void init(Context context, int index, String tagName, int tagIcon) {
        tagIconIv = new ImageView(context);
        tagNameTv = new TextView(context);
        durationTv = new DurationView(context);
        playBtn = new Button(context);
        stopBtn = new Button(context);
        tagIconIv.setId(100*index);
        tagNameTv.setId(100 * index + 1);
        durationTv.setId(100 * index + 2);
        playBtn.setId(100 * index + 3);
        stopBtn.setId(100 * index + 4);

        tagNameTv.setText(tagName);
        tagNameTv.setTextSize(nameSize);
        durationTv.setText(duration);
        durationTv.setTextSize(durationSize);
        playBtn.setBackgroundResource(playPauseIcon);
        stopBtn.setBackgroundResource(stopIcon);
        tagIconIv.setImageResource(tagIcon);

        tagParams = new LayoutParams(iconSize, iconSize);
        tagParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tagParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        tagParams.setMargins(margin, margin, margin, margin);
        addView(tagIconIv, tagParams);

        nameParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        nameParams.leftMargin=10;//是指到父控件的边缘或边上的控件的距离
        nameParams.addRule(RelativeLayout.ALIGN_TOP, tagIconIv.getId());
        nameParams.addRule(RelativeLayout.RIGHT_OF, tagIconIv.getId());
        addView(tagNameTv, nameParams);

        durationParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        durationParams.leftMargin = margin;
        durationParams.topMargin = margin;
        durationParams.bottomMargin = margin;
        durationParams.addRule(RelativeLayout.BELOW, tagNameTv.getId());
        durationParams.addRule(RelativeLayout.RIGHT_OF, tagIconIv.getId());
        addView(durationTv, durationParams);

        stopParams = new LayoutParams(iconSize, iconSize);
        stopParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        stopParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        stopParams.setMargins(margin, margin, margin, margin);
        addView(stopBtn, stopParams);

        playParams = new LayoutParams(iconSize, iconSize);
        playParams.addRule(RelativeLayout.LEFT_OF, stopBtn.getId());
        playParams.addRule(RelativeLayout.RIGHT_OF, durationTv.getId());
        playParams.addRule(RelativeLayout.RIGHT_OF, tagNameTv.getId());
        playParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(playBtn, playParams);

        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.playPauseClick();
            }
        });

        stopBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.stopClick();
            }
        });
    }

    //在这里，durationTv.setText(duration),postInvalidateDelayed(1000)没有效果。
    //诡异的是，在setDoing()中，又可以了。
//    int i = 1;
//    @Override
//    protected void onDraw(Canvas canvas) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                do {
//                    i++;
//                } while(i<100);
//            }
//        }).start();
//        duration = ""+i;
//        //durationTv.setText(duration);
//        postInvalidateDelayed(1000);
//        super.onDraw(canvas);
//    }

    public void setControlClick(controlClick mListener) {
        this.mListener = mListener;
    }

    public interface controlClick {
        void playPauseClick();
        void stopClick();
        void onItemClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mListener.onItemClick();
        return true;//是否要分情况return。
    }

    public DurationView getDurationTv() {
        return durationTv;
    }

    public void setDurationTv(DurationView durationTv) {
        this.durationTv = durationTv;
    }

    public boolean getDoing() {
        return doing;
    }

    int i = 1;
    public void setDoing(boolean doing) {
        this.doing = doing;
        if (doing) {
            playBtn.setBackgroundResource(R.drawable.pause_btn);
            durationTv.setText("liangjie : " + i);
        } else {
            playBtn.setBackgroundResource(R.drawable.resume_btn);
            durationTv.setText("liangjie : " + i);
        }
        i++;
        postInvalidateDelayed(1000);
        //invalidate();
    }
}
