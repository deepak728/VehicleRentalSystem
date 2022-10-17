package com.carrental.models;

import com.carrental.enums.VehicleType;

public class Vehicle {
    private final VehicleType vehicleType;
    private final String vehicleNoPlate;
    private final String model;

    public Vehicle(VehicleType vehicleType, String vehicleNoPlate) {
        this.vehicleType = vehicleType;
        this.vehicleNoPlate = vehicleNoPlate;

        model = null;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNoPlate() {
        return vehicleNoPlate;
    }

    public String getModel() {
        return model;
    }

}
