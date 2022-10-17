package com.carrental.service;

import com.carrental.dao.BranchDAO;
import com.carrental.dao.ReservationDAO;
import com.carrental.models.Branch;
import com.carrental.models.Display;
import com.carrental.models.VehicleItem;

import java.util.ArrayList;
import java.util.List;

public class DisplayVehicle {
    private final BranchDAO branchDAO;
    private final ReservationDAO reservationDAO;

    public DisplayVehicle() {
        this.branchDAO = BranchDAO.getInstance();
        this.reservationDAO = ReservationDAO.getInstance();
    }

    /*
    Get all available vehicle of a given branch sorted by price.
     */
    public List<String> getAllAvailVehicleOfBranch(Display displayDuration) {
        Branch branch = branchDAO.findBranch(displayDuration.getBranchId());
        if (branch == null) {
            return null;
        }
        List<String> retVehicleList = new ArrayList<String>();
        List<VehicleItem> allVehicleListOfBranch;

        allVehicleListOfBranch = branchDAO.getAllAvailVehicleOfBranch(branch.getBranchId());

        for (VehicleItem vehicleItem : allVehicleListOfBranch) {
            if (reservationDAO.checkAvailability(vehicleItem.getVehicleID(), displayDuration.getDuration()))
                retVehicleList.add(vehicleItem.getVehicleID());
        }
        return retVehicleList;
    }
}
