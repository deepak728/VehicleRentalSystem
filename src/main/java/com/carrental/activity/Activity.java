package com.carrental.activity;

import com.carrental.enums.VehicleType;
import com.carrental.helpers.PricingHelper;
import com.carrental.models.Branch;
import com.carrental.models.Display;
import com.carrental.models.Reservation;
import com.carrental.models.VehicleItem;
import com.carrental.service.*;

import java.util.Collections;
import java.util.List;

public class Activity {
    private final AddBranch addBranch;
    private final AddVehicle addVehicle;
    private final BookVehicle bookVehicle;
    private final DisplayVehicle displayVehicle;
    private final ReturnVehicle returnVehicle;
    private final PricingHelper pricing;


    public Activity() {
        this.addBranch = new AddBranch();
        this.addVehicle = new AddVehicle();
        this.bookVehicle = new BookVehicle();
        this.displayVehicle = new DisplayVehicle();
        this.returnVehicle = new ReturnVehicle();
        this.pricing = new PricingHelper();
    }

    public boolean handleAddBranch(String branchId, List<VehicleType> vehicleTypes) {
        Branch newBranch = new Branch(branchId, vehicleTypes);
        if (!addBranch.addBranch(newBranch)) {
          //  System.out.println("Branch Cannot be added ");
            return false;
        }
       // System.out.println("Branch Added " + branchId);
        return true;
    }

    public boolean handleAddVehicle(String branchId, VehicleType vehicleType, String vehicleId, float price) {
        VehicleItem newVehicle = new VehicleItem(vehicleType, "", vehicleId, branchId, price);
        if (!addVehicle.addVehicle(newVehicle)) {
            //System.out.println("Vehicle cannot be added to branch" + branchId);
            return false;
        }
      //  System.out.println(vehicleId + " Successfully added to " + branchId);
        return true;
    }

    public float handleBookVehicle(String branchId, VehicleType vehicleType, int startTime, int endTime) {
        if (startTime >= endTime) {
            return -1;
        }
        Reservation reservation = new Reservation(branchId, vehicleType, startTime, endTime);
        float reservationPrice = bookVehicle.bookVehicle(reservation);
        if (reservationPrice == -1) {
            //System.out.println("Unable to book any " + vehicleType + " from branch " + branchId);
        } else {
            //System.out.println("Successfully booked a " + vehicleType + " for duration(" +
            //        startTime + " - " + endTime + ")from " + branchId);
        }
        return reservationPrice;
    }

    public List<String> handleDisplayVehicle(String branchId, int startTime, int endTime) {
        if (startTime >= endTime) {
            return Collections.emptyList();
        }
        List<String> availableVehicleList;
        Display display = new Display(branchId, startTime, endTime);
        availableVehicleList = displayVehicle.getAllAvailVehicleOfBranch(display);
        if (availableVehicleList == null) {
           // System.out.println("No vehicle to show");
            return Collections.emptyList();
        }
        return availableVehicleList;
    }

}
