package com.rfidsystem;

import java.util.Calendar;

/**
 * Created by michaelAM on 2017-03-22.
 */
public class PickUps {

    private Item item;
    private long startTime;
    private long endTime;
    private long duration;
    private int day;
    private int month;
    private int year;

    public PickUps(long startTime, long endTime,Item item){
        this.item = item;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = endTime - startTime;
        java.util.Date startTimeDate = new java.util.Date(startTime);
        java.util.Date endTimeDate = new java.util.Date(endTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTimeDate);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.month = cal.get(Calendar.MONTH) + 1;
        this.year = cal.get(Calendar.YEAR);
        System.out.println("New PickUp from: "+startTimeDate+" to: "+endTimeDate);

    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
