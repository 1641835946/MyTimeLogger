package com.example.administrator.mytimelogger.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class GridTag {

    private long icon;
    private String name;
    //唯一标识一个tag。作用是点击时，添加ActivitiesCustom时，知道添加的标签类型。
    private int tagID;

    public void setIcon(long icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public long getIcon() {
        return icon;
    }

    public int getTagID() {
        return tagID;
    }

    public String getName() {
        return name;
    }
}
