package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.WorkoutClass;
import utils.DatabaseConnection;

/**
 * WorkoutClassDAO
 * Data Access Object (DAO) for performing operations on WorkoutClass records.
 * Provides methods to add, retrieve, update, and delete workout classes.
 * 
 * Author: Brandon Maloney
 * Updated by: Abiodun Magret Oyedele
 * Date: 2025-12-08
 */
public class WorkoutClassDAO {
    private Connection conn;

    /**
     * Constructor initializes the DAO with a database connection.
     * Uses DatabaseConnection utility to obtain the connection.
     * 
     */
    public WorkoutClassDAO() {
        this.conn = DatabaseConnection.getcon();
    }

    /**
     * Constructor with existing connection.
     * 
     * @param conn The database connection to use.
     */
    public WorkoutClassDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Create a new WorkoutClass record in the database.
     * 
     * @param wc The WorkoutClass object to be created.
     * @return true if creation was successful, false otherwise.
     */
    public boolean createWorkoutClass(WorkoutClass wc) {

        if (wc.getTrainerID() <= 0) {
            System.err.println("Invalid trainerID for WorkoutClass");
            return false;
        }

        String sql = "INSERT INTO WorkoutClasses (workoutClassType, workoutClassDescription, trainerID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, wc.getWorkoutClassType());
            pstmt.setString(2, wc.getWorkoutClassDescription());
            pstmt.setInt(3, wc.getTrainerID());

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    wc.setWorkoutClassID(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets a WorkoutClass by its ID.
     * 
     * @param id id of the WorkoutClass to retrieve.
     * @return The WorkoutClass object if found, null otherwise.
     */
    public WorkoutClass getWorkoutClassById(int id) {
        String sql = "SELECT * FROM WorkoutClasses WHERE workoutClassID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new WorkoutClass(
                        rs.getInt("workoutClassID"),
                        rs.getString("workoutClassType"),
                        rs.getString("workoutClassDescription"),
                        rs.getInt("trainerID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all WorkoutClasses assigned to a trainer.
     *
     * @param trainerId ID of the trainer.
     * @return List of WorkoutClass objects.
     */
    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) {
        List<WorkoutClass> classes = new ArrayList<>();

        String sql = "SELECT * FROM WorkoutClasses WHERE trainerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                classes.add(new WorkoutClass(
                        rs.getInt("workoutClassID"),
                        rs.getString("workoutClassType"),
                        rs.getString("workoutClassDescription"),
                        rs.getInt("trainerID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * Gets all WorkoutClass records from the database.
     * 
     * @return A list of all WorkoutClass objects.
     */
    public List<WorkoutClass> getAllWorkoutClasses() {
        List<WorkoutClass> list = new ArrayList<>();
        String sql = "SELECT * FROM WorkoutClasses";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new WorkoutClass(
                        rs.getInt("workoutClassID"),
                        rs.getString("workoutClassType"),
                        rs.getString("workoutClassDescription"),
                        rs.getInt("trainerID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Updates an existing WorkoutClass record in the database.
     * 
     * @param wc The WorkoutClass object with updated information.
     * @return true if update was successful, false otherwise.
     */
    public boolean updateWorkoutClass(WorkoutClass wc) {
        String sql = "UPDATE WorkoutClasses SET workoutClassType = ?, workoutClassDescription = ?, trainerID = ? WHERE workoutClassID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, wc.getWorkoutClassType());
            pstmt.setString(2, wc.getWorkoutClassDescription());
            pstmt.setInt(3, wc.getTrainerID());
            pstmt.setInt(4, wc.getWorkoutClassID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a WorkoutClass record from the database.
     * 
     * @param id The ID of the WorkoutClass to delete.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteWorkoutClass(int id) {
        String sql = "DELETE FROM WorkoutClasses WHERE workoutClassID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
