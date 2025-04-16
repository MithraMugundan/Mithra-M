
package com.hexaware.ims.dao;

import com.hexaware.ims.entity.Claim;
import java.util.List;

public interface IClaimDao {

    boolean createClaim(Claim claim);

    Claim getClaimById(int claimId);

    List<Claim> getAllClaims();
}
