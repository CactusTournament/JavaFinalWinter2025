package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Admin;
import utils.DatabaseConnection;

/**
 * AdminDAO
 * Data Access Object (DAO) class for performing CRUD operations on Admin users.
 * Handles creation, retrieval, update, and deletion of Admin records in the
 * database.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-06
 */
public class AdminDAO {

    /**
     * Default constructor for AdminDAO.
     * Initializes an instance of AdminDAO for performing CRUD operations.
     */
    public AdminDAO() {
        // No initialization required for now
    }

    /**
     * Creates a new admin in the database.
     * Hashes the admin's password before storing it.
     *
     * @param admin The Admin object to be inserted.
     * @return true if the admin was successfully created; false otherwise.
     */
    public boolean createAdmin(Admin admin) {
        String query = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getUserAddress());
            ps.setString(3, admin.getUserPhoneNumber());
            ps.setString(4, admin.getUserRole());
            ps.setString(5, admin.getPasswordHash());
            ps.setString(6, admin.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves an admin by their unique user ID.
     *
     * @param userId The ID of the admin to retrieve.
     * @return Admin object if found; null otherwise.
     */
    public Admin getAdminById(int userId) {
        String query = "SELECT * FROM Users WHERE userId = ? AND userRole = 'Admin'";
        try (Connection conn = DatabaseConnection.getcon();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Admin(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("passwordHash"),
                        rs.getString("email"),
                        rs.getString("userPhoneNumber"),
                        rs.getString("userAddress"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves an admin by their email address.
     *
     * @param email The email of the admin to retrieve.
     * @return Admin object if found; null otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public Admin getAdminByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM admins WHERE email = ?";
        try (Connection conn = DatabaseConnection.getcon();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("passwordHash"),
                        rs.getString("email"),
                        rs.getString("userPhoneNumber"),
                        rs.getString("userAddress"));
            }
        }
        return null;
    }

    /**
     * Retrieves all admins from the database.
     *
     * @return List of Admin objects. Returns an empty list if none are found.
     */
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE userRole = 'Admin'";
        try (Connection conn = DatabaseConnection.getcon();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                admins.add(new Admin(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("passwordHash"),
                        rs.getString("email"),
                        rs.getString("userPhoneNumber"),
                        rs.getString("userAddress")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    /**
     * Updates an existing admin's information in the database.
     * Hashes the password before updating.
     *
     * @param admin The Admin object with updated information.
     * @return true if the update was successful; false otherwise.
     */
    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, passwordHash = ?, email = ? WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getcon();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getUserAddress());
            ps.setString(3, admin.getUserPhoneNumber());
            ps.setString(4, admin.getPasswordHash());
            ps.setString(5, admin.getEmail());
            ps.setInt(6, admin.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an admin from the database by their user ID.
     *
     * @param userId The ID of the admin to delete.
     * @return true if the admin was successfully deleted; false otherwise.
     */
    public boolean deleteAdmin(int userId) {
        String query = "DELETE FROM Users WHERE userId = ? AND userRole = 'Admin'";
        try (Connection conn = DatabaseConnection.getcon();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}