package services;

import dao.GymMerchDAO;
import models.GymMerch;
import java.util.List;
import utils.LoggerUtil;
import java.util.logging.Logger;

/**
 * GymMerchService
 * Service layer for managing GymMerch operations.
 * Provides methods to add, retrieve, update, and delete gym merchandise.
 */
public class GymMerchService {
    /**
     * DAO for GymMerch operations.
     * Logger for logging events.
     */
    private GymMerchDAO gymMerchDAO;
    private static final Logger logger = LoggerUtil.getLogger();

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
     * Logs the addition process.
     *
     * @param merch GymMerch object to add.
     */
    public void addMerch(GymMerch merch) {
        try{ 
            gymMerchDAO.addGymMerch(merch);

            logger.info("Gym merchandise added successfully: " + merch.getMerchName());
        } catch (Exception e) {
            System.err.println("Error adding gym merchandise: " + e.getMessage());
            e.printStackTrace();
            logger.severe("Logging failed during adding gym merchandise: " + e.getMessage());
        }
    }

    /**
     * Retrieves a GymMerch by its ID.
     * Logs the retrieval process.
     *
     * @param id ID of the GymMerch to retrieve.
     * @return GymMerch object if found, null otherwise.
     */
    public GymMerch getMerch(int id) {
        try {
            GymMerch merch = gymMerchDAO.getGymMerchById(id);
            if (merch != null) {
                logger.info("Gym merchandise retrieved successfully: " + merch.getMerchName());
                return merch;
            } else {
                logger.warning("Gym merchandise with ID " + id + " not found.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving gym merchandise: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all GymMerch records.
     * Logs the retrieval process.
     *
     * @return List of all GymMerch objects.
     */
    public List<GymMerch> getAllMerch() {
        try {
            List<GymMerch> merchList = gymMerchDAO.getAllGymMerch();
            logger.info("Retrieved " + merchList.size() + " gym merchandise items from the database.");

            return merchList;
        } catch (Exception e) {
            System.err.println("Error retrieving all gym merchandise: " + e.getMessage());
            e.printStackTrace();
            logger.severe("Logging failed during retrieving all gym merchandise: " + e.getMessage());
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
            boolean updated = gymMerchDAO.updateGymMerch(merch);
            if (updated) {
                logger.info("Gym merchandise updated successfully: " + merch.getMerchName());
            } else {
                logger.warning("Gym merchandise update failed for: " + merch.getMerchName());
            }
        } catch (Exception e) {
            System.err.println("Error updating gym merchandise: " + e.getMessage());
            e.printStackTrace();
            logger.severe("Logging failed during updating gym merchandise: " + e.getMessage());
        }
    }

    /**
     * Deletes a GymMerch by its ID.
     *
     * @param id ID of the GymMerch to delete.
     */
    public void deleteMerch(int id) {
        try {
            boolean deleted = gymMerchDAO.deleteGymMerch(id);
            if (deleted) {
                logger.info("Gym merchandise deleted successfully with ID: " + id);
            } else {
                logger.warning("Gym merchandise deletion failed for ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error deleting gym merchandise: " + e.getMessage());
            e.printStackTrace();
            logger.severe("Logging failed during deleting gym merchandise: " + e.getMessage());
        }
    }
}