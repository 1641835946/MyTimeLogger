package com.example.administrator.mytimelogger.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Tag implements Serializable{

    private int id;
    private String name;
    private int color;//R.color
    private int icon;

    public Tag() {
    }

    public Tag(String name, int color, int icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public Tag(int id, String name, int color, int icon) {
        this(name, color, icon);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**不比较id，用于EditAddTagActivity中添加或修改Tag*/
    @Override
    public boolean equals(Object obj) {//这种情况需要自己写吗？
        Tag thatTag = (Tag) obj;
        if (this == thatTag) {
            return true;
        } else if (this.getName().equals(thatTag.getName())){
            return false;
        } else if (this.color != thatTag.color) {
            return false;
        } else if (this.icon != thatTag.icon) {
            return false;
        }
        return true;
    }
}
