package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.WorkoutClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WorkoutClassDAO
 * Data Access Object (DAO) for performing operations on WorkoutClass records in the database.
 * Provides methods to add a workout class and retrieve all workout classes.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class WorkoutClassDAO {
    private Connection conn;

    /**
     * Constructor to initialize DAO with a database connection.
     *
     * @param conn Active SQL Connection object.
     */
    public WorkoutClassDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds a new WorkoutClass to the database.
     *
     * @param wc WorkoutClass object to insert.
     * @throws SQLException if a database access error occurs.
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
     * Retrieves all WorkoutClass records from the database.
     *
     * @return List of WorkoutClass objects. Returns an empty list if no records are found.
     * @throws SQLException if a database access error occurs.
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
