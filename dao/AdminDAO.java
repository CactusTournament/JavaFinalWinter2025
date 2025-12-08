package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.Admin;
import JavaFinalWinter2025.utils.DatabaseConnection;
import JavaFinalWinter2025.utils.PasswordUtil;  // Import PasswordUtil
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // Create an admin
    public boolean createAdmin(Admin admin) {
        String query = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            // Hash password before saving
            String hashedPassword = PasswordUtil.hashPassword(admin.getPasswordHash());
            
            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getUserAddress());
            ps.setString(3, admin.getUserPhoneNumber());
            ps.setString(4, admin.getUserRole());
            ps.setString(5, hashedPassword);  // Store the hashed password
            ps.setString(6, admin.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get admin by ID
    public Admin getAdminById(int userId) {
        String query = "SELECT * FROM Users WHERE userId = ? AND userRole = 'Admin'";
        try (Connection conn = DatabaseConnection.getConnection();
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
                        rs.getString("userAddress")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE userRole = 'Admin'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                admins.add(new Admin(
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
        return admins;
    }

    // Update an admin
    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, passwordHash = ?, email = ? WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            // Hash the password if it's being updated
            String hashedPassword = PasswordUtil.hashPassword(admin.getPasswordHash());
            
            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getUserAddress());
            ps.setString(3, admin.getUserPhoneNumber());
            ps.setString(4, hashedPassword);  // Store the hashed password
            ps.setString(5, admin.getEmail());
            ps.setInt(6, admin.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete an admin by ID
    public boolean deleteAdmin(int userId) {
        String query = "DELETE FROM Users WHERE userId = ? AND userRole = 'Admin'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
