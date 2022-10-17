package com.carrental.driver;

import com.carrental.activity.Activity;
import com.carrental.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private final Activity activity;

    public Driver() {
        this.activity = new Activity();
    }


    public void StartProgram(String command) {

        String[] split = command.split("\\s+");
        int sizeOfInput = split.length;

        if (split[0].equals("ADD_BRANCH")) {
            String branchId = split[1];
            List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
            String[] vehicleTypeInput = split[2].split(",");

            for (int i = 0; i < vehicleTypeInput.length; i++) {
                VehicleType newVehicleType = VehicleType.valueOf(vehicleTypeInput[i]);
                vehicleTypes.add(newVehicleType);
            }
            if(activity.handleAddBranch(branchId, vehicleTypes))
                System.out.println("TRUE");
            else
                System.out.println("FALSE");
        }

        if (split[0].equals("ADD_VEHICLE")) {
            String branchId = split[1];
            VehicleType vehicleType = VehicleType.valueOf(split[2]);
            String vehicleId = split[3];
            float price = Float.parseFloat(split[4]);
            if(activity.handleAddVehicle(branchId, vehicleType, vehicleId, price))
                System.out.println("TRUE");
            else
                System.out.println("FALSE");
        }

        if (split[0].equals("BOOK")) {
            String branchId = split[1];
            VehicleType vehicleType = VehicleType.valueOf(split[2]);
            int startTime = Integer.parseInt(split[3]);
            int endTime = Integer.parseInt(split[4]);
            int totalPrice = (int) activity.handleBookVehicle(branchId, vehicleType, startTime, endTime);
            System.out.println(totalPrice);
        }

        if (split[0].equals("DISPLAY_VEHICLES")) {
            String branchId = split[1];
            int startTime = Integer.parseInt(split[2]);
            int endTime = Integer.parseInt(split[3]);
            List<String> vehicleList = activity.handleDisplayVehicle(branchId, startTime, endTime);
            for (int i = 0; i < vehicleList.size(); i++) {
                if (i == vehicleList.size() - 1)
                    System.out.print(vehicleList.get(i) + "\n");
                else
                    System.out.print(vehicleList.get(i) + ",");
            }
        }

    }
}
