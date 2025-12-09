package JavaFinalWinter2025.services;

import JavaFinalWinter2025.dao.WorkoutClassDAO;
import JavaFinalWinter2025.src.WorkoutClass;
import java.util.List;
import java.sql.SQLException;

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
     */
    private WorkoutClassDAO workoutClassDAO;

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
        } catch (SQLException e) {
            System.err.println("Error adding workout class: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a WorkoutClass by its ID.
     * throws SQLException if a database access error occurs.
     *
     * @param id ID of the WorkoutClass to retrieve.
     * @return WorkoutClass object if found, null otherwise.
     */
    public WorkoutClass getWorkoutClass(int id) {
        try{
            return workoutClassDAO.getWorkoutClassById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving workout class: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all WorkoutClasses.
     * throws SQLException if a database access error occurs.
     *
     * @return List of all WorkoutClass objects.
     */
    public List<WorkoutClass> getAllWorkoutClasses() {
        try {
            return workoutClassDAO.getAllWorkoutClasses();
        } catch (SQLException e) {
            System.err.println("Error retrieving all workout classes: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates an existing WorkoutClass.
     * throws SQLException if a database access error occurs.
     *
     * @param workoutClass WorkoutClass object with updated information.
     */
    public void updateWorkoutClass(WorkoutClass workoutClass) {
        try {
            workoutClassDAO.updateWorkoutClass(workoutClass);
        } catch (SQLException e) {
            System.err.println("Error updating workout class: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a WorkoutClass by its ID.
     * throws SQLException if a database access error occurs.
     *
     * @param id ID of the WorkoutClass to delete.
     */
    public void deleteWorkoutClass(int id) {
        try {
            workoutClassDAO.deleteWorkoutClass(id);
        } catch (SQLException e) {
            System.err.println("Error deleting workout class: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

