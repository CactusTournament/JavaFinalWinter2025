package JavaFinalWinter2025.services;

import JavaFinalWinter2025.dao.WorkoutClassDAO;
import JavaFinalWinter2025.src.WorkoutClass;
import java.util.List;
import java.sql.SQLException;
import JavaFinalWinter2025.utils.LoggerUtil;
import java.util.logging.Logger;

/**
 * WorkoutClassService
 * Service layer for managing WorkoutClass operations.
 * Provides methods to add, retrieve, update, and delete workout classes.
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-08
 */
public class WorkoutClassService {
    /**
     * DAO for WorkoutClass operations.
     * Logger for logging events.
     */
    private WorkoutClassDAO workoutClassDAO;
    private static final Logger logger = LoggerUtil.getLogger();

    /**
     * Constructor to initialize the service with a WorkoutClassDAO.
     *
     * @param workoutClassDAO DAO for WorkoutClass operations.
     */
    public WorkoutClassService(WorkoutClassDAO workoutClassDAO) {
        this.workoutClassDAO = workoutClassDAO;
    }

    /**
     * Adds a new WorkoutClass.
     * throws SQLException if a database access error occurs.
     *
     * @param workoutClass WorkoutClass object to add.
     */
    public void addWorkoutClass(WorkoutClass workoutClass) {
        try {
            workoutClassDAO.addWorkoutClass(workoutClass);
            logger.info("Workout class added successfully: " + workoutClass.getWorkoutClassType());
        } catch (SQLException e) {
            System.err.println("Error adding workout class: " + e.getMessage());
            e.printStackTrace();
            logger.severe("Error adding workout class: " + e.getMessage());
        }
    }

    /**
     * Retrieves a WorkoutClass by its ID.
     * throws SQLException if a database access error occurs.
     * Logs the retrieval process.
     *
     * @param id ID of the WorkoutClass to retrieve.
     * @return WorkoutClass object if found, null otherwise.
     */
    public WorkoutClass getWorkoutClass(int id) {
        try{
            WorkoutClass workoutToAdd = workoutClassDAO.getWorkoutClassById(id);
            if (workoutToAdd != null) {
                logger.info("Workout class retrieved successfully: " + workoutToAdd.getWorkoutClassType());
                return workoutToAdd;
            } else {
                logger.info("Workout class with ID " + id + " not found.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving workout class: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all WorkoutClasses.
     * throws SQLException if a database access error occurs.
     * Logs the retrieval process.
     *
     * @return List of all WorkoutClass objects.
     */
    public List<WorkoutClass> getAllWorkoutClasses() {
        try {
            List<WorkoutClass> classes = workoutClassDAO.getAllWorkoutClasses();
            if (classes != null) {
                logger.info("Retrieved " + classes.size() + " workout classes from the database.");
                return classes;
            } else {
                logger.info("No workout classes found in the database.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all workout classes: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates an existing WorkoutClass.
     * throws SQLException if a database access error occurs.
     * Logs the update process.
     *
     * @param workoutClass WorkoutClass object with updated information.
     */
    public void updateWorkoutClass(WorkoutClass workoutClass) {
        try {
            workoutClassDAO.updateWorkoutClass(workoutClass);
            logger.info("Workout class updated successfully: " + workoutClass.getWorkoutClassType());
        } catch (SQLException e) {
            System.err.println("Error updating workout class: " + e.getMessage());
            e.printStackTrace();
            logger.severe("Error updating workout class: " + e.getMessage());
        }
    }

    /**
     * Deletes a WorkoutClass by its ID.
     * throws SQLException if a database access error occurs.
     * Logs the deletion process.
     *
     * @param id ID of the WorkoutClass to delete.
     */
    public void deleteWorkoutClass(int id) {
        try {
            workoutClassDAO.deleteWorkoutClass(id);
            logger.info("Workout class deleted successfully with ID: " + id);
        } catch (SQLException e) {
            System.err.println("Error deleting workout class: " + e.getMessage());
            e.printStackTrace();
            logger.severe("Error deleting workout class: " + e.getMessage());
        }
    }
}

