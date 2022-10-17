package com.activity.carrental.activity;

import com.carrental.activity.Activity;
import com.carrental.enums.VehicleType;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private static final List<String> BRANCH_IDs = Arrays.asList("1231", "1234", "1232", "1235");
    private static final String INVALID_BRANCH_ID = "INVALID_BRANCH";
    private static final List<VehicleType> VEHICLE_TYPES = Arrays.asList(VehicleType.BIKE, VehicleType.CAR);
    private static final VehicleType VEHICLE_TYPE_CAR = VehicleType.CAR;
    private static final VehicleType VEHICLE_TYPE_BIKE = VehicleType.BIKE;
    private static final List<String> VEHICLE_IDs = Arrays.asList("vehicleId", "vehicleId2", "vehicleId3", "vehicleId4");
    private static final String VEHICLE_ID_2 = "vehicleId5";
    private static final float PRICE = 100;
    private static final float PRICE_2 = 200;
    private static final int START_TIME = 12;
    private static final int END_TIME = 14;

    private static final int START_TIME_2 = 15;
    private static final int END_TIME_2 = 18;

    private static final int DISPLAY_START_TIME = 12;
    private static final int DISPLAY_END_TIME = 16;

    @org.junit.jupiter.api.Test
    void handleAddBranch() {
        final Activity activity = new Activity();
        boolean branchAddedFlag = activity.handleAddBranch(BRANCH_IDs.get(0), VEHICLE_TYPES);
        assertTrue(branchAddedFlag);
    }

    @org.junit.jupiter.api.Test
    void handleAddVehicle_BranchNotFound() {
        final Activity activity = new Activity();
        boolean addVehicleFlag = activity.handleAddVehicle(INVALID_BRANCH_ID, VEHICLE_TYPE_CAR, VEHICLE_IDs.get(0), PRICE);
        assertFalse(addVehicleFlag);
    }

    @org.junit.jupiter.api.Test
    void handleAddVehicle_HappyCase() {
        final Activity activity = new Activity();
        boolean branchAddedFlag = activity.handleAddBranch(BRANCH_IDs.get(1), VEHICLE_TYPES);
        assertTrue(branchAddedFlag);
        boolean addVehicleFlag = activity.handleAddVehicle(BRANCH_IDs.get(1), VEHICLE_TYPE_CAR, VEHICLE_IDs.get(1), PRICE);
        assertTrue(addVehicleFlag);
    }

    @org.junit.jupiter.api.Test
    void handleBookVehicle() {
        final Activity activity = new Activity();
        boolean branchAddedFlag = activity.handleAddBranch(BRANCH_IDs.get(2), VEHICLE_TYPES);
        assertTrue(branchAddedFlag);
        boolean addVehicleFlag = activity.handleAddVehicle(BRANCH_IDs.get(2), VEHICLE_TYPE_CAR, VEHICLE_IDs.get(2), PRICE);
        assertTrue(addVehicleFlag);
        float price = activity.handleBookVehicle(BRANCH_IDs.get(2), VEHICLE_TYPE_CAR, START_TIME, END_TIME);
        assertEquals(price, 200);
    }

    @org.junit.jupiter.api.Test
    void handleDisplayVehicle() {
        final Activity activity = new Activity();
        // Add branch
        boolean branchAddedFlag = activity.handleAddBranch(BRANCH_IDs.get(3), VEHICLE_TYPES);
        assertTrue(branchAddedFlag);
        // Add vehicle 1
        boolean addVehicleFlag = activity.handleAddVehicle(BRANCH_IDs.get(3), VEHICLE_TYPE_CAR, VEHICLE_IDs.get(3), PRICE);
        assertTrue(addVehicleFlag);
        // Add vehicle 2
        boolean addVehicleFlag2 = activity.handleAddVehicle(BRANCH_IDs.get(3), VEHICLE_TYPE_BIKE, VEHICLE_ID_2, PRICE_2);
        assertTrue(addVehicleFlag2);

        // Book vehicle 1
        float price1 = activity.handleBookVehicle(BRANCH_IDs.get(3), VEHICLE_TYPE_CAR, START_TIME, END_TIME);
        assertEquals(price1, PRICE * (END_TIME - START_TIME));
        // Get available vehicles from 12 to 16
        List<String> availableVehicles = activity.handleDisplayVehicle(BRANCH_IDs.get(3), DISPLAY_START_TIME, DISPLAY_END_TIME);
        assertEquals(availableVehicles.size(), 1);
        assertEquals(availableVehicles.get(0), VEHICLE_ID_2);

        // Book vehicle 2
        float price2 = activity.handleBookVehicle(BRANCH_IDs.get(3), VEHICLE_TYPE_BIKE, START_TIME_2, END_TIME_2);
        assertEquals(price2, PRICE_2 * (END_TIME_2 - START_TIME_2));
        // Get available vehicles from 12 to 16
        List<String> availableVehicles2 = activity.handleDisplayVehicle(BRANCH_IDs.get(3), DISPLAY_START_TIME,
                DISPLAY_END_TIME);
        assertEquals(availableVehicles2.size(), 0);
    }
}