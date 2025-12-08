package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.GymMerch;
import JavaFinalWinter2025.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GymMerchDAO {

    // Add a new gym merchandise item
    public boolean addGymMerch(GymMerch gymMerch) {
        String query = "INSERT INTO GymMerch (merchName, merchType, merchPrice, quantityInStock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, gymMerch.getMerchName());
            ps.setString(2, gymMerch.getMerchType());
            ps.setDouble(3, gymMerch.getMerchPrice());
            ps.setInt(4, gymMerch.getQuantityInStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get gym merchandise by ID
    public GymMerch getGymMerchById(int merchID) {
        String query = "SELECT * FROM GymMerch WHERE merchID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, merchID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new GymMerch(
                        rs.getInt("merchID"),
                        rs.getString("merchName"),
                        rs.getString("merchType"),
                        rs.getDouble("merchPrice"),
                        rs.getInt("quantityInStock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all gym merchandise items
    public List<GymMerch> getAllGymMerch() {
        List<GymMerch> gymMerchList = new ArrayList<>();
        String query = "SELECT * FROM GymMerch";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                gymMerchList.add(new GymMerch(
                        rs.getInt("merchID"),
                        rs.getString("merchName"),
                        rs.getString("merchType"),
                        rs.getDouble("merchPrice"),
                        rs.getInt("quantityInStock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gymMerchList;
    }

    // Update a gym merchandise item
    public boolean updateGymMerch(GymMerch gymMerch) {
        String query = "UPDATE GymMerch SET merchName = ?, merchType = ?, merchPrice = ?, quantityInStock = ? WHERE merchID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, gymMerch.getMerchName());
            ps.setString(2, gymMerch.getMerchType());
            ps.setDouble(3, gymMerch.getMerchPrice());
            ps.setInt(4, gymMerch.getQuantityInStock());
            ps.setInt(5, gymMerch.getMerchID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a gym merchandise item by ID
    public boolean deleteGymMerch(int merchID) {
        String query = "DELETE FROM GymMerch WHERE merchID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, merchID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
