package com.carrental.models;

import com.carrental.enums.VehicleType;

import java.util.List;

public class Branch {
    private final String branchId;
    List<VehicleType> vehicleTypesInBranch;

    public Branch(String branchId, List<VehicleType> vehicleTypesInBranch) {
        this.branchId = branchId;
        this.vehicleTypesInBranch = vehicleTypesInBranch;
    }

    public String getBranchId() {
        return branchId;
    }

    public List<VehicleType> getVehicleTypesInBranch() {
        return vehicleTypesInBranch;
    }
}
