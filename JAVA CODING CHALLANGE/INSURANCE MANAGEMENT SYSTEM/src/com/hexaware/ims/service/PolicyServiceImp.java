package com.hexaware.ims.service;

import com.hexaware.ims.dao.IPolicyDao;
import com.hexaware.ims.dao.PolicyDaoImp;
import com.hexaware.ims.entity.Policy;
import com.hexaware.ims.exceptions.PolicyNotFoundException;

import java.util.List;

public class PolicyServiceImp implements IPolicyService {

    IPolicyDao dao = new PolicyDaoImp();  // Use the PolicyDaoImpl to interact with the database

    public PolicyServiceImp() {
    }

    @Override
    public boolean createPolicy(Policy policy) {
        return this.dao.createPolicy(policy);
    }

    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        return this.dao.getPolicy(policyId);
    }

    @Override
    public List<Policy> getAllPolicies() {
        return this.dao.getAllPolicies();
    }

    @Override
    public boolean updatePolicy(Policy policy) {
        return this.dao.updatePolicy(policy);
    }

    @Override
    public boolean deletePolicy(int policyId) {
        return this.dao.deletePolicy(policyId);
    }

    // Optional: You can add a validation method like in EmployeeServiceImp to validate data
    public static boolean validateData(Policy policy) {
        boolean flag = false;
        if (policy.getPolicyId() > 0 && policy.getPolicyName().length() > 3 && policy.getPremium() > 0.0) {
            flag = true;
        }

        return flag;
    }
}
