package com.hexaware.ims.dao;

import com.hexaware.ims.entity.Policy;
import com.hexaware.ims.exceptions.PolicyNotFoundException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PolicyDaoImp implements IPolicyDao {

    public PolicyDaoImp() {
    }

    @Override
    public boolean createPolicy(Policy policy) {
        boolean status = false;

        try {
            Connection conn = DBUtil.getDBConnection();
            String insert = "INSERT INTO policy (policyId, policyName, policyType, premiumAmount) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insert);
            pstmt.setInt(1, policy.getPolicyId());
            pstmt.setString(2, policy.getPolicyName());
            pstmt.setString(3, policy.getPolicyType());
            pstmt.setDouble(4, policy.getPremium());

            int rows = pstmt.executeUpdate();
            status = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        Policy policy = null;

        try {
            Connection conn = DBUtil.getDBConnection();
            String select = "SELECT * FROM policy WHERE policyId = ?";
            PreparedStatement pstmt = conn.prepareStatement(select);
            pstmt.setInt(1, policyId);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }

            String name = rs.getString("policyName");
            String type = rs.getString("policyType");
            double premium = rs.getDouble("premiumAmount");

            policy = new Policy(policyId, name, type, premium);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return policy;
    }

    @Override
    public List<Policy> getAllPolicies() {
        List<Policy> policies = new ArrayList<>();

        try {
            Connection conn = DBUtil.getDBConnection();
            String select = "SELECT * FROM policy";
            PreparedStatement pstmt = conn.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("policyId");
                String name = rs.getString("policyName");
                String type = rs.getString("policyType");
                double premium = rs.getDouble("premiumAmount");

                policies.add(new Policy(id, name, type, premium));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return policies;
    }

    @Override
    public boolean updatePolicy(Policy policy) {
        boolean status = false;

        try {
            Connection conn = DBUtil.getDBConnection();
            String update = "UPDATE policy SET policyName = ?, policyType = ?, premiumAmount = ? WHERE policyId = ?";
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, policy.getPolicyName());
            pstmt.setString(2, policy.getPolicyType());
            pstmt.setDouble(3, policy.getPremium());
            pstmt.setInt(4, policy.getPolicyId());

            int rows = pstmt.executeUpdate();
            status = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean deletePolicy(int policyId) {
        boolean status = false;

        try {
            Connection conn = DBUtil.getDBConnection();
            String delete = "DELETE FROM policy WHERE policyId = ?";
            PreparedStatement pstmt = conn.prepareStatement(delete);
            pstmt.setInt(1, policyId);

            int rows = pstmt.executeUpdate();
            status = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }
}

