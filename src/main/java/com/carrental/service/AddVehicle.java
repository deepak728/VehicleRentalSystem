package com.carrental.service;

import com.carrental.dao.BranchDAO;
import com.carrental.models.VehicleItem;

public class AddVehicle {
    private final BranchDAO branchDAO;

    public AddVehicle() {
        this.branchDAO = BranchDAO.getInstance();
    }

    /*
    Add given vehicle to the branch.
     */
    public boolean addVehicle(VehicleItem vehicleItem) {
        if (!branchDAO.isVehicleTypePresentInBranch(vehicleItem.getBranchId(), vehicleItem.getVehicleType())) {
            return false;
        }
        return branchDAO.addVehicle(vehicleItem);
    }
}
