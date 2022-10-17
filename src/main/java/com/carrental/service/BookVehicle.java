package com.carrental.service;

import com.carrental.dao.BranchDAO;
import com.carrental.dao.ReservationDAO;
import com.carrental.enums.VehicleType;
import com.carrental.models.Reservation;
import com.carrental.models.VehicleItem;

import java.util.List;

public class BookVehicle {
    private final BranchDAO branchDAO;
    private final ReservationDAO reservationDAO;
    private final int batchSize = 2;

    public BookVehicle() {
        this.branchDAO = BranchDAO.getInstance();
        this.reservationDAO = ReservationDAO.getInstance();
    }

    public float bookVehicle(Reservation reservation) {
        if (!branchDAO.isVehicleTypePresentInBranch(reservation.getBranchId(), reservation.getVehicleType()))
            return -1;

        VehicleItem vehicleItem = getAvailVehicle(reservation);
        if (vehicleItem == null) return -1;

        if (reservationDAO.addReservation(vehicleItem.getVehicleID(), reservation.getBookingDuration())) {
            int hoursOfRenting = (reservation.getBookingDuration().getEndTime() - reservation.getBookingDuration().getStartTime());
            float totalPrice = vehicleItem.getPrice() * (hoursOfRenting);
            /*

            Uncomment code to apply dynamic pricing .
            if (isDynamicPricingApplicable(reservation))
                totalPrice *= (1.1);
            */
            return totalPrice;
        }
        return -1;
    }

    /*
    Check if dynamic price is applicable or not
     */
    public boolean isDynamicPricingApplicable(Reservation reservation) {
        String branchId = reservation.getBranchId();
        VehicleType vehicleType = reservation.getVehicleType();
        List<VehicleItem> vehicleItemList;
        int batchNo = 1, noOfAvailVehicle = 0, noOfPresentVehicle = 0;

        while (branchDAO.fetchVehicles(branchId, vehicleType, batchNo, batchSize) != null) {
            vehicleItemList = branchDAO.fetchVehicles(branchId, vehicleType, batchNo, batchSize);
            noOfPresentVehicle += vehicleItemList.size();

            for (VehicleItem vehicleItem : vehicleItemList) {
                if (reservationDAO.checkAvailability(vehicleItem.getVehicleID(), reservation.getBookingDuration()))
                    noOfAvailVehicle++;
            }
            batchNo++;
        }
        if (noOfAvailVehicle == 0) return false;
        return noOfAvailVehicle / noOfPresentVehicle <= 0.2;
    }

    /*
    Get the cheapest available vehicle for given duration.
     */
    public VehicleItem getAvailVehicle(Reservation reservation) {
        String branchId = reservation.getBranchId();
        VehicleType vehicleType = reservation.getVehicleType();
        int batchNo = 1;
        List<VehicleItem> vehicleItemList;

        while (branchDAO.fetchVehicles(branchId, vehicleType, batchNo, batchSize) != null) {
            vehicleItemList = branchDAO.fetchVehicles(branchId, vehicleType, batchNo, batchSize);

            for (VehicleItem vehicleItem : vehicleItemList) {
                if (reservationDAO.checkAvailability(vehicleItem.getVehicleID(), reservation.getBookingDuration()))
                    return vehicleItem;
            }
            batchNo++;
        }
        return null;
    }


}
