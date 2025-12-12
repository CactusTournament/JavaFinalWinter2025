package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Trainer;
import utils.DatabaseConnection;

/**
 * TrainerDAO
 * Provides CRUD operations for Trainer records stored within the Users table.
 * Trainers are Users with userRole = 'Trainer'.
 *
 * Author: Brandon Maloney
 * Updated: 2025-12-08
 */
public class TrainerDAO {

    /**
     * Creates a new Trainer (User with role 'Trainer').
     * Password is hashed using bcrypt before insertion.
     */
    public boolean createTrainer(Trainer trainer) {
        String sql = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) " +
                     "VALUES (?, ?, ?, 'Trainer', ?, ?)";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trainer.getUserName());
            stmt.setString(2, trainer.getUserAddress());
            stmt.setString(3, trainer.getUserPhoneNumber());
            stmt.setString(4, trainer.getPasswordHash());
            stmt.setString(5, trainer.getEmail());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a Trainer by ID.
     */
    public Trainer getTrainerById(int trainerId) {
        String sql = "SELECT * FROM Users WHERE userId = ? AND userRole = 'Trainer'";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTrainer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a Trainer by email.
     */
    public Trainer getTrainerByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ? AND userRole = 'Trainer'";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTrainer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all Trainer records.
     */
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE userRole = 'Trainer'";

        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                trainers.add(mapResultSetToTrainer(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    /**
     * Updates a Trainer record.
     * Password is re-hashed before update.
     */
    public boolean updateTrainer(Trainer trainer) {
        String sql = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, passwordHash = ?, email = ? " +
                     "WHERE userId = ? AND userRole = 'Trainer'";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trainer.getUserName());
            stmt.setString(2, trainer.getUserAddress());
            stmt.setString(3, trainer.getUserPhoneNumber());
            stmt.setString(4, trainer.getPasswordHash());
            stmt.setString(5, trainer.getEmail());
            stmt.setInt(6, trainer.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a Trainer.
     */
    public boolean deleteTrainer(int trainerId) {
        String sql = "DELETE FROM Users WHERE userId = ? AND userRole = 'Trainer'";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Maps a ResultSet row to a Trainer object.
     */
    private Trainer mapResultSetToTrainer(ResultSet rs) throws SQLException {
        return new Trainer(
                rs.getInt("userId"),
                rs.getString("userName"),
                rs.getString("passwordHash"),
                rs.getString("email"),
                rs.getString("userPhoneNumber"),
                rs.getString("userAddress")
        );
    }
}
