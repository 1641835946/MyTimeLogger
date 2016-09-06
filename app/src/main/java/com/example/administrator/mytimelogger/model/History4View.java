package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/31.
 */
public class History4View {

    private Activities activities;
    private Tag tag;
    private int viewType;
    private String yearMouthDay;

//    public History4View() {}

    public History4View(Activities activities, Tag tag, int viewType) {
        this.activities = activities;
        this.tag = tag;
        this.viewType = viewType;
    }

    public History4View(int viewType, String yearMouthDay) {
        this.viewType = viewType;
        this.yearMouthDay = yearMouthDay;
    }

    public int getViewType() {
        return viewType;
    }

    public String getYearMouthDay() {
        return yearMouthDay;
    }

    public Activities getActivities() {
        return activities;
    }

    public Tag getTag() {
        return tag;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setYearMouthDay(String yearMouthDay) {
        this.yearMouthDay = yearMouthDay;
    }
}
