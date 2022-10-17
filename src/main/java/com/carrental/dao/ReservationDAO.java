package com.carrental.dao;

import com.carrental.models.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReservationDAO {
    private static ReservationDAO inst = null;
    private final HashMap<String, List<Duration>> reservations;

    private ReservationDAO() {
        this.reservations = new HashMap<String, List<Duration>>();
    }

    public static ReservationDAO getInstance() {
        if (inst == null)
            inst = new ReservationDAO();
        return inst;
    }

    /*
    Check if given slot is free for a vehicle.
     */
    public boolean isRequestedSlotFree(List<Duration> allBookedDuration, Duration requestedDuration) {

        for (Duration bookedSlot : allBookedDuration) {
            int requestedStartTime = requestedDuration.getStartTime();
            int requestedEndTime = requestedDuration.getEndTime();
            int currBookingStartTime = bookedSlot.getStartTime();
            int currBookingEndTime = bookedSlot.getEndTime();

            if (requestedStartTime >= currBookingStartTime & requestedStartTime < currBookingEndTime)
                return false;
            if (requestedEndTime > currBookingStartTime & requestedEndTime <= currBookingEndTime)
                return false;
            if (requestedStartTime >= currBookingStartTime & requestedEndTime <= currBookingEndTime)
                return false;
            if (requestedStartTime <= currBookingStartTime & requestedEndTime >= currBookingEndTime)
                return false;
        }
        return true;
    }

    /*
    check if given vehicle is available for booking.
     */
    public boolean checkAvailability(String vehicleId, Duration requestedDuration) {
        if (!reservations.containsKey(vehicleId)) return true;
        List<Duration> allBookedDuration = reservations.get(vehicleId);

        return isRequestedSlotFree(allBookedDuration, requestedDuration);
    }

    /*
    Reserve vehicle for given duration
     */
    public synchronized boolean addReservation(String vehicleId, Duration duration) {

        if (!checkAvailability(vehicleId, duration)) return false;
        List<Duration> allReservationsOfVehicle;
        if (!reservations.containsKey(vehicleId)) {
            allReservationsOfVehicle = new ArrayList<Duration>();
            reservations.put(vehicleId, allReservationsOfVehicle);
        }
        allReservationsOfVehicle = reservations.get(vehicleId);
        allReservationsOfVehicle.add(duration);
        reservations.put(vehicleId, allReservationsOfVehicle);

        int size = reservations.get(vehicleId).size();
        return true;
    }
}
