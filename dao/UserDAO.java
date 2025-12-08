package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.User;
import JavaFinalWinter2025.utils.DatabaseConnection;
import JavaFinalWinter2025.utils.PasswordUtil;  // Import PasswordUtil
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Create a user in the database
    public boolean createUser(User user) {
        String query = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            // Hash password before saving
            String hashedPassword = PasswordUtil.hashPassword(user.getPasswordHash());
            
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserAddress());
            ps.setString(3, user.getUserPhoneNumber());
            ps.setString(4, user.getUserRole());
            ps.setString(5, hashedPassword);  // Store the hashed password
            ps.setString(6, user.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve a user by ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM Users WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("passwordHash"),
                        rs.getString("email"),
                        rs.getString("userPhoneNumber"),
                        rs.getString("userAddress"),
                        rs.getString("userRole")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("passwordHash"),
                        rs.getString("email"),
                        rs.getString("userPhoneNumber"),
                        rs.getString("userAddress"),
                        rs.getString("userRole")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update a user's information
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, userRole = ?, passwordHash = ?, email = ? WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserAddress());
            ps.setString(3, user.getUserPhoneNumber());
            ps.setString(4, user.getUserRole());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getEmail());
            ps.setInt(7, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a user by ID
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE userId = ?";
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
