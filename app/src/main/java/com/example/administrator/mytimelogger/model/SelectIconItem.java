package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/9/12.
 */
public class SelectIconItem {

    private String item;
    private int type;
    public int sectionManager;
    public int sectionFirstPosition;

    public SelectIconItem(String item, int type,int sectionManager, int sectionFirstPosition) {
        this.item = item;
        this.type = type;
        this.sectionManager = sectionManager;
        this.sectionFirstPosition = sectionFirstPosition;
    }

    public String getItem() {
        return item;
    }

    public int getType() {
        return type;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setType(int type) {
        this.type = type;
    }
}
