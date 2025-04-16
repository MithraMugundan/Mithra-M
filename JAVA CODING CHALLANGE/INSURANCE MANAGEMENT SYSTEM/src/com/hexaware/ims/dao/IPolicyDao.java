package com.hexaware.ims.dao;

import java.util.List;

import com.hexaware.ims.entity.Policy;
import com.hexaware.ims.exceptions.PolicyNotFoundException;


public interface IPolicyDao {

    boolean createPolicy(Policy policy);

    Policy getPolicy(int policyId) throws PolicyNotFoundException;

    List<Policy> getAllPolicies();

    boolean updatePolicy(Policy policy);

    boolean deletePolicy(int policyId);
}
