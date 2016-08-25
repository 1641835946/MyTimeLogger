package com.example.administrator.mytimelogger.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/25.
 */
public class DurationView extends TextView {

    private boolean start = false;

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean getStart() {
        return start;
    }

    private String durationStr;

    public DurationView(Context context) {
        super(context);
        init();
    }

    public DurationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DurationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private String generateDurationStr (long duration) {
        String durationStr = generatePart((int)(duration / 3600)) + ":" +
                generatePart((int)(duration / 3600 %60)) + ":" +
                generatePart((int)(duration % 60));
        return durationStr;
    }

    private void init() {

    }

    private String generatePart(int time) {
        String back;
        if (time < 10) {
            back = "0" + time;
        } else {
            back = "" + time;
        }
        return back;
    }

    int i = 1;
    @Override
    protected void onDraw(Canvas canvas) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                do {
//                    i++;
//                } while(start);
//            }
//        }).start();
//        durationStr = generateDurationStr(i);
//        this.setText(durationStr);
//        postInvalidateDelayed(1000);
        super.onDraw(canvas);
    }
}
