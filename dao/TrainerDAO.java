package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.src.Trainer;
import JavaFinalWinter2025.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TrainerDAO
 * Data Access Object (DAO) class for performing CRUD operations on Trainer records.
 * Handles creation, retrieval, update, and deletion of Trainer records in the database.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class TrainerDAO {

    /**
     * Creates a new Trainer in the database.
     *
     * @param trainer The Trainer object to add.
     * @return true if the Trainer was successfully created; false otherwise.
     */
    public boolean createTrainer(Trainer trainer) {
        String query = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) " +
                       "VALUES (?, ?, ?, 'Trainer', ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, trainer.getUserName());
            ps.setString(2, trainer.getUserAddress());
            ps.setString(3, trainer.getUserPhoneNumber());
            ps.setString(4, trainer.getPasswordHash());  // hashed already
            ps.setString(5, trainer.getEmail());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a Trainer by their ID.
     *
     * @param trainerId The ID of the Trainer.
     * @return Trainer object if found; null otherwise.
     */
    public Trainer getTrainerById(int trainerId) {
        String query = "SELECT * FROM Users WHERE userId = ? AND userRole = 'Trainer'";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, trainerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Trainer(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("passwordHash"),
                        rs.getString("email"),
                        rs.getString("userPhoneNumber"),
                        rs.getString("userAddress")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all Trainers from the database.
     *
     * @return List of Trainer objects. Returns an empty list if none are found.
     */
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE userRole = 'Trainer'";
        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                trainers.add(new Trainer(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("passwordHash"),
                        rs.getString("email"),
                        rs.getString("userPhoneNumber"),
                        rs.getString("userAddress")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    /**
     * Updates an existing Trainer's information in the database.
     *
     * @param trainer The Trainer object with updated information.
     * @return true if the update was successful; false otherwise.
     */
    public boolean updateTrainer(Trainer trainer) {
        String query = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, passwordHash = ?, email = ? " +
                       "WHERE userId = ? AND userRole = 'Trainer'";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, trainer.getUserName());
            ps.setString(2, trainer.getUserAddress());
            ps.setString(3, trainer.getUserPhoneNumber());
            ps.setString(4, trainer.getPasswordHash());
            ps.setString(5, trainer.getEmail());
            ps.setInt(6, trainer.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a Trainer from the database by their ID.
     *
     * @param trainerId The ID of the Trainer to delete.
     * @return true if the deletion was successful; false otherwise.
     */
    public boolean deleteTrainer(int trainerId) {
        String query = "DELETE FROM Users WHERE userId = ? AND userRole = 'Trainer'";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, trainerId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
