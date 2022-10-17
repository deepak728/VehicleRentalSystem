package com.carrental.models;

public class Display {
    private final String branchId;
    private final Duration duration;

    public Display(String branchId, int startTime, int endTime) {
        this.branchId = branchId;
        this.duration = new Duration(startTime, endTime);
    }

    public String getBranchId() {
        return branchId;
    }

    public Duration getDuration() {
        return duration;
    }
}
