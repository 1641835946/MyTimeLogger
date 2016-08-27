package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Activities {

    private int setId;//群组的ID，多个活动可以有一个共同的群组ID
    private Time beginTime;
    private Time endTime;
    private long duration;

    public Activities() {}
    public Activities(int setId, Time beginTime, Time endTime, long duration) {
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

    public Time getEndTime() {
        return endTime;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Time getBeginTime() {
        return beginTime;
    }
}
