package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.Member;
import JavaFinalWinter2025.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MemberDAO
 * Data Access Object (DAO) class for performing CRUD operations on Member records.
 * Handles creation, retrieval, update, and deletion of Member records in the database.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class MemberDAO {

    /**
     * Creates a new member in the database.
     *
     * @param member The Member object to add.
     * @return true if the member was successfully created; false otherwise.
     */
    public boolean createMember(Member member) {
        String query = "INSERT INTO Users (userName, userAddress, userPhoneNumber, userRole, passwordHash, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, member.getUserName());
            ps.setString(2, member.getUserAddress());
            ps.setString(3, member.getUserPhoneNumber());
            ps.setString(4, member.getUserRole());
            ps.setString(5, member.getPasswordHash());
            ps.setString(6, member.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a member by their ID.
     *
     * @param userId The ID of the member.
     * @return Member object if found; null otherwise.
     */
    public Member getMemberById(int userId) {
        String query = "SELECT * FROM Users WHERE userId = ? AND userRole = 'Member'";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Member(
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

    /**
     * Retrieves all members from the database.
     *
     * @return List of Member objects. Returns an empty list if none are found.
     */
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE userRole = 'Member'";
        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                members.add(new Member(
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
        return members;
    }

    /**
     * Updates an existing member in the database.
     *
     * @param member The Member object with updated information.
     * @return true if the update was successful; false otherwise.
     */
    public boolean updateMember(Member member) {
        String query = "UPDATE Users SET userName = ?, userAddress = ?, userPhoneNumber = ?, passwordHash = ?, email = ? WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, member.getUserName());
            ps.setString(2, member.getUserAddress());
            ps.setString(3, member.getUserPhoneNumber());
            ps.setString(4, member.getPasswordHash());
            ps.setString(5, member.getEmail());
            ps.setInt(6, member.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a member from the database by their ID.
     *
     * @param userId The ID of the member to delete.
     * @return true if the deletion was successful; false otherwise.
     */
    public boolean deleteMember(int userId) {
        String query = "DELETE FROM Users WHERE userId = ? AND userRole = 'Member'";
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
