package com.carrental.dao;

import com.carrental.enums.VehicleType;
import com.carrental.models.Branch;
import com.carrental.models.VehicleItem;

import java.util.*;

public class BranchDAO {

    private static BranchDAO inst = null;
    private final HashMap<String, Branch> branchInfo;
    private final HashMap<String, VehicleItem> vehicleInfo;
    private final HashMap<String, HashMap<VehicleType, List<String>>> branchCatalog;

    private BranchDAO() {
        this.branchInfo = new HashMap<String, Branch>();
        this.vehicleInfo = new HashMap<String, VehicleItem>();
        this.branchCatalog = new HashMap<String, HashMap<VehicleType, List<String>>>();
    }

    public static BranchDAO getInstance() {
        if (inst == null)
            inst = new BranchDAO();
        return inst;
    }

    /*
    Add vehicle types in a branch
     */
    public synchronized void addVehicleTypeInBranch(Branch branch) {
        for (VehicleType vehicleType : branch.getVehicleTypesInBranch()) {
            List<String> vehicleList = new ArrayList<String>();
            branchCatalog.get(branch.getBranchId()).put(vehicleType, vehicleList);
        }
    }

    /*
     Add a particular branch
     */
    public synchronized boolean addBranch(Branch branch) {
        String branchId = branch.getBranchId();
        branchInfo.put(branchId, branch);
        HashMap<VehicleType, List<String>> vehicleTypeListHashMap = new HashMap<VehicleType, List<String>>();
        branchCatalog.put(branchId, vehicleTypeListHashMap);

        addVehicleTypeInBranch(branch);

        return true;
    }


    /*
    check if that branch is present and return branch object.
    */

    public Branch findBranch(String branchId) {
        if (branchInfo != null & branchInfo.containsKey(branchId))
            return branchInfo.get(branchId);
        return null;
    }
    /*
    Check if that vehicle type is present in that branch.
    */
    public boolean isVehicleTypePresentInBranch(String branchId, VehicleType vehicleType) {
        if (findBranch(branchId) == null) return false;
        return branchInfo.get(branchId).getVehicleTypesInBranch().contains(vehicleType);
    }

    /*
    Add Vehicle to branch sorted by Price
    */

    public synchronized boolean addSortedVehicleToBranch(String branchId, String vehicleId, VehicleType vehicleType) {

        List<String> vehicleList;
        vehicleList = branchCatalog.get(branchId).get(vehicleType);
        int idx = 0;
        for (String currVehicleInList : vehicleList) {
            if (vehicleInfo.get(vehicleId).getPrice() >= vehicleInfo.get(currVehicleInList).getPrice())
                idx++;
        }
        vehicleList.add(idx, vehicleId);
        branchCatalog.get(branchId).put(vehicleType, vehicleList);
        return true;
    }

    /*
    Add vehicle to a branch.
     */
    public synchronized boolean addVehicle(VehicleItem vehicleItem) {
        String branchId = vehicleItem.getBranchId();
        String vehicleId = vehicleItem.getVehicleID();
        VehicleType vehicleType = vehicleItem.getVehicleType();

        if (findBranch(branchId) == null) return false;
        if (vehicleInfo.containsKey(vehicleId)) return false;
        vehicleInfo.put(vehicleId, vehicleItem);

        if (addSortedVehicleToBranch(branchId, vehicleId, vehicleType)) {
            int sizeofVehicleTypeList = branchCatalog.get(branchId).get(vehicleType).size();
            return true;
        }
        return false;
    }

    /*
    Fetch All vehicles of a particular type present in a branch.
     */
    public List<VehicleItem> fetchVehicles(String branchId, VehicleType vehicleType, int batchNo, int batchSize) {
        int startingIdx = (batchNo - 1) * batchSize;
        int allVehicleListSize = branchCatalog.get(branchId).get(vehicleType).size();

        if (startingIdx >= allVehicleListSize) return null;
        List<VehicleItem> vehicleItemList = new ArrayList<>();

        for (int idx = startingIdx; idx < Math.min(allVehicleListSize, startingIdx + batchSize); idx++) {
            String currVehicleId = branchCatalog.get(branchId).get(vehicleType).get(idx);
            vehicleItemList.add(vehicleInfo.get(currVehicleId));
        }

        return vehicleItemList;
    }

    /*
    Get All Available vehicles of a particular types of a branch.
     */
    public List<VehicleItem> getAllAvailVehicleOfBranchAndType(String branchId, VehicleType vehicleType) {
        List<VehicleItem> vehicleList = new ArrayList<VehicleItem>();
        for (String vehicle : branchCatalog.get(branchId).get(vehicleType)) {
            vehicleList.add(vehicleInfo.get(vehicle));
        }
        return vehicleList;
    }

    /*
        Get All Available vehicles of all types of a branch

     */
    public List<VehicleItem> getAllAvailVehicleOfBranch(String branchId) {
        List<VehicleItem> vehicleList = new ArrayList<VehicleItem>();

        for (VehicleType vehicleType : branchInfo.get(branchId).getVehicleTypesInBranch()) {
            vehicleList.addAll(getAllAvailVehicleOfBranchAndType(branchId, vehicleType));
        }
        sortAllAvailableVehicleOfBranch(vehicleList);
        return vehicleList;
    }

    /*
        Sort All Available vehicles of all types of a branch

     */
    public void sortAllAvailableVehicleOfBranch(List<VehicleItem> vehicleList) {
        Collections.sort(vehicleList, new Comparator<VehicleItem>() {
            @Override
            public int compare(VehicleItem v1, VehicleItem v2) {
                return Float.compare(v1.getPrice(), v2.getPrice());
            }
        });
    }

}

