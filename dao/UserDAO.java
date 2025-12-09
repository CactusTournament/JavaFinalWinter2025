package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.src.User;
import JavaFinalWinter2025.utils.DatabaseConnection;
import JavaFinalWinter2025.utils.PasswordUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO
 * Data Access Object (DAO) class for performing CRUD operations on User records.
 * Handles creation, retrieval, update, and deletion of User records in the database.
 * Passwords are hashed using PasswordUtil before being stored.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class UserDAO {

    /**
     * Creates a new User in the database.
     * Password will be hashed before storage.
     *
     * @param user The User object to add.
     * @return true if the User was successfully created; false otherwise.
     */
    public boolean createUser(User user) {
        String query = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            String hashedPassword = PasswordUtil.hashPassword(user.getPasswordHash());
            
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserAddress());
            ps.setString(3, user.getUserPhoneNumber());
            ps.setString(4, user.getUserRole());
            ps.setString(5, hashedPassword);
            ps.setString(6, user.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a User by their ID.
     *
     * @param userId The ID of the User.
     * @return User object if found; null otherwise.
     */
    public User getUserById(int userId) {
        String query = "SELECT * FROM Users WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getcon();
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

    /**
     * Retrieves all Users from the database.
     *
     * @return List of User objects. Returns an empty list if none are found.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = DatabaseConnection.getcon();
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

    /**
     * Updates an existing User's information in the database.
     *
     * @param user The User object with updated information.
     * @return true if the update was successful; false otherwise.
     */
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, userRole = ?, passwordHash = ?, email = ? WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getcon();
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

    /**
     * Deletes a User from the database by their ID.
     *
     * @param userId The ID of the User to delete.
     * @return true if the deletion was successful; false otherwise.
     */
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE userId = ?";
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
