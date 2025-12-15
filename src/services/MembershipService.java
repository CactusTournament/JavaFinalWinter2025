package services;

import dao.MembershipDAO;
import models.Membership;
import java.util.List;
import utils.LoggerUtil;
import java.util.logging.Logger;

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
     */
    public void updateMembership(Membership membership) {
        try {
            boolean success = membershipDAO.updateMembership(membership);
            if (success) {
                logger.info("Membership updated successfully: ID " + membership.getMembershipID());
            } else {
                logger.warning("Failed to update membership: ID " + membership.getMembershipID());
            }
        } catch (Exception e) {
            logger.severe("Error updating membership: " + e.getMessage());
        }
    }

    /**
     * Delete a membership by its ID.
     * 
     * @param id The ID of the membership to delete.
     */
    public void deleteMembership(int id) {
        try {
            boolean success = membershipDAO.deleteMembership(id);
            if (success) {
                logger.info("Membership deleted successfully: ID " + id);
            } else {
                logger.warning("Failed to delete membership: ID " + id);
            }
        } catch (Exception e) {
            logger.severe("Error deleting membership: " + e.getMessage());
        }
    }
}