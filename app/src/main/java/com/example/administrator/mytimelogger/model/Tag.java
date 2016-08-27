package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Tag {

    private int id;
    private String name;
    private long color;
    private long icon;

    public Tag() {
    }

    public Tag(String name, long color, long icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public Tag(int id, String name, long color, long icon) {
        this(name, color, icon);
        this.id = id;
    }

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
