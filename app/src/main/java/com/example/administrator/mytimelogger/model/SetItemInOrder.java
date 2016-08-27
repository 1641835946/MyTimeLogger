package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/26.
 */
public class SetItemInOrder {

    private int setID;
    private int state;

    public SetItemInOrder() {}
    public SetItemInOrder(int setID, int state) {
        this.setID = setID;
        this.state = state;
    }

    public void setSetID(int setID) {
        this.setID = setID;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSetID() {
        return setID;
    }

    public int getState() {
        return state;
    }
}
