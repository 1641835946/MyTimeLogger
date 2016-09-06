package com.example.administrator.mytimelogger.model;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ActivityItem4View {

    private int state;
    private Tag tag;
    private Set set;//与Tag有重复，tag'id

    public ActivityItem4View() {}

    public ActivityItem4View(int state, Tag tag, Set set) {
        this.state = state;
        this.tag = tag;
        this.set = set;
    }

    public Tag getTag() {return tag;}

    public int getState() {
        return state;
    }

    public Set getSet() {
        return set;
    }

    public void setTag(Tag tag) {this.tag = tag;}
    public void setState(int state) {
        this.state = state;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
