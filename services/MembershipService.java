package JavaFinalWinter2025.services;

import JavaFinalWinter2025.dao.MembershipDAO;
import JavaFinalWinter2025.src.Membership;
import java.util.List;

/**
 * MembershipService
 * Service class for handling membership-related operations.
 * Utilizes MembershipDAO for database interactions.
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-09
 */
public class MembershipService {
    /**
     * MembershipDAO instance for database operations.
     */
    private MembershipDAO membershipDAO;

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
     */
    public void addMembership(Membership membership) {
        membershipDAO.createMembership(membership);
    }

    /**
     * Get a membership by its ID.
     * 
     * @param id The ID of the membership.
     * @return The Membership object if found; null otherwise.
     */
    public Membership getMembership(int id) {
        return membershipDAO.getMembershipById(id);
    }

    /**
     * Get all memberships.
     * 
     * @return List of all Membership objects.
     */
    public List<Membership> getAllMemberships() {
        return membershipDAO.getAllMemberships();
    }

    /**
     * Update an existing membership.
     * 
     * @param membership The Membership object with updated details.
     */
    public void updateMembership(Membership membership) {
        membershipDAO.updateMembership(membership);
    }

    /**
     * Delete a membership by its ID.
     * 
     * @param id The ID of the membership to delete.
     */
    public void deleteMembership(int id) {
        membershipDAO.deleteMembership(id);
    }
}

