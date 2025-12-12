package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.User;
import utils.DatabaseConnection;

/**
 * UserDAO
 * Provides CRUD operations for User records stored in the Users table.
 * Passwords are securely hashed using bcrypt before being stored.
 *
 * Author: Brandon Maloney
 * Updated: 2025-12-08
 */
public class UserDAO {

    /**
     * Creates a new User in the database.
     * Expects the password to be hashed before calling this method.
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserAddress());
            stmt.setString(3, user.getUserPhoneNumber());
            stmt.setString(4, user.getUserRole());
            stmt.setString(5, user.getPasswordHash());
            stmt.setString(6, user.getEmail());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a User by username.
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE userName = ?";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a User by their email.
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a User by their ID.
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of all Users.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Updates a User record.
     * Expects the password to be hashed before calling this method.
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, userRole = ?, passwordHash = ?, email = ? " +
                     "WHERE userId = ?";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserAddress());
            stmt.setString(3, user.getUserPhoneNumber());
            stmt.setString(4, user.getUserRole());
            stmt.setString(5, user.getPasswordHash());
            stmt.setString(6, user.getEmail());
            stmt.setInt(7, user.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a User by ID.
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE userId = ?";

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Maps a ResultSet row to a User object.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
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
}
