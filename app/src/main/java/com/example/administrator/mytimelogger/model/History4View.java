package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/31.
 */
public class History4View {

    private Activities activities;
    private Tag tag;
    private int viewType;
    private int yearMouthDay;

    public History4View() {}

    public History4View(Activities activities, Tag tag, int viewType) {
        this.activities = activities;
        this.tag = tag;
        this.viewType = viewType;
    }

    public History4View(int viewType, int yearMouthDay) {
        this.viewType = viewType;
        this.yearMouthDay = yearMouthDay;
    }

    public History4View(int viewType, String test) {
        this.viewType = viewType;
        this.tag = new Tag();
        tag.setName(test);
    }

    public int getViewType() {
        return viewType;
    }

    public int getYearMouthDay() {
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

    public void setYearMouthDay(int yearMouthDay) {
        this.yearMouthDay = yearMouthDay;
    }
}
