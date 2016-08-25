package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Tag {

    private int id;
    private String name;
    private long color;
    private long icon;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getColor() {
        return color;
    }

    public long getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(long icon) {
        this.icon = icon;
    }

    public void setColor(long color) {
        this.color = color;
    }
}
