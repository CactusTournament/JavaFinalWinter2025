package services;

import dao.MembershipDAO;
import dao.MembershipPlanDAO;
import java.util.List;
import java.util.logging.Logger;
import models.Membership;
import models.MembershipPlan;
import utils.LoggerUtil;

/**
 * MembershipService
 * Service class for handling membership-related operations.
 * Utilizes MembershipDAO for database interactions.
 * 
 * Author: Abiodun Magret Oyedele
 * Date: 2025-12-09
 */
public class MembershipService {
    /**
     * MembershipDAO instance for database operations.
     * Logger for logging events.
     */
    private MembershipDAO membershipDAO;
    private MembershipPlanDAO membershipPlanDAO = new MembershipPlanDAO();
    private static final Logger logger = LoggerUtil.getLogger();

    /**
     * Constructor to initialize MembershipService with a MembershipDAO instance.
     * 
     * @param membershipDAO The MembershipDAO instance to use.
     */
    public MembershipService(MembershipDAO membershipDAO) {
        this.membershipDAO = membershipDAO;
    }

    /**
     * Returns all available membership plans from the catalog.
     */
    public List<MembershipPlan> getAvailablePlans() {
        try {
            return membershipPlanDAO.getAllPlans();
        } catch (Exception e) {
            logger.severe("Error retrieving membership plans: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Purchases the specified plan for the given member (creates a Membership record).
     * @param planId plan id from the catalog
     * @param memberId user id of purchasing member
     * @return created Membership or null
     */
    public Membership purchasePlan(int planId, int memberId) {
        try {
            MembershipPlan plan = membershipPlanDAO.getPlanById(planId);
            if (plan == null) {
                logger.warning("Requested plan not found: " + planId);
                return null;
            }
            Membership membership = new Membership(0, plan.getPlanType(), plan.getPlanDescription(), plan.getPlanPrice(), memberId);
            boolean ok = membershipDAO.createMembership(membership);
            return ok ? membership : null;
        } catch (Exception e) {
            logger.severe("Error purchasing plan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Add a new membership.
     * 
     * @param membership The Membership object to add.
     * @return The added Membership object if successful; null otherwise.
     */
    public Membership addMembership(Membership membership) {
        try {
            boolean success = membershipDAO.createMembership(membership);
            if (success) {
                logger.info("Membership added successfully: " + membership.getMembershipType() + "Success: " + success);
                return membership;
            } else {
                logger.warning("Failed to add membership: " + membership.getMembershipType());
            }
        } catch (Exception e) {
            logger.severe("Error adding membership: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get a membership by its ID.
     * 
     * @param id The ID of the membership.
     * @return The Membership object if found; null otherwise.
     */
    public Membership getMembership(int id) {
        try {
            Membership membership = membershipDAO.getMembershipById(id);
            if (membership != null) {
                logger.info("Membership retrieved successfully: ID " + id);
            } else {
                logger.warning("Membership not found: ID " + id);
            }
            return membership;
        } catch (Exception e) {
            logger.severe("Error retrieving membership: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get all memberships.
     * 
     * @return List of all Membership objects.
     */
    public List<Membership> getAllMemberships() {
        try {
            List<Membership> memberships = membershipDAO.getAllMemberships();
            logger.info("Retrieved " + memberships.size() + " memberships from the database." + memberships);
            return memberships;
        } catch (Exception e) {
            logger.severe("Error retrieving all memberships: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update an existing membership.
     * 
     * @param membership The Membership object with updated details.
     * @return true if the update was successful; false otherwise.
     */
    public boolean updateMembership(Membership membership) {
        try {
            boolean success = membershipDAO.updateMembership(membership);
            if (success) {
                logger.info("Membership updated successfully: ID " + membership.getMembershipID());
                return true;
            } else {
                logger.warning("Failed to update membership: ID " + membership.getMembershipID());
                return false;
            }
        } catch (Exception e) {
            logger.severe("Error updating membership: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a membership by its ID.
     * 
     * @param id The ID of the membership to delete.
     * @return true if deletion was successful; false otherwise.
     */
    public boolean deleteMembership(int id) {
        try {
            boolean success = membershipDAO.deleteMembership(id);
            if (success) {
                logger.info("Membership deleted successfully: ID " + id);
                return true;
            } else {
                logger.warning("Failed to delete membership: ID " + id);
                return false;
            }
        } catch (Exception e) {
            logger.severe("Error deleting membership: " + e.getMessage());
            return false;
        }
    }

    /**
     * View total revenue from all memberships.
     * @return total revenue
     */
    public double viewTotalRevenue() {
        return membershipDAO.getAllMemberships()
                .stream()
                .mapToDouble(Membership::getMembershipCost)
                .sum();
    }

    /**
     * Calculates total membership expenses for a specific member.
     *
     * @param memberId ID of the member
     * @return total expenses
     */
    public double calculateMemberExpenses(int memberId) {
        try {
            List<Membership> list = membershipDAO.getMembershipsByMemberId(memberId);
            return list.stream().mapToDouble(Membership::getMembershipCost).sum();
        } catch (Exception e) {
            logger.severe("Error calculating member expenses: " + e.getMessage());
            return 0.0;
        }
    }
}