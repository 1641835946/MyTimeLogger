package com.example.administrator.mytimelogger.model;

import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/2.
 */
public class CustomSet4List {

    private TextView duration;
    private ImageButton pause;

    public CustomSet4List() {}

    public CustomSet4List(TextView duration, ImageButton pause) {
        this.duration = duration;
        this.pause = pause;
    }

    public TextView getDuration() {
        return duration;
    }

    public ImageButton getPause() {
        return pause;
    }


    public void setDuration(TextView duration) {
        this.duration = duration;
    }

    public void setPause(ImageButton pause) {
        this.pause = pause;
    }

}
