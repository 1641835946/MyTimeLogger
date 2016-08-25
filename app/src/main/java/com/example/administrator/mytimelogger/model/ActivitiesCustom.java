package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/23.
 */
public class ActivitiesCustom {

    private String name;
    private long icon;
    private int state;
    private Time beginTime;
    private long duration;
    private int setID;

    public String getName() {
        return name;
    }

    public long getIcon() {
        return icon;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public int getState() {
        return state;
    }

    public long getDuration() {
        return duration;
    }

    public int getSetID() {
        return setID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(long icon) {
        this.icon = icon;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setSetID(int setID) {
        this.setID = setID;
    }
}
