package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.Member;
import JavaFinalWinter2025.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Member Data Access Object (DAO)
 * Handles CRUD operations for Member users in the database.
 * Provides methods to create, read, update, and delete member records.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-07
 */
public class MemberDAO {

    /**
     * Creates a new member
     * @param member the member to create
     * @return boolean true indicating success
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
     * Get a member by ID
     * 
     * @param userId the member's user ID
     * @return Member the member object, or null if not found
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
     * Get all members
     * 
     * @return List<Member> list of all members
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
     * Update a member's information
     * @param member the member with updated information
     * @return boolean true if update was successful, false otherwise
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
     * Delete a member by ID
     * 
     * @param userId the member's user ID
     * @return boolean true if deletion was successful, false otherwise
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
