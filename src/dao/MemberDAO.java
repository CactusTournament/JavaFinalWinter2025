package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Member;
import utils.DatabaseConnection;

/**
 * MemberDAO
 * Data Access Object (DAO) class for performing CRUD operations on Member records.
 * Handles creation, retrieval, update, and deletion of Member records in the database.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class MemberDAO {

    /** Creates a new member in the database. */
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

            return ps.executeUpdate() > 0; // true if row inserted
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Retrieves a member by ID. */
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

    /** Retrieves all members. */
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

    /** Updates an existing member. */
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

    /** Deletes a member by ID. */
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
