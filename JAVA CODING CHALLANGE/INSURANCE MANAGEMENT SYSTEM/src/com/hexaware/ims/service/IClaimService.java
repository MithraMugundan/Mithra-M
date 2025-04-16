package com.hexaware.ims.service;

import java.util.List;

import com.hexaware.ims.entity.Claim;

public interface IClaimService {

	boolean createClaim(Claim claim);

    Claim getClaimById(int claimId);

    List<Claim> getAllClaims();
}
