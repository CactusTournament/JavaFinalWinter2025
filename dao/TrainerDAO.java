package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.Trainer;
import JavaFinalWinter2025.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Trainer Data Access Object (DAO)
 * TrainerDAO handles CRUD operations for Trainer entities in the database.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-07
 */
public class TrainerDAO {

    /**
     * Creates a new trainer
     * @param trainer the trainer to create
     * @return boolean true indicating success
     */
    public boolean createTrainer(Trainer trainer) {
        String query = "INSERT INTO Trainers (trainerName, trainerSpecialty, trainerCertification) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, trainer.getTrainerName());
            ps.setString(2, trainer.getTrainerSpecialty());
            ps.setString(3, trainer.getTrainerCertification());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get a trainer by ID
     * @param trainerID the trainer ID
     * @return Trainer the trainer object, or null if not found
     */
    public Trainer getTrainerById(int trainerID) {
        String query = "SELECT * FROM Trainers WHERE trainerID = ?";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, trainerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Trainer(rs.getInt("trainerID"), rs.getString("trainerName"), 
                        rs.getString("trainerSpecialty"), rs.getString("trainerCertification"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all trainers
     * @return List<Trainer> list of all trainers
     */
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        String query = "SELECT * FROM Trainers";
        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                trainers.add(new Trainer(rs.getInt("trainerID"), rs.getString("trainerName"), 
                        rs.getString("trainerSpecialty"), rs.getString("trainerCertification")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

    /**
     * Update a trainer
     * @param trainer the trainer to update
     * @return boolean true indicating success
     */
    public boolean updateTrainer(Trainer trainer) {
        String query = "UPDATE Trainers SET trainerName = ?, trainerSpecialty = ?, trainerCertification = ? WHERE trainerID = ?";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, trainer.getTrainerName());
            ps.setString(2, trainer.getTrainerSpecialty());
            ps.setString(3, trainer.getTrainerCertification());
            ps.setInt(4, trainer.getTrainerID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete a trainer
     * @param trainerID the trainer ID to delete
     * @return boolean true indicating success
     */
    public boolean deleteTrainer(int trainerID) {
        String query = "DELETE FROM Trainers WHERE trainerID = ?";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, trainerID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
