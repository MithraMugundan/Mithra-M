package com.hexaware.ims.presentation;

import com.hexaware.ims.entity.Policy;
import com.hexaware.ims.entity.Claim;
import com.hexaware.ims.exceptions.PolicyNotFoundException;
import com.hexaware.ims.service.IPolicyService;
import com.hexaware.ims.service.PolicyServiceImp;
import com.hexaware.ims.service.IClaimService;
import com.hexaware.ims.service.ClaimServiceImp;

import java.util.List;
import java.util.Scanner;

public class ClientUI {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        IPolicyService policyService = new PolicyServiceImp();
        IClaimService claimService = new ClaimServiceImp();

        boolean flag = true;

        while (flag) {
            System.out.println("\n--- INSURANCE MANAGEMENT SYSTEM ---");
            System.out.println("1. POLICY DETAILS");
            System.out.println("2. CLAIM DETAILS");
            System.out.println("0. EXIT");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 0:
                    flag = false;
                    System.out.println("Thank you, Visit Again..");
                    break;
                case 1:
                    policyMenu(policyService);
                    break;
                case 2:
                    claimMenu(claimService);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // ---------------- POLICY MENU -------------------
    public static void policyMenu(IPolicyService service) {
        boolean subFlag = true;
        while (subFlag) {
            System.out.println("\n--- POLICY DETAILS ---");
            System.out.println("1. ADD POLICY");
            System.out.println("2. UPDATE POLICY");
            System.out.println("3. DELETE POLICY");
            System.out.println("4. DISPLAY ALL POLICIES");
            System.out.println("5. SEARCH POLICY BY ID");
            System.out.println("0. BACK TO MAIN MENU");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 0:
                    subFlag = false;
                    break;
                case 1:
                    Policy p1 = readPolicyData();
                    boolean isValid = PolicyServiceImp.validateData(p1);
                    if (isValid) {
                        boolean inserted = service.createPolicy(p1);
                        System.out.println(inserted ? "Policy inserted successfully." : "Insertion failed.");
                    } else {
                        System.err.println("Invalid Input Data");
                    }
                    break;
                case 2:
                    System.out.print("Enter Policy ID to update: ");
                    int updateId = sc.nextInt();
                    Policy updated = readPolicyData();
                    updated.setPolicyId(updateId);
                    boolean updatedRes = service.updatePolicy(updated);
                    System.out.println(updatedRes ? "Policy updated successfully." : "Update failed.");
                    break;
                case 3:
                    System.out.print("Enter Policy ID to delete: ");
                    int deleteId = sc.nextInt();
                    boolean deleted = service.deletePolicy(deleteId);
                    System.out.println(deleted ? "Policy deleted successfully." : "Delete failed.");
                    break;
                case 4:
                    List<Policy> policies = service.getAllPolicies();
                    if (policies.isEmpty()) {
                        System.out.println("No policies found.");
                    } else {
                        policies.forEach(System.out::println);
                    }
                    break;
                case 5:
                    System.out.print("Enter Policy ID to search: ");
                    int searchId = sc.nextInt();
                    try {
                        Policy p = service.getPolicy(searchId);
                        System.out.println(p);
                    } catch (PolicyNotFoundException e) {
                        System.err.println("Policy not found with ID: " + searchId);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static Policy readPolicyData() {
        System.out.print("Enter Policy ID: ");
        int policyId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Policy Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Premium Amount: ");
        double premium = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Policy Type: ");
        String type = sc.nextLine();

        Policy p = new Policy();
        p.setPolicyId(policyId);
        p.setPolicyName(name);
        p.setPremium(premium);
        p.setPolicyType(type);
        return p;
    }

    // ---------------- CLAIM MENU -------------------
    public static void claimMenu(IClaimService claimService) {
        boolean subFlag = true;
        while (subFlag) {
            System.out.println("\n--- CLAIM DETAILS ---");
            System.out.println("1. CREATE CLAIM");
            System.out.println("2. GET CLAIM BY ID");
            System.out.println("3. DISPLAY ALL CLAIMS");
            System.out.println("0. BACK TO MAIN MENU");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 0:
                    subFlag = false;
                    break;
                case 1:
                    System.out.println("Enter Claim Details:");
                    Claim newClaim = new Claim();
                    System.out.print("Enter Claim ID: ");
                    newClaim.setClaimId(sc.nextInt());
                    sc.nextLine();
                    System.out.print("Enter Claim Number: ");
                    newClaim.setClaimNumber(sc.nextLine());
                    System.out.print("Enter Claim Amount: ");
                    newClaim.setClaimAmount(sc.nextDouble());
                    sc.nextLine();
                    System.out.print("Enter Status: ");
                    newClaim.setStatus(sc.nextLine());
                    // Setting policy and client as null or dummy as per current implementation
                    newClaim.setPolicy(null);
                    newClaim.setClient(null);

                    boolean success = claimService.createClaim(newClaim);
                    System.out.println(success ? "Claim created successfully." : "Claim creation failed.");
                    break;
                case 2:
                    System.out.print("Enter Claim ID: ");
                    int cid = sc.nextInt();
                    Claim claim = claimService.getClaimById(cid);
                    if (claim != null) {
                        System.out.println(claim);
                    } else {
                        System.out.println("Claim not found.");
                    }
                    break;
                case 3:
                    List<Claim> claims = claimService.getAllClaims();
                    if (claims.isEmpty()) {
                        System.out.println("No claims found.");
                    } else {
                        claims.forEach(System.out::println);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
