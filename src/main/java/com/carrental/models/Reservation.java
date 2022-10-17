package com.carrental.models;

import com.carrental.enums.VehicleType;

public class Reservation {

    private final VehicleType vehicleType;
    private final String branchId;
    private final Duration duration;


    public Reservation(String branchId, VehicleType vehicleType, int startTime, int endTime) {
        this.branchId = branchId;
        this.vehicleType = vehicleType;
        this.duration = new Duration(startTime, endTime);
    }

    public String getBranchId() {
        return branchId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }


    public Duration getBookingDuration() {
        return duration;
    }

}
