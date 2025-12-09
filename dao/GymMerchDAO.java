package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.GymMerch;
import JavaFinalWinter2025.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GymMerchDAO
 * Data Access Object (DAO) class for performing CRUD operations on gym merchandise items.
 * Handles creation, retrieval, update, and deletion of GymMerch records in the database.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class GymMerchDAO {

    /**
     * Adds a new gym merchandise item to the database.
     *
     * @param gymMerch The GymMerch object to add.
     * @return true if the item was successfully added; false otherwise.
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
     * Retrieves a gym merchandise item by its ID.
     *
     * @param merchID The ID of the merchandise item.
     * @return GymMerch object if found; null otherwise.
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
     * Retrieves all gym merchandise items from the database.
     *
     * @return List of GymMerch objects. Returns an empty list if none are found.
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
     * Updates an existing gym merchandise item in the database.
     *
     * @param gymMerch The GymMerch object with updated information.
     * @return true if the update was successful; false otherwise.
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
     * Deletes a gym merchandise item from the database by its ID.
     *
     * @param merchID The ID of the merchandise item to delete.
     * @return true if the deletion was successful; false otherwise.
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
