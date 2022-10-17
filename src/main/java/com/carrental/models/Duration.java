package com.carrental.models;

public class Duration {
    private final int startTime;
    private final int endTime;

    public Duration(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

}
