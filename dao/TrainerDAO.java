package JavaFinalWinter2025.dao;


import JavaFinalWinter2025.Trainer;
import JavaFinalWinter2025.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class TrainerDAO {

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
