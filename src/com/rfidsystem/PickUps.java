package com.rfidsystem;

/**
 * Created by michaelAM on 2017-03-22.
 */
public class PickUps {

    private long startTime;
    private long endTime;
    private long duration;

    public PickUps(long startTime, long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = endTime - startTime;
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

}
