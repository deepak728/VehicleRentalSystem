package com.carrental.service;

import com.carrental.dao.BranchDAO;
import com.carrental.models.Branch;

public class AddBranch {
    private final BranchDAO branchDAO;

    public AddBranch() {
        this.branchDAO = BranchDAO.getInstance();
    }

    /*
    Add given branch
     */
    public boolean addBranch(Branch branch) {
        if (branch.getBranchId().isEmpty())
            return false;

        if (checkBranch(branch.getBranchId()))
            return false;
        return branchDAO.addBranch(branch);
    }

    // Check if that branch exists or not
    public boolean checkBranch(String branchId) {
        return branchDAO.findBranch(branchId) != null;
    }
}
