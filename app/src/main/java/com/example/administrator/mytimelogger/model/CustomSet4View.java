package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/26.
 */
public class CustomSet4View {

    private int state;
    private String tagName;
    private int tagIcon;
    private Set set;

    public CustomSet4View() {}

    public CustomSet4View(int state, String tagName, int tagIcon, Set set) {
        this.state = state;
        this.tagName = tagName;
        this.tagIcon = tagIcon;
        this.set = set;
    }

    public void setTagIcon(int tagIcon) {
        this.tagIcon = tagIcon;
    }

    public int getTagIcon() {
        return tagIcon;
    }

    public String getTagName() {return tagName;}

    public int getState() {
        return state;
    }

    public Set getSet() {
        return set;
    }

    public void setTagName(String tagName) {this.tagName = tagName;}

    public void setState(int state) {
        this.state = state;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
