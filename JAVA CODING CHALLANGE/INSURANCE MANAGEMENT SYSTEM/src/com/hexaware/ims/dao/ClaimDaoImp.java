package com.hexaware.ims.dao;

import com.hexaware.ims.entity.Claim;
import com.hexaware.ims.entity.Policy;
import com.hexaware.ims.entity.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDaoImp implements IClaimDao {

	@Override
	public boolean createClaim(Claim claim) {
	    boolean isInserted = false;

	    try {
	        Connection conn = DBUtil.getDBConnection();
	        String insert = "INSERT INTO claim (claimId, claimNumber, claimAmount, status, clientId) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(insert);
	        pstmt.setInt(1, claim.getClaimId());
	        pstmt.setString(2, claim.getClaimNumber());
	        pstmt.setDouble(3, claim.getClaimAmount());
	        pstmt.setString(4, claim.getStatus());
	        pstmt.setInt(5, claim.getClient().getClientId());

	        int count = pstmt.executeUpdate();
	        if (count > 0) {
	            isInserted = true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return isInserted;
	}


    @Override
    public Claim getClaimById(int claimId) {
        Claim claim = null;

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT * FROM claim WHERE claimId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, claimId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("claimId");
                String number = rs.getString("claimNumber");
                Date filedDate = rs.getDate("dateFiled");
                double amount = rs.getDouble("claimAmount");
                String status = rs.getString("status");

                int policyId = rs.getInt("policyId");
                int clientId = rs.getInt("clientId");

                Policy policy = new Policy();
                policy.setPolicyId(policyId);

                Client client = new Client();
                client.setClientId(clientId);

                claim = new Claim();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claim;
    }

    @Override
    public List<Claim> getAllClaims() {
        List<Claim> list = new ArrayList<>();

        try {
            Connection conn = DBUtil.getDBConnection();
            String query = "SELECT * FROM claim";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("claimId");
                String number = rs.getString("claimNumber");
                Date filedDate = rs.getDate("dateFiled");
                double amount = rs.getDouble("claimAmount");
                String status = rs.getString("status");

                int policyId = rs.getInt("policyId");
                int clientId = rs.getInt("clientId");

                Policy policy = new Policy();
                policy.setPolicyId(policyId);

                Client client = new Client();
                client.setClientId(clientId);

                Claim claim = new Claim();
                list.add(claim);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
