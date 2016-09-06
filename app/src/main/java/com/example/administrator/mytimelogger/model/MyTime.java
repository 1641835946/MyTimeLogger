package com.example.administrator.mytimelogger.model;

import com.example.administrator.mytimelogger.util.SmallUtil;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MyTime implements Comparable<MyTime>{

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public MyTime() {}

    public MyTime(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public MyTime(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        String dateFormat = year + "-" +
                SmallUtil.generatePart(month) + "-" +
                SmallUtil.generatePart(day) + " " +
                SmallUtil.generatePart(hour) + ":" +
                SmallUtil.generatePart(minute) + ":" +
                SmallUtil.generatePart(second);
        return dateFormat;
    }

    @Override
    public int compareTo(MyTime o) {
        long thisTime = month*100000000 + day*1000000 + hour*10000 + minute*100 + second;
        long thatTime = o.month*100000000 + o.day*1000000 + o.hour*10000 + o.minute*100 + o.second;
        if (thisTime > thatTime) {
            return 1;
        } else if (thisTime < thatTime) {
            return -1;
        } else {
            return 0;
        }
    }
}
