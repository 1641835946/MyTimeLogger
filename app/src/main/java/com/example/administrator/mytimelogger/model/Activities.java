package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Activities {

    private int setId;//群组的ID，多个活动可以有一个共同的群组ID
    private MyTime beginTime;
    private MyTime endTime;
    private long duration;

    public Activities() {}
    public Activities(int setId, MyTime beginTime, MyTime endTime, long duration) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.duration = duration;
        this.setId = setId;
    }

    public int getSetId() {
        return setId;
    }

    public long getDuration() {
        return duration;
    }

    public MyTime getEndTime() {
        return endTime;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public void setBeginTime(MyTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(MyTime endTime) {
        this.endTime = endTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public MyTime getBeginTime() {
        return beginTime;
    }
}
