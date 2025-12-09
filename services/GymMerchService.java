package JavaFinalWinter2025.services;

import JavaFinalWinter2025.dao.GymMerchDAO;
import JavaFinalWinter2025.src.GymMerch;
import java.util.List;

/**
 * GymMerchService
 * Service layer for managing GymMerch operations.
 * Provides methods to add, retrieve, update, and delete gym merchandise.
 */
public class GymMerchService {
    /**
     * DAO for GymMerch operations.
     */
    private GymMerchDAO gymMerchDAO;

    /**
     * Constructor to initialize the service with a GymMerchDAO.
     *
     * @param gymMerchDAO DAO for GymMerch operations.
     */
    public GymMerchService(GymMerchDAO gymMerchDAO) {
        this.gymMerchDAO = gymMerchDAO;
    }

    /**
     * Adds a new GymMerch.
     * throws Exception if a database access error occurs.
     *
     * @param merch GymMerch object to add.
     */
    public void addMerch(GymMerch merch) {
        try{ 
            gymMerchDAO.addGymMerch(merch);
        } catch (Exception e) {
            System.err.println("Error adding gym merchandise: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a GymMerch by its ID.
     *
     * @param id ID of the GymMerch to retrieve.
     * @return GymMerch object if found, null otherwise.
     */
    public GymMerch getMerch(int id) {
        try {
            return gymMerchDAO.getGymMerchById(id);
        } catch (Exception e) {
            System.err.println("Error retrieving gym merchandise: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all GymMerch records.
     *
     * @return List of all GymMerch objects.
     */
    public List<GymMerch> getAllMerch() {
        try {
            return gymMerchDAO.getAllGymMerch();
        } catch (Exception e) {
            System.err.println("Error retrieving all gym merchandise: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates an existing GymMerch.
     *
     * @param merch GymMerch object with updated information.
     */
    public void updateMerch(GymMerch merch) {
        try {
            gymMerchDAO.updateGymMerch(merch);
        } catch (Exception e) {
            System.err.println("Error updating gym merchandise: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a GymMerch by its ID.
     *
     * @param id ID of the GymMerch to delete.
     */
    public void deleteMerch(int id) {
        try {
            gymMerchDAO.deleteGymMerch(id);
        } catch (Exception e) {
            System.err.println("Error deleting gym merchandise: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

