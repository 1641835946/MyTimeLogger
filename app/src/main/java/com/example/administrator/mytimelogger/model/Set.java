package com.example.administrator.mytimelogger.model;

import com.example.administrator.mytimelogger.model.Time;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Set {

    private int setID;//群组的ID，多个活动可以有一个共同的群组ID,这里是用于存储Activities
    private int tagID;
    private String commit;
    private long duration;
    private Time beginTime;

    public Set() {}

    public Set(int tagID, String commit, long duration, Time beginTime) {
        this.tagID = tagID;
        this.commit = commit;
        this.duration = duration;
        this.beginTime = beginTime;
    }

    public Set(int setID, int state, int tagID, String commit, long duration, Time beginTime) {
        this(tagID, commit, duration, beginTime);
        this.setID = setID;
    }

    public void setSetID(int setID) {
        this.setID = setID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public int getSetID() {
        return setID;
    }

    public int getTagID() {
        return tagID;
    }

    public String getCommit() {
        return commit;
    }

    public long getDuration() {
        return duration;
    }

    public Time getBeginTime() {
        return beginTime;
    }
}
