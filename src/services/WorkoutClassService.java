package services;

import dao.WorkoutClassDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import models.WorkoutClass;
import utils.LoggerUtil;

/**
 * WorkoutClassService
 * Service layer for managing WorkoutClass operations.
 * Methods throw SQLException to allow tests and callers to handle database errors.
 * 
 * Author: Abiodun Magret Oyedele
 * Updated: 2025-12-11
 */
public class WorkoutClassService {

    private WorkoutClassDAO workoutClassDAO;
    private static final Logger logger = LoggerUtil.getLogger();

    public WorkoutClassService(WorkoutClassDAO workoutClassDAO) {
        this.workoutClassDAO = workoutClassDAO;
    }

    /**
     * Adds a new WorkoutClass.
     * @param workoutClass WorkoutClass object to add.
     * @throws SQLException if a database error occurs.
     */
    public void createWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        workoutClassDAO.createWorkoutClass(workoutClass);
        logger.info("Workout class added successfully: " + workoutClass.getWorkoutClassType());
    }

    /**
     * Retrieves a WorkoutClass by ID.
     * @param id ID of the workout class.
     * @return WorkoutClass object if found, null otherwise.
     * @throws SQLException if a database error occurs.
     */
    public WorkoutClass getWorkoutClass(int id) throws SQLException {
        WorkoutClass wc = workoutClassDAO.getWorkoutClassById(id);
        if (wc != null) {
            logger.info("Workout class retrieved: " + wc.getWorkoutClassType());
        } else {
            logger.warning("Workout class not found: ID " + id);
        }
        return wc;
    }

    /**
     * Retrieves all WorkoutClasses.
     * @return List of all WorkoutClass objects.
     * @throws SQLException if a database error occurs.
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> classes = workoutClassDAO.getAllWorkoutClasses();
        logger.info("Retrieved " + (classes != null ? classes.size() : 0) + " workout classes");
        return classes;
    }

    /**
     * Updates an existing WorkoutClass.
     * @param workoutClass WorkoutClass object with updated data.
     * @throws SQLException if a database error occurs.
     */
    public void updateWorkoutClass(WorkoutClass workoutClass) throws SQLException {
        workoutClassDAO.updateWorkoutClass(workoutClass);
        logger.info("Workout class updated: " + workoutClass.getWorkoutClassType());
    }

    /**
     * Deletes a WorkoutClass by ID.
     * @param id ID of the WorkoutClass to delete.
     * @throws SQLException if a database error occurs.
     */
    public void deleteWorkoutClass(int id) throws SQLException {
        workoutClassDAO.deleteWorkoutClass(id);
        logger.info("Workout class deleted: ID " + id);
    }
}
