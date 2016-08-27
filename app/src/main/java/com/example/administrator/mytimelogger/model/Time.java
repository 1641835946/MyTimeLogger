package com.example.administrator.mytimelogger.model;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Time {

    private int year;
    private int mouth;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public Time() {}

    public Time(int year, int mouth, int day, int hour, int minute, int second) {
        this.year = year;
        this.mouth = mouth;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getYear() {
        return year;
    }

    public int getMouth() {
        return mouth;
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

    public void setMouth(int mouth) {
        this.mouth = mouth;
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
}
