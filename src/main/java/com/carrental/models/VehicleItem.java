package com.carrental.models;

import com.carrental.enums.VehicleType;

public class VehicleItem extends Vehicle {
    private final String vehicleId;
    private final String branchId;
    private final float price;

    public VehicleItem(VehicleType vehicleType, String vehicleNoPlate, String vehicleID, String branchId, float price) {
        super(vehicleType, vehicleNoPlate);
        this.vehicleId = vehicleID;
        this.branchId = branchId;
        this.price = price;
    }

    public String getVehicleID() {
        return vehicleId;
    }

    public String getBranchId() {
        return branchId;
    }

    public float getPrice() {
        return price;
    }
}
