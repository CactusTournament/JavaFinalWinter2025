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
     * Sets the generated userId on the User object.
     * 
     * @param user The User object to be created.
     * @return true if creation was successful, false otherwise.
     */
    public boolean createUser(User user) {
        String sql = """
            INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserAddress());
            stmt.setString(3, user.getUserPhoneNumber());
            stmt.setString(4, user.getUserRole());
            stmt.setString(5, user.getPasswordHash());
            stmt.setString(6, user.getEmail());

            int rows = stmt.executeUpdate();
            if (rows == 0) return false;

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setUserId(keys.getInt(1));
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a User by username.
     * @return null if no user is found.
     * 
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
     * @param email The email of the user to retrieve.
     * @return The User object if found, null otherwise.
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
     * @param userId The ID of the user to retrieve.
     * @return The User object if found, null otherwise.
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
     * 
     * @return A list of User objects.
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
     * 
     * @param user The User object with updated information.
     * @return true if update was successful, false otherwise.
     */
    public boolean updateUser(User user) {
        String sql = """
            UPDATE Users
            SET userName = ?, userAddress = ?, userPhoneNumber = ?, userRole = ?, passwordHash = ?, email = ?
            WHERE userId = ?
        """;

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
     * @param userId The ID of the user to delete.
     * @return true if deletion was successful, false otherwise.
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
     * @param rs The ResultSet object.
     * @return The mapped User object.
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
