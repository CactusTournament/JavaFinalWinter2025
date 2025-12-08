package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.Membership;
import JavaFinalWinter2025.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Membership Data Access Object (DAO)
 * Handles CRUD operations for Memberships in the database.
 * Provides methods to create, read, update, and delete membership records.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-07
 */
public class MembershipDAO {

    /**
     * Creates a new membership
     * @param membership the membership to create
     * @return boolean true indicating success
     */
    public boolean createMembership(Membership membership) {
        String query = "INSERT INTO Memberships (membershipType, membershipDescription, membershipCost, memberID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, membership.getMembershipType());
            ps.setString(2, membership.getMembershipDescription());
            ps.setDouble(3, membership.getMembershipCost());
            ps.setInt(4, membership.getMemberID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get a membership by ID
     * @param membershipID the membership ID
     * @return Membership the membership object, or null if not found
     */
    public Membership getMembershipById(int membershipID) {
        String query = "SELECT * FROM Memberships WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, membershipID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Membership(
                        rs.getInt("membershipID"),
                        rs.getString("membershipType"),
                        rs.getString("membershipDescription"),
                        rs.getDouble("membershipCost"),
                        rs.getInt("memberID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all memberships
     * @return List<Membership> list of all memberships
     */
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String query = "SELECT * FROM Memberships";
        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                memberships.add(new Membership(
                        rs.getInt("membershipID"),
                        rs.getString("membershipType"),
                        rs.getString("membershipDescription"),
                        rs.getDouble("membershipCost"),
                        rs.getInt("memberID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberships;
    }

    /**
     * Update a membership
     * @param membership the membership to update
     * @return boolean true if update was successful
     */
    public boolean updateMembership(Membership membership) {
        String query = "UPDATE Memberships SET membershipType = ?, membershipDescription = ?, membershipCost = ?, memberID = ? WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, membership.getMembershipType());
            ps.setString(2, membership.getMembershipDescription());
            ps.setDouble(3, membership.getMembershipCost());
            ps.setInt(4, membership.getMemberID());
            ps.setInt(5, membership.getMembershipID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete a membership
     * @param membershipID the membership ID to delete
     * @return boolean true if deletion was successful
     */
    public boolean deleteMembership(int membershipID) {
        String query = "DELETE FROM Memberships WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getcon();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, membershipID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
