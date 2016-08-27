package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/26.
 */
public class CustomSet {

    private int state;
    private Set set;

    public CustomSet() {}

    public CustomSet(int state, Set set) {
        this.state = state;
        this.set = set;
    }

    public int getState() {
        return state;
    }

    public Set getSet() {
        return set;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
