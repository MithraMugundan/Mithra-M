package com.hexaware.ims.service;

import com.hexaware.ims.dao.ClaimDaoImp;
import com.hexaware.ims.dao.IClaimDao;
import com.hexaware.ims.entity.Claim;

import java.util.List;

public class ClaimServiceImp implements IClaimService {

    IClaimDao dao = new ClaimDaoImp();

    public ClaimServiceImp() {
    }

    @Override
    public boolean createClaim(Claim claim) {
        return this.dao.createClaim(claim);
    }

    @Override
    public Claim getClaimById(int claimId) {
        return this.dao.getClaimById(claimId);
    }

    @Override
    public List<Claim> getAllClaims() {
        return this.dao.getAllClaims();
    }

    public static boolean validateData(Claim claim) {
        boolean flag = false;
        if (claim.getClaimId() > 0 && claim.getClaimNumber().length() > 3 && claim.getClaimAmount() > 0.0) {
            flag = true;
        }
        return flag;
    }
}
