package JavaFinalWinter2025.dao;


import JavaFinalWinter2025.WorkoutClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WorkoutClass Data Access Object (DAO)
 * Handles CRUD operations for WorkoutClass entities in the database.
 * Provides methods to create, read, update, and delete workout class records.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-07
 */
public class WorkoutClassDAO {
    /**
     * Database connection
     */
    private Connection conn;

    /**
     * Constructor
     * @param conn
     */
    public WorkoutClassDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Add a new workout class
     * @param wc the workout class to add
     * @throws SQLException
     */
    public void addWorkoutClass(WorkoutClass wc) throws SQLException {
        String sql = "INSERT INTO WorkoutClasses (workoutClassID, workoutClassType, workoutClassDescription, trainerID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, wc.getWorkoutClassID());
            pstmt.setString(2, wc.getWorkoutClassType());
            pstmt.setString(3, wc.getWorkoutClassDescription());
            pstmt.setInt(4, wc.getTrainerID());
            pstmt.executeUpdate();
        }
    }

    /**
     * Get all workout classes
     * @return List of WorkoutClass
     * @throws SQLException
     */
    public List<WorkoutClass> getAllWorkoutClasses() throws SQLException {
        List<WorkoutClass> list = new ArrayList<>();
        String sql = "SELECT * FROM WorkoutClasses";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                WorkoutClass wc = new WorkoutClass(
                        rs.getInt("workoutClassID"),
                        rs.getString("workoutClassType"),
                        rs.getString("workoutClassDescription"),
                        rs.getInt("trainerID")
                );
                list.add(wc);
            }
        }
        return list;
    }
}
