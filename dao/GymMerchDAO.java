package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.GymMerch;
import JavaFinalWinter2025.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GymMerch Data Access Object (DAO)
 * Handles CRUD operations for GymMerch items in the database.
 * Provides methods to create, read, update, and delete gym merchandise records.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-07
 */
public class GymMerchDAO {

    /**
     * Add a new gym merchandise item
     * 
     * @param gymMerch the gym merchandise item to add
     * @return boolean true if addition was successful, false otherwise
     */
    public boolean addGymMerch(GymMerch gymMerch) {
        String query = "INSERT INTO GymMerch (merchName, merchType, merchPrice, quantityInStock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
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

    /**
     * Get a gym merchandise item by ID
     * 
     * @param merchID the merchandise ID
     * @return GymMerch the gym merchandise item, or null if not found
     */
    public GymMerch getGymMerchById(int merchID) {
        String query = "SELECT * FROM GymMerch WHERE merchID = ?";
        try (Connection conn = DatabaseConnection.getcon();
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

    /**
     * Get all gym merchandise items
     * 
     * @return List<GymMerch> list of all gym merchandise items
     */
    public List<GymMerch> getAllGymMerch() {
        List<GymMerch> gymMerchList = new ArrayList<>();
        String query = "SELECT * FROM GymMerch";
        try (Connection conn = DatabaseConnection.getcon();
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

    /**
     * Update a gym merchandise item
     * 
     * @param gymMerch the gym merchandise item with updated details
     * @return boolean true if update was successful, false otherwise
     */
    public boolean updateGymMerch(GymMerch gymMerch) {
        String query = "UPDATE GymMerch SET merchName = ?, merchType = ?, merchPrice = ?, quantityInStock = ? WHERE merchID = ?";
        try (Connection conn = DatabaseConnection.getcon();
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

    /**
     * Delete a gym merchandise item by ID
     * @param merchID the merchandise ID to delete
     * @return boolean true if deletion was successful, false otherwise
     */
    public boolean deleteGymMerch(int merchID) {
        String query = "DELETE FROM GymMerch WHERE merchID = ?";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, merchID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
